package systemstesting;

import DataSet.Benchmark;
import DataSet.Benchmark;
import UptodatAnswers.CuratedAnswer;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.MainBean;
import org.apache.commons.io.IOUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import qa.dataStructures.Question;

@ManagedBean
@SessionScoped
public class Evaluator_NewQASystem  implements EvaluatorInterface{

    public static BenchmarkEval evaluatedBenchmark;
    static int currentQuestion;

    ArrayList<Query> qs;

    static String KB;

    static ArrayList<String> systemAnswersList;
    static ArrayList<String> corectAnswersList;

    static ArrayList<QuestionEval> evaluatedQuestions;

    static int counter = 0;
    static int qsWithAnswers = 0;
    
    int progress=0;
    static Benchmark ben;

    public Evaluator_NewQASystem() throws IOException {

    }

    public void evaluate(Benchmark benchmark) throws IOException {
        KB = MainBean.eval_knowledgebase;
        evaluatedBenchmark = new BenchmarkEval(MainBean.eval_benchmark);
        currentQuestion = 0;
        
        evaluatedQuestions = new ArrayList<>();

        qs = benchmark.queries;
        evaluatedBenchmark.allQuestions = benchmark.questions.size();

        counter = 0;
        qsWithAnswers = 0;

        performance(benchmark, MainBean.eval_benchmark, MainBean.eval_update_answers);
    }

    public void periodicPoll() throws IOException
    {
        performance(ben, MainBean.eval_benchmark, MainBean.eval_update_answers);
    }
    
    public static void performance(Benchmark benchmark, String benchmarkName, boolean curated) throws IOException {
        ben = benchmark;
        systemAnswersList = new ArrayList<>();
        corectAnswersList = new ArrayList<>();
        
        //for (Question question : questions) {
        Question question = benchmark.questions.get(currentQuestion);
        currentQuestion++;
        corectAnswersList = new ArrayList<>();
        counter++;
        //1- Determine CorectAnswerList
        if (curated) {
            corectAnswersList = CuratedAnswer.upToDateAnswer(question.getQuestionQuery(), KB);
        } else {
            corectAnswersList = question.getAnswers();

            for (int i = 0; i < corectAnswersList.size(); i++) {
                if (corectAnswersList.get(i) != null) {
                    corectAnswersList.set(i, corectAnswersList.get(i).trim().replace('_', ' ')
                            .replaceAll("\n", "").replaceAll("\t", "")
                            .replace("http://dbpedia.org/resource/", "")
                            .replace("https://en.wikipedia.org/wiki/", "")
                            .replace("http://www.wikidata.org/entity/", ""));
                }

            }
            try {
                if (corectAnswersList != null) {
                    if (corectAnswersList.size() > 0) {
                        qsWithAnswers++;
                    } else {
                        return;
                    }

                    if (corectAnswersList.size() == 1 && corectAnswersList.get(0).equals("null")) {
                        return;
                    }
                }
            } catch (Exception e) {
                corectAnswersList = new ArrayList<>();
            }
        }
            //2- Determine systemAnswersList
        //for (int i = 0; i < 3; i++) {
        String q = question.getQuestionString().replace('?', ' ').replace(" ", "%20");

        answer(q);

        systemAnswersList = new ArrayList<String>(new HashSet<String>(systemAnswersList));
        corectAnswersList = new ArrayList<String>(new HashSet<String>(corectAnswersList));
        
        //3- List of Questions and their (R, P, F1)
        evaluatedBenchmark.evaluatedQuestions.add(new QuestionEval(question.getQuestionString(), question, corectAnswersList, systemAnswersList));

        //}
        if (currentQuestion >= benchmark.questions.size()) {
            //4- Calculate parameters
            evaluatedBenchmark.calculateParameters();
        }

    }

    static void answer(String question) throws IOException, JSONException {

        JSONObject json = null;

        //////////////////
        String command
                = "curl -X POST http://qanswer-core1.univ-st-etienne.fr/api/gerbil?"
                + "query=" + question;// + "&kb=" + KB;
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        Process process = processBuilder.start();
        InputStream inputStream = process.getInputStream();
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        /////////////////

        result = result.replace("\\n", " ").replace("\\{", "{").replace("\\}", "}").replace("\\", "")
                .replace("\"{", "{").replace("\"}", "}").replace("}\"", "}");
//        System.out.println(result);

        try {
            systemAnswersList = new ArrayList<>();
            json = new JSONObject(result);
            JSONArray bindings = json.getJSONArray("questions").getJSONObject(0)
                    .getJSONObject("question").getJSONObject("answers")
                    .getJSONObject("results").getJSONArray("bindings");

            String varName = json.getJSONArray("questions").getJSONObject(0)
                    .getJSONObject("question").getJSONObject("answers")
                    .getJSONObject("head").getJSONArray("vars").getString(0);

//            System.out.println(varName);

            for (int i = 0; i < bindings.length(); i++) {
                JSONObject binding = (JSONObject) bindings.getJSONObject(i);
                JSONObject o1 = (JSONObject) binding.getJSONObject(varName);
                String value = (String) o1.get("value");

                //For wikidata with Freebase benchmarks
                if (value.startsWith("http")) {
                    org.jsoup.nodes.Document doc = Jsoup.connect(value).get();
                    Elements div = doc.select(".wikibase-title-label");
                    Element e = div.get(0);
                    value = e.text();
                    systemAnswersList.add(value.replace('_', ' '));
                }
                systemAnswersList.add(value.replace('_', ' ')
                        .replace("http://dbpedia.org/resource/", "")
                        .replace("https://en.wikipedia.org/wiki/", "")
                        .replace("http://www.wikidata.org/entity/", ""));
            }
        } catch (Exception e) {
            try {
                boolean b = json.getJSONArray("questions").getJSONObject(0)
                        .getJSONObject("question").getJSONObject("answers")
                        .getBoolean("boolean");
                systemAnswersList = new ArrayList<>();
                if (b) {
                    systemAnswersList.add("Yes");
                } else {
                    systemAnswersList.add("No");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public BenchmarkEval getEvaluatedBenchmark() {
        return evaluatedBenchmark;
    }

    public void setEvaluatedBenchmark(BenchmarkEval evaluatedBenchmark) {
        Evaluator_NewQASystem.evaluatedBenchmark = evaluatedBenchmark;
    }

    public int getProgress() {
        progress = (int)(100*((double)currentQuestion/ben.questions.size()));
        if (currentQuestion >= ben.questions.size()) {
            progress=100;
        }
        
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
    
    
    public void onComplete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("CBench Collected All System Answers."));
    }

    public ArrayList<QuestionEval> getEvaluatedQuestions() {
        return evaluatedQuestions;
    }

    public void setEvaluatedQuestions(ArrayList<QuestionEval> evaluatedQuestions) {
        Evaluator_NewQASystem.evaluatedQuestions = evaluatedQuestions;
    }
    
    
    
}
