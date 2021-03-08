package systemstesting;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
public class Evaluator_WDAqua {

    static BenchmarkEval evaluatedBenchmark;
    static int currentQuestion;

    static ArrayList<Query> qs;
    static ArrayList<Question> questions = DataSetPreprocessing.questions;

    static String KB;

    static ArrayList<String> systemAnswersList;
    static ArrayList<String> corectAnswersList;

    static ArrayList<QuestionEval> evaluatedQuestions;

    static int counter = 0;
    static int qsWithAnswers = 0;

    public Evaluator_WDAqua() throws IOException {

    }

    public static void evaluate() throws IOException {
        KB = MainBean.eval_knowledgebase;
        evaluatedBenchmark = new BenchmarkEval(MainBean.eval_benchmark);
        currentQuestion = 0;
        
        evaluatedQuestions = new ArrayList<>();

        String benchmark = MainBean.eval_benchmark;
        try {

            if (benchmark.equals("QALD-1")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_1, false, false, false);
            } else if (benchmark.equals("QALD-2")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_2, false, false, false);
            } else if (benchmark.equals("QALD-3")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_3, false, false, false);
            } else if (benchmark.equals("QALD-4")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_4, false, false, false);
            } else if (benchmark.equals("QALD-5")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_5, false, false, false);
            } else if (benchmark.equals("QALD-6")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_6, false, false, false);
            } else if (benchmark.equals("QALD-7")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_7, false, false, false);
            } else if (benchmark.equals("QALD-8")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_8, false, false, false);
            } else if (benchmark.equals("QALD-9")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_9, false, false, false);
            } else if (benchmark.equals("QALD-ALL")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_ALL, false, false, false);
            } else if (benchmark.equals("LC-QUAD")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.LC_QUAD, true, false, false);
            } else if (benchmark.equals("WebQuestions")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.WebQuestions, false, true, true);
            } else if (benchmark.equals("GraphQuestions")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.GraphQuestions, false, true, true);
            } else if (benchmark.equals("SimpleDBpediaQA")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.SimpleDBpediaQA, false, true, true);
            } else if (benchmark.equals("SimpleQuestions")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.SimpleQuestions, false, true, true);
            } else if (benchmark.equals("ComplexQuestions")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.ComplexQuestions, false, true, true);
            } else if (benchmark.equals("ComQA")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.ComQA, false, true, true);
            } else if (benchmark.equals("TempQuestions")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.TempQuestions, false, true, true);
            } else if (benchmark.equals("UserDefined")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.UserDefined, false, true, true);
            } else if (benchmark.equals("PropertiesDefined")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.PropertiesDefined, false, true, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        questions = DataSetPreprocessing.questions;
        evaluatedBenchmark.allQuestions = questions.size();

        counter = 0;
        qsWithAnswers = 0;

        performance(MainBean.eval_benchmark, MainBean.eval_update_answers);
    }

    public void periodicPoll() throws IOException
    {
        performance(MainBean.eval_benchmark, MainBean.eval_update_answers);
    }
    
    public static void performance(String benchmarkName, boolean curated) throws IOException {

        systemAnswersList = new ArrayList<>();
        corectAnswersList = new ArrayList<>();
        
        //for (Question question : questions) {
        Question question = questions.get(currentQuestion);
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

        //3- List of Questions and their (R, P, F1)
        evaluatedBenchmark.evaluatedQuestions.add(new QuestionEval(question.getQuestionString(), question, corectAnswersList, systemAnswersList));

        //}
        if (currentQuestion >= questions.size()) {
            //4- Calculate parameters
            evaluatedBenchmark.calculateParameters();

            //5- At the End, Print Results
            evaluatedBenchmark.printScores();
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
        System.out.println(result);

        try {
            systemAnswersList = new ArrayList<>();
            json = new JSONObject(result);
            JSONArray bindings = json.getJSONArray("questions").getJSONObject(0)
                    .getJSONObject("question").getJSONObject("answers")
                    .getJSONObject("results").getJSONArray("bindings");

            String varName = json.getJSONArray("questions").getJSONObject(0)
                    .getJSONObject("question").getJSONObject("answers")
                    .getJSONObject("head").getJSONArray("vars").getString(0);

            System.out.println(varName);

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
//                systemAnswersList.add(value.replace('_', ' ')
//                        .replace("http://dbpedia.org/resource/", "")
//                        .replace("https://en.wikipedia.org/wiki/", "")
//                        .replace("http://www.wikidata.org/entity/", ""));
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
        Evaluator_WDAqua.evaluatedBenchmark = evaluatedBenchmark;
    }

    
    
}
