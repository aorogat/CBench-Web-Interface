package systemstesting;

import DataSet.Benchmark;
import UptodatAnswers.CuratedAnswer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.MainBean;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceReader;
import org.apache.jena.query.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import qa.dataStructures.Question;

@ManagedBean
@SessionScoped
public class Evaluator_QAsparqlBean implements EvaluatorInterface{
    
    public static final int ASK_QUERY = 1;
    public static final int COUNT_QUERY = 2;
    public static final int OTHER_QUERY = 0;

    static BenchmarkEval evaluatedBenchmark;
    static int currentQuestion;

    ArrayList<Query> qs;

    static String KB;

    static ArrayList<String> systemAnswersList;
    static ArrayList<String> corectAnswersList;

    static ArrayList<QuestionEval> evaluatedQuestions;

    static int counter = 0;
    static int qsWithAnswers = 0;

    int progress = 0;
    static Benchmark ben;
    static String answerFileString;

    
    static Object obj;
    public Evaluator_QAsparqlBean() throws IOException {
        try {
            JSONParser parser = new JSONParser();
            InputStream initialStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("data/QASparql/qald_9_answer_output.json");

                byte[] buffer = IOUtils.toByteArray(initialStream);
                Reader targetReader = new CharSequenceReader(new String(buffer));
                obj = parser.parse(targetReader);
                //Object obj = parser.parse(new FileReader("LCQuad_All_answer_output.json"));
                
                answerFileString = obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void periodicPoll() throws IOException {
        for (int i = 0; i < 40; i++) {
            performance(ben, MainBean.eval_benchmark, MainBean.eval_update_answers);
        }
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
        String q = question.getQuestionString();

        answer(q);

        systemAnswersList = new ArrayList<String>(new HashSet<String>(systemAnswersList));
        corectAnswersList = new ArrayList<String>(new HashSet<String>(corectAnswersList));
        
        //3- List of Questions and their (R, P, F1)
        evaluatedBenchmark.evaluatedQuestions.add(new QuestionEval(question.getQuestionString(), question, corectAnswersList, systemAnswersList));

        //}
        //if (currentQuestion >= benchmark.questions.size()) {
            //4- Calculate parameters
            evaluatedBenchmark.calculateParameters();

            //5- At the End, Print Results
            //evaluatedBenchmark.printScores();
        //}

    }

    public static ArrayList<String> answer(String question) throws IOException, JSONException {
        try {
            systemAnswersList = new ArrayList<>();

            //1- Get SPARQL Query data from File
            String queryGraph = "";
            String target_var = "";
            int queryType = 0;

            
            try {
                
                JSONArray jsonObject = new JSONArray(obj.toString());
                JSONObject questionResponse = null;

                for (Object c : jsonObject) {
                    JSONObject current = (JSONObject) c;
                    String currentQuestion = current.getString("question");

                    if (currentQuestion.trim().toLowerCase().equals(question.trim().toLowerCase())) {
                        questionResponse = current;
//                        System.out.println(current.toString());
                        break;
                    }
                }

                try {
                    JSONArray generated_queries = questionResponse.getJSONArray("generated_queries");

                    for (Object o : generated_queries) {
                        JSONObject generated_query = (JSONObject) o;
                        if (generated_query.getBoolean("correct")) {
                            queryGraph = generated_query.getString("query");
                            target_var = generated_query.getString("target_var");
                        }
                    }

                    try {
                        JSONArray query_type = questionResponse.getJSONArray("question_type");
                        queryType = query_type.getInt(0);
                    } catch (Exception ex) {
                        try {
                            queryType = questionResponse.getInt("question_type");
                        } catch (Exception e) {
                            queryType = -1;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            String sparqlQuery = "";
            if (!(queryGraph.equals(""))) {
                switch (queryType) {
                    case ASK_QUERY:
                        sparqlQuery = "ASK WHERE{ " + queryGraph + "}";
                    case COUNT_QUERY:
                        sparqlQuery = "SELECT (COUNT(DISTINCT " + target_var + ") as ?c) WHERE{ " + queryGraph + "}";
                    case OTHER_QUERY:
                        sparqlQuery = "SELECT DISTINCT " + target_var + " WHERE{ " + queryGraph + "}";
                }
            }

            try {

            } catch (Exception e) {
                sparqlQuery = "";
            }
            if (sparqlQuery == null) {
                sparqlQuery = "";
            }

            sparqlQuery = sparqlQuery.replaceAll("&lt;", "<").replaceAll("&gt;", ">");

//            System.out.println("               " + sparqlQuery);

            //2- Get Answer from DBpedia
            if (!sparqlQuery.equals("")) {
                systemAnswersList = CuratedAnswer.upToDateAnswerDBpedia(sparqlQuery, "dbpedia");
            } else {
                systemAnswersList = new ArrayList<>();
            }

        } catch (Exception eex) {
            eex.printStackTrace();
        }
        return systemAnswersList;
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
        Evaluator_QAsparqlBean.evaluatedBenchmark = evaluatedBenchmark;
    }

    public int getProgress() {
        progress = (int) (100 * ((double) currentQuestion / ben.questions.size()));
        if (currentQuestion >= ben.questions.size()) {
            progress = 100;
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
        Evaluator_QAsparqlBean.evaluatedQuestions = evaluatedQuestions;
    }

    public String getAnswerFileString() {
        return answerFileString;
    }

    public void setAnswerFileString(String answerFileString) {
        Evaluator_QAsparqlBean.answerFileString = answerFileString;
    }

    
    
}
