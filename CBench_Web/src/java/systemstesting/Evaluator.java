package systemstesting;

import DataSet.Benchmark;
import DataSet.Benchmark;
import UptodatAnswers.CuratedAnswer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.jena.query.Query;
import org.json.JSONException;
import org.json.JSONObject;
import qa.dataStructures.Question;

public abstract class Evaluator {

    static ArrayList<Query> qs;
    //static ArrayList<Question> questions = Benchmark.questions;

    public static String KB = "dbpedia";
    static int benchmark;
    static String benchmarkString = "";
    static boolean updateBenchmark = false;
    static String url = "http://localhost:8080/startquestionansweringwithtextquestion";

    static ArrayList<String> systemAnswersList;
    static ArrayList<String> corectAnswersList;

    static ArrayList<QuestionEval> evaluatedQuestions;

    static Scanner in = new Scanner(System.in);

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

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static void evaluate(Evaluator evaluator) throws IOException, JSONException, Exception {
        //Read required data     
        System.out.println("        |");
        System.out.println("        ++++> Select the KG from [default, dbpedia, wikidata, freebase]");
        System.out.print("               KG is: ");
        KB = in.nextLine().trim().toLowerCase();
        System.out.println("        |");
        System.out.println("        ++++> Select the Benchmark (Enter the integer value) from");
        System.out.println("                [1- QALD_1, \t\t2- QALD_2, ..., \t\t9- QALD_9, \t\t10- QALD_ALL, ");
        System.out.println("                 11- LC-QUAD, \t\t12- WebQuestions, \t\t13- GraphQuestions, ");
        System.out.println("                 14- ComplexQuestions, \t15- ComQA, \t\t\t16- TempQuestions, ");
        System.out.println("                 17- SimpleDBpediaQA, \t18- SimpleQuestions, ");
        System.out.println("                 19- UserDefined, \t20- Properties Defined]");
        System.out.print("               Benchmark is: ");
        benchmark = in.nextInt();
        switch (benchmark) {
            case 1:
                benchmark = Benchmark.QALD_1;
                System.out.println("               ---> CBench will use the QALD_1 Benchmark.");
                benchmarkString = "QALD_1";
                break;
            case 2:
                benchmark = Benchmark.QALD_2;
                System.out.println("               ---> CBench will use the QALD_2 Benchmark.");
                benchmarkString = "QALD_2";
                break;
            case 3:
                benchmark = Benchmark.QALD_3;
                System.out.println("               ---> CBench will use the QALD_3 Benchmark.");
                benchmarkString = "QALD_3";
                break;
            case 4:
                benchmark = Benchmark.QALD_4;
                System.out.println("               ---> CBench will use the QALD_4 Benchmark.");
                benchmarkString = "QALD_4";
                break;
            case 5:
                benchmark = Benchmark.QALD_5;
                System.out.println("               ---> CBench will use the QALD_5 Benchmark.");
                benchmarkString = "QALD_5";
                break;
            case 6:
                benchmark = Benchmark.QALD_6;
                System.out.println("               ---> CBench will use the QALD_6 Benchmark.");
                benchmarkString = "QALD_6";
                break;
            case 7:
                benchmark = Benchmark.QALD_7;
                System.out.println("               ---> CBench will use the QALD_7 Benchmark.");
                benchmarkString = "QALD_7";
                break;
            case 8:
                benchmark = Benchmark.QALD_8;
                System.out.println("               ---> CBench will use the QALD_8 Benchmark.");
                benchmarkString = "QALD_8";
                break;
            case 9:
                benchmark = Benchmark.QALD_9;
                System.out.println("               ---> CBench will use the QALD_9 Benchmark.");
                benchmarkString = "QALD_9";
                break;
            case 10:
                benchmark = Benchmark.QALD_ALL;
                System.out.println("               ---> CBench will use the QALD_All Benchmark.");
                benchmarkString = "QALD_ALL";
                break;
            case 11:
                benchmark = Benchmark.LC_QUAD;
                System.out.println("               ---> CBench will use the LC-QUAD Benchmark.");
                benchmarkString = "LC-QUAD";
                break;
            case 12:
                benchmark = Benchmark.WebQuestions;
                System.out.println("               ---> CBench will use the WebQuestions Benchmark.");
                benchmarkString = "WebQuestions";
                break;
            case 13:
                benchmark = Benchmark.GraphQuestions;
                System.out.println("               ---> CBench will use the GraphQuestions Benchmark.");
                benchmarkString = "GraphQuestions";
                break;
            case 14:
                benchmark = Benchmark.ComplexQuestions;
                System.out.println("               ---> CBench will use the ComplexQuestions Benchmark.");
                benchmarkString = "ComplexQuestions";
                break;
            case 15:
                benchmark = Benchmark.ComQA;
                System.out.println("               ---> CBench will use the ComQA Benchmark.");
                benchmarkString = "ComQA";
                break;
            case 16:
                benchmark = Benchmark.TempQuestions;
                System.out.println("               ---> CBench will use the TempQuestions Benchmark.");
                benchmarkString = "TempQuestions";
                break;
            case 17:
                benchmark = Benchmark.SimpleDBpediaQA;
                System.out.println("               ---> CBench will use the SimpleDBpediaQA Benchmark.");
                benchmarkString = "SimpleDBpediaQA";
                break;
            case 18:
                benchmark = Benchmark.SimpleQuestions;
                System.out.println("               ---> CBench will use the SimpleQuestions Benchmark.");
                benchmarkString = "SimpleQuestions";
                break;
            case 19:
                benchmark = Benchmark.UserDefined;
                System.out.println("               ---> CBench will use the UserDefined Benchmark.");
                benchmarkString = "User Defined";
                break;
            case 20:
                benchmark = Benchmark.PropertiesDefined;
                System.out.println("               ---> CBench will use the PropertiesDefined Benchmark.");
                benchmarkString = "Properties Defined";
                break;
            default:
                evaluate(evaluator);
        }
        System.out.println("        |");
        System.out.println("        ++++> Would you like to update the answers from an endpoint?");
        System.out.print("               Enter  [y/n]: ");
        String update = in.next().toLowerCase().trim();
        switch (update.charAt(0)) {
            case 'y':
                updateBenchmark = true;
                System.out.println("               ---> CBench will update the answers.");
                break;
            case 'n':
                updateBenchmark = false;
                System.out.println("               ---> CBench will not update the answers.");
                break;
            default:
                evaluate(evaluator);
        }

        System.out.println("        |");
        System.out.println("        ++++> What is the thresold value for Fqi to consider a question partially correct answered?");
        System.out.print("               Enter a value between 0 and 1: ");
        BenchmarkEval.threshold = in.nextDouble();
        if (BenchmarkEval.threshold < 0 || BenchmarkEval.threshold > 1) {
            evaluate(evaluator);
        }

        performance(benchmark, benchmarkString, updateBenchmark, evaluator);
    }

    public static void performance(int benchmark, String benchmarkName, boolean curated, Evaluator evaluator) throws IOException {
        systemAnswersList = new ArrayList<>();
        corectAnswersList = new ArrayList<>();
        evaluatedQuestions = new ArrayList<>();

        //KB = "freebase";
        //qs = Benchmark.getQueriesWithoutDuplicates(benchmark);
        

        ///////////qs = Benchmark.queries;
        BenchmarkEval evaluatedBenchmark = new BenchmarkEval(benchmarkName);
        ///////////evaluatedBenchmark.allQuestions = questions.size();

        int counter = 0;
        int qsWithAnswers = 0;

        if (curated) {
            System.out.println("        |");
            System.out.println("        ++++> What is your KG endpoint URL (ex http://dbpedia.org/sparql?)?");
            System.out.print("               Endpoint is: ");
            CuratedAnswer.endpoint = in.next();
            CuratedAnswer.endpoint = CuratedAnswer.endpoint.trim().toLowerCase();
        }

        System.out.println("+");
        System.out.println("+");
        System.out.println("+");
        System.out.println("+       ++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("++++++> Collecting Correct answers and Systems answers +");
        System.out.println("        ++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("                CBench will communicate your QA");
        System.out.println("");
        System.out.print("             Press 's' then Enter to Start ...");
        in.next();
//        for (Question question : questions) {
//            corectAnswersList = new ArrayList<>();
//            counter++;
//
//            //1- Determine CorectAnswerList
//            if (curated) {
//                if (KB.equals("default")) {
//                    KB = question.getDatabase();
//                }
//
//                corectAnswersList = CuratedAnswer.upToDateAnswer(question.getQuestionQuery(), KB);
//
//            } else {
//                corectAnswersList = question.getAnswers();
//
//                for (int i = 0; i < corectAnswersList.size(); i++) {
//                    if (corectAnswersList.get(i) != null) {
//                        corectAnswersList.set(i, corectAnswersList.get(i).trim().replace('_', ' ')
//                                .replaceAll("\n", "").replaceAll("\t", "")
//                                .replace("http://dbpedia.org/resource/", "")
//                                .replace("https://en.wikipedia.org/wiki/", "")
//                                .replace("http://www.wikidata.org/entity/", ""));
//                    }
//
//                }
//                try {
//                    if (corectAnswersList != null) {
//                        if (corectAnswersList.size() == 1
//                                && (corectAnswersList.get(0) == null
//                                || corectAnswersList.get(0).equals(""))) {
//                            continue;
//                        }
//
//                    } else {
//                        continue;
//                    }
//                } catch (Exception e) {
//                    continue;
//                }
//            }
//
//            if (!KB.equals(question.getDatabase())) {
//                continue;
//            }
//
//            if (corectAnswersList == null) {
//                continue;
//            }
//            if (corectAnswersList.size() > 0) {
//                qsWithAnswers++;
//            } else {
//                continue;
//            }
//            //2- Determine systemAnswersList
//            //for (int i = 0; i < 3; i++) {
//            String q = "";
//            if (evaluator instanceof Evaluator_QAsparql
//                || evaluator instanceof Evaluator_AskNow
//                || evaluator instanceof Evaluator_File_Based) {
//                q = question.getQuestionString();
//            } else {
//                q = question.getQuestionString().replace('?', ' ').replace(" ", "%20");
//            }
//            System.out.println();
////            System.out.println("               Question number:  " + counter);
//            System.out.println("              " + counter + "- Question: " + question.getQuestionString());
//            System.out.println("               Correct Answer = " + corectAnswersList.toString());
//
//            //////////////////////////////////////////////////////////////////////////////////////////////////////////
//            if (KB.equals("default")) {
//                KB = question.getDatabase(); /////Use for multiple endpoints////////////////////dbpedia, freebase, wikidata
//            }            //////////////////////////////////////////////////////////////////////////////////////////////////////////
//            systemAnswersList = evaluator.answer(q);
//            //  if(systemAnswersList.size() == 0)
//            //    continue;
//            //else
//            //  break;
//            //  }
//            //Loading indicator
//            System.out.println("               System Answer = " + systemAnswersList.toString());
//
//            //3- List of Questions and their (R, P, F1)
//            evaluatedBenchmark.evaluatedQuestions.add(new QuestionEval(question.getQuestionString(), question, corectAnswersList, systemAnswersList));

//        }

        System.out.println("+");
        System.out.println("+");
        System.out.println("+");
        System.out.println("+       ++++++++++++++");
        System.out.println("++++++> Final Report +");
        System.out.println("        ++++++++++++++");
        System.out.print("               . Final Scores");
        //4- Calculate parameters
        evaluatedBenchmark.calculateParameters();

        //5- At the End, Print Results
        evaluatedBenchmark.printScores();

        System.out.println("\n\n\n\n\n\n\n");

    }

    public abstract ArrayList<String> answer(String question) throws IOException, JSONException;

}
