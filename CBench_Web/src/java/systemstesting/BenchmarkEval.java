package systemstesting;

import ShapeAnalysis.QueryShapeType;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import visualization.FineGrained;
import model.MainBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import static systemstesting.Evaluator_WDAqua.evaluatedBenchmark;
import java.util.stream.*;
import java.util.*;
import java.util.function.*;

@ManagedBean
@SessionScoped
public class BenchmarkEval {

    //Benchmark related data
    String benchmarkName;
    int allQuestions;
    private int questionsWithCorrectAnswers;

    //System related data
    private int answeredWithThetaThreshold; //For Correectly or partially Answered
    private int answered;
    public static double threshold = 0.000001;

    public ArrayList<QuestionEval> evaluatedQuestions = new ArrayList<>();

    private ArrayList<QuestionEval> singleEdgeEvaluatedQuestions = new ArrayList<>();
    private ArrayList<QuestionEval> chainEvaluatedQuestions = new ArrayList<>();
    private ArrayList<QuestionEval> chainSetEvaluatedQuestions = new ArrayList<>();
    private ArrayList<QuestionEval> starEvaluatedQuestions = new ArrayList<>();
    private ArrayList<QuestionEval> treeEvaluatedQuestions = new ArrayList<>();
    private ArrayList<QuestionEval> forestEvaluatedQuestions = new ArrayList<>();
    private ArrayList<QuestionEval> cycleEvaluatedQuestions = new ArrayList<>();
    private ArrayList<QuestionEval> flowerEvaluatedQuestions = new ArrayList<>();
    private ArrayList<QuestionEval> flowerSetEvaluatedQuestions = new ArrayList<>();

    private DonutChartModel donutModel;
    private Set<String> propertiesSet;

    public BenchmarkEval() {
        benchmarkName = MainBean.eval_benchmark;
    }

    public BenchmarkEval(String benchmarkName) {
        this.benchmarkName = benchmarkName;
    }

    public void printScores() throws IOException {

        if (threshold <= 0) {
            threshold = 0.00000001;
        }

        Scanner in = new Scanner(System.in);
        System.out.println("       +");
        System.out.println("       +");
        System.out.println("       +");
        System.out.println("       +");
        System.out.println("       ++++> Would you like to see the individual questions evaluation (y/n)?");
        System.out.print("               Enter  [y/n]: ");
        String update = in.next().toLowerCase().trim();
        switch (update.charAt(0)) {
            case 'y':
                System.out.println("\n============== " + benchmarkName + " All Questions ====================");
                for (QuestionEval q : evaluatedQuestions) {
                    System.out.println(q.toString());
                }
                break;
            case 'n':
                break;
            default:
                break;
        }

        System.out.println("       +");
        System.out.println("       +");
        System.out.println("       +");
        System.out.println("       +");
        System.out.println("       ++++> Would you like to see the individual questions evaluation");
        System.out.println("       ++++> categorized by their shape (y/n)?");
        System.out.print("               Enter  [y/n]: ");
        update = in.next().toLowerCase().trim();
        switch (update.charAt(0)) {
            case 'y':
                System.out.println("\n\n\n\n============== " + benchmarkName + " SingleEdge Questions ====================");
                getSingleEdgeEvaluatedQuestions();
                for (QuestionEval q : singleEdgeEvaluatedQuestions) {
                    System.out.println(q.toString());
                    int ans = 0;
                    if (q.F_q >= threshold) {
                        ans = 1;
                    }
                }

                System.out.println("\n\n\n\n============== " + benchmarkName + " Chain Questions ====================");
                getChainEvaluatedQuestions();
                for (QuestionEval q : chainEvaluatedQuestions) {
                    System.out.println(q.toString());
                }

                System.out.println("\n\n\n\n============== " + benchmarkName + " Chain Set Questions ====================");
                getChainSetEvaluatedQuestions();
                for (QuestionEval q : chainSetEvaluatedQuestions) {
                    System.out.println(q.toString());
                }

                System.out.println("\n\n\n\n============== " + benchmarkName + " Star Questions ====================");
                getStarEvaluatedQuestions();
                for (QuestionEval q : starEvaluatedQuestions) {
                    System.out.println(q.toString());
                }

                System.out.println("\n\n\n\n============== " + benchmarkName + " Tree Questions ====================");
                getTreeEvaluatedQuestions();
                for (QuestionEval q : treeEvaluatedQuestions) {
                    System.out.println(q.toString());
                }

                System.out.println("\n\n\n\n============== " + benchmarkName + " Forest Questions ====================");
                getForestEvaluatedQuestions();
                for (QuestionEval q : forestEvaluatedQuestions) {
                    System.out.println(q.toString());
                }

                System.out.println("\n\n\n\n============== " + benchmarkName + " Flower Questions ====================");
                getFlowerEvaluatedQuestions();
                for (QuestionEval q : flowerEvaluatedQuestions) {
                    System.out.println(q.toString());
                }

                System.out.println("\n\n\n\n============== " + benchmarkName + " Flower Set Questions ====================");
                getFlowerSetEvaluatedQuestions();
                for (QuestionEval q : flowerSetEvaluatedQuestions) {
                    System.out.println(q.toString());
                }

                break;
            case 'n':
                break;
            default:
                break;
        }

        System.out.println("       +");
        System.out.println("       +");
        System.out.println("       +");
        System.out.println("       +");
        System.out.println("       ++++> Final Scores, Press 's' then Enter to show");
        update = in.next();

        System.out.println("\n================================ " + benchmarkName + " ================================");
        System.out.println("All Questions: " + allQuestions);
        System.out.println("System Answered: " + answered);
        System.out.println("Questions With Correct Answers: " + questionsWithCorrectAnswers);
        System.out.println("================================ " + benchmarkName + " Scores =========================");
        System.out.println("Micro Scores");
        System.out.println("\tR_Mi : " + R_Mi() + "\t" + "P_Mi : " + P_Mi() + "\t" + "F_Mi : " + F_Mi());
        System.out.println("Macro Scores");
        System.out.println("\tF_Ma : " + F_Ma());
        System.out.println("Global Scores (threshold = 1)");
        System.out.println("\tR_G : " + R_G() + "\t" + "P_MG : " + P_G() + "\t" + "F_MG : " + F_G());
        System.out.println("\tQuestions Partially Correct Answered With Theta Threshold = 1: " + answeredWithThetaThreshold);
        double s = threshold == 0.00000001 ? 0 : threshold;
        System.out.println("Global Scores (threshold = " + s + ")");
        System.out.println("\tR_G(" + s + ") : " + R_G(threshold) + "\t" + "P_MG(" + s + ") : " + P_G(threshold) + "\t" + "F_MG(" + s + ") : " + F_G(threshold));
        System.out.println("\tQuestions Partially Correct Answered With Theta Threshold = " + s + "): " + answeredWithThetaThreshold);
        System.out.println("=========================================================================================");

        //Evaluation Visualization using Python
        writePropertiesToFile("singleEdge", singleEdgeEvaluatedQuestions);
        writePropertiesToFile("chain", chainEvaluatedQuestions);
        writePropertiesToFile("chainSet", chainSetEvaluatedQuestions);
        writePropertiesToFile("tree", treeEvaluatedQuestions);
        writePropertiesToFile("star", starEvaluatedQuestions);
        writePropertiesToFile("forest", forestEvaluatedQuestions);
        writePropertiesToFile("flower", flowerEvaluatedQuestions);
        writePropertiesToFile("flowerSet", flowerSetEvaluatedQuestions);
        writePropertiesToFile("cycle", cycleEvaluatedQuestions);
        try {
            FineGrained.visualize();
        } catch (Exception e) {
            System.out.println("CBench cannot viualize the results due to missed configuration.");
            System.out.println("You can do it yourself by running the visualize.py file.");
        }
    }

    public void calculateParameters() {
        answered = 0;
        questionsWithCorrectAnswers = 0;
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            if (evaluatedQuestion.G.size() > 0) {
                questionsWithCorrectAnswers++;
                if (evaluatedQuestion.A.size() > 0) {
                    answered++;
                }
            }
        }
    }

    //Micro scores
    public double R_Mi() {
        int sumOfIntesect = 0;
        int sumOfGi = 0;
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            sumOfIntesect += evaluatedQuestion.A_intersect_G_length;
            sumOfGi += evaluatedQuestion.G.size();
        }
        if(sumOfGi<=0)
            return 0;
        return sumOfIntesect / (double) sumOfGi;
    }

    public double P_Mi() {
        int sumOfIntesect = 0;
        int sumOfAi = 0;
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            sumOfIntesect += evaluatedQuestion.A_intersect_G_length;
            sumOfAi += evaluatedQuestion.A.size();
        }
        if(sumOfAi<=0)
            return 0;
        return sumOfIntesect / (double) sumOfAi;
    }

    public double F_Mi() {
        double R = R_Mi();
        double P = P_Mi();

        if(R+P<=0)
            return 0;
        return (2 * R * P) / (R + P);
    }

    //Macro scores
    public double F_Ma() {
        double sumOfFi = 0;
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            sumOfFi += evaluatedQuestion.F_q;
        }
        if(questionsWithCorrectAnswers<=0)
            return 0;
        return sumOfFi / (double) questionsWithCorrectAnswers;
    }

    public double R_Ma() {
        double sumOfFi = 0;
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            sumOfFi += evaluatedQuestion.R_q;
        }
        if(questionsWithCorrectAnswers<=0)
            return 0;
        return sumOfFi / (double) questionsWithCorrectAnswers;
    }

    public double P_Ma() {
        double sumOfFi = 0;
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            sumOfFi += evaluatedQuestion.P_q;
        }
        if(questionsWithCorrectAnswers<=0)
            return 0;
        return sumOfFi / (double) questionsWithCorrectAnswers;
    }

    public double R_G() {
        return R_G(1);
    }

    public double P_G() {
        return P_G(1);
    }

    public double F_G() {
        evaluatedBenchmark.calculateParameters();
        return F_G(1);
    }

    //Global scores with threshold theta
    public double R_G(double theta) {
        answeredWithThetaThreshold = 0;
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            if (evaluatedQuestion.F_q >= theta) {
                answeredWithThetaThreshold++;
            }
        }
        if(questionsWithCorrectAnswers<=0)
            return 0;
        return answeredWithThetaThreshold / (double) questionsWithCorrectAnswers;
    }

    public double P_G(double theta) {
        answeredWithThetaThreshold = 0;
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            if (evaluatedQuestion.F_q >= theta) {
                answeredWithThetaThreshold++;
            }
        }
        if(answered<=0)
            return 0;
        return answeredWithThetaThreshold / (double) answered;
    }

    public double F_G(double theta) {
        evaluatedBenchmark.calculateParameters();
        double R = R_G(theta);
        double P = P_G(theta);
        if(R+P<=0)
            return 0;
        return (2 * R * P) / (R + P);
    }

    public ArrayList<QuestionEval> getSingleEdgeEvaluatedQuestions() {
        singleEdgeEvaluatedQuestions = new ArrayList<>();
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            try {
                if (QueryShapeType.isSingleEdge(evaluatedQuestion.question.getQuestionQuery())) {
                    singleEdgeEvaluatedQuestions.add(evaluatedQuestion);
                }
            } catch (Exception e) {
            }
        }

        return singleEdgeEvaluatedQuestions;
    }

    public ArrayList<QuestionEval> getChainEvaluatedQuestions() {
        chainEvaluatedQuestions = new ArrayList<>();
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            try {
                if (QueryShapeType.isChain(evaluatedQuestion.question.getQuestionQuery())) {
                    chainEvaluatedQuestions.add(evaluatedQuestion);
                }
            } catch (Exception e) {
            }
        }

        return chainEvaluatedQuestions;
    }

    public ArrayList<QuestionEval> getChainSetEvaluatedQuestions() {
        chainSetEvaluatedQuestions = new ArrayList<>();
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            try {
                if (QueryShapeType.isChainSet(evaluatedQuestion.question.getQuestionQuery())) {
                    chainSetEvaluatedQuestions.add(evaluatedQuestion);
                }
            } catch (Exception e) {
            }
        }
        return chainSetEvaluatedQuestions;
    }

    public ArrayList<QuestionEval> getStarEvaluatedQuestions() {
        starEvaluatedQuestions = new ArrayList<>();
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            try {
                if (QueryShapeType.isStar(evaluatedQuestion.question.getQuestionQuery())) {
                    starEvaluatedQuestions.add(evaluatedQuestion);
                }
            } catch (Exception e) {
            }
        }
        return starEvaluatedQuestions;
    }

    public ArrayList<QuestionEval> getTreeEvaluatedQuestions() {
        treeEvaluatedQuestions = new ArrayList<>();
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            try {
                if (QueryShapeType.isTree(evaluatedQuestion.question.getQuestionQuery())) {
                    treeEvaluatedQuestions.add(evaluatedQuestion);
                }
            } catch (Exception e) {
            }
        }
        return treeEvaluatedQuestions;
    }

    public ArrayList<QuestionEval> getForestEvaluatedQuestions() {
        forestEvaluatedQuestions = new ArrayList<>();
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            try {
                if (QueryShapeType.isForest(evaluatedQuestion.question.getQuestionQuery())) {
                    forestEvaluatedQuestions.add(evaluatedQuestion);
                }
            } catch (Exception e) {
            }
        }
        return forestEvaluatedQuestions;
    }

    public ArrayList<QuestionEval> getCycleEvaluatedQuestions() {
        cycleEvaluatedQuestions = new ArrayList<>();
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            try {
                if (QueryShapeType.isCycle(evaluatedQuestion.question.getQuestionQuery())) {
                    cycleEvaluatedQuestions.add(evaluatedQuestion);
                }
            } catch (Exception e) {
            }
        }
        return cycleEvaluatedQuestions;
    }

    public ArrayList<QuestionEval> getFlowerEvaluatedQuestions() {
        flowerEvaluatedQuestions = new ArrayList<>();
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            try {
                if (QueryShapeType.isFlower(evaluatedQuestion.question.getQuestionQuery())) {
                    flowerEvaluatedQuestions.add(evaluatedQuestion);
                }
            } catch (Exception e) {
            }
        }
        return flowerEvaluatedQuestions;
    }

    public ArrayList<QuestionEval> getFlowerSetEvaluatedQuestions() {
        flowerSetEvaluatedQuestions = new ArrayList<>();
        for (QuestionEval evaluatedQuestion : evaluatedQuestions) {
            try {
                if (QueryShapeType.isFlowerSet(evaluatedQuestion.question.getQuestionQuery())) {
                    flowerSetEvaluatedQuestions.add(evaluatedQuestion);
                }
            } catch (Exception e) {
            }
        }
        return flowerSetEvaluatedQuestions;
    }

    private void writePropertiesToFile(String fileName, ArrayList<QuestionEval> evaluatedQuestionsType) {
        FileWriter myWriter;
        try {
            myWriter = new FileWriter(fileName + ".txt");
            for (QuestionEval q : evaluatedQuestionsType) {
                int ans = 0;
                if (q.F_q >= threshold) {
                    ans = 1;
                }
                try {
                    String property = "(" + q.properties.type + ", " + q.properties.keywords.trim().replace(' ', '-') + ", T=" + q.properties.triples + ")";
                    myWriter.append(property + "\t" + ans + "\t" + (1 - ans) + "\n");
                } catch (Exception ex) {
                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred during writing the report.");
            e.printStackTrace();
        }
    }

    public ArrayList<QuestionEval> getEvaluatedQuestions() {
        return evaluatedQuestions;
    }

    public void setEvaluatedQuestions(ArrayList<QuestionEval> evaluatedQuestions) {
        this.evaluatedQuestions = evaluatedQuestions;
    }

    public String getBenchmarkName() {
        return benchmarkName;
    }

    public void setBenchmarkName(String benchmarkName) {
        this.benchmarkName = benchmarkName;
    }

    public int getAllQuestions() {
        return allQuestions;
    }

    public void setAllQuestions(int allQuestions) {
        this.allQuestions = allQuestions;
    }

    public int getQuestionsWithCorrectAnswers() {
        return questionsWithCorrectAnswers;
    }

    public void setQuestionsWithCorrectAnswers(int questionsWithCorrectAnswers) {
        this.questionsWithCorrectAnswers = questionsWithCorrectAnswers;
    }

    public int getAnsweredWithThetaThreshold() {
        return answeredWithThetaThreshold;
    }

    public void setAnsweredWithThetaThreshold(int answeredWithThetaThreshold) {
        this.answeredWithThetaThreshold = answeredWithThetaThreshold;
    }

    public int getAnswered() {
        return answered;
    }

    public void setAnswered(int answered) {
        this.answered = answered;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        BenchmarkEval.threshold = threshold;
    }

    public void createDonutModel() {
        donutModel = new DonutChartModel();

        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
        circle1.put("Brand 1", 150);
        circle1.put("Brand 2", 400);
        circle1.put("Brand 3", 200);
        circle1.put("Brand 4", 10);
        donutModel.addCircle(circle1);
    }

    private HorizontalBarChartModel single_model;
    private HorizontalBarChartModel chain_model;
    private HorizontalBarChartModel chainSet_model;
    private HorizontalBarChartModel star_model;
    private HorizontalBarChartModel tree_model;
    private HorizontalBarChartModel forest_model;
    private HorizontalBarChartModel flower_model;
    private HorizontalBarChartModel flowerSet_model;
    private HorizontalBarChartModel cycle_model;
    private HorizontalBarChartModel all_model;

    public HorizontalBarChartModel getSingle_model() {
        return generateModel(singleEdgeEvaluatedQuestions, "Single-Edge");
    }

    public HorizontalBarChartModel getChain_model() {
        return generateModel(chainEvaluatedQuestions, "Chain");
    }

    public HorizontalBarChartModel getChainSet_model() {
        return generateModel(chainSetEvaluatedQuestions, "Chain-Set");
    }

    public HorizontalBarChartModel getStar_model() {
        return generateModel(starEvaluatedQuestions, "Star");
    }

    public HorizontalBarChartModel getTree_model() {
        return generateModel(treeEvaluatedQuestions, "Tree");
    }

    public HorizontalBarChartModel getFlower_model() {
        return generateModel(flowerEvaluatedQuestions, "Flower");
    }

    public HorizontalBarChartModel getFlowerSet_model() {
        return generateModel(flowerSetEvaluatedQuestions, "Flower-Set");
    }

    public HorizontalBarChartModel getCycle_model() {
        return generateModel(cycleEvaluatedQuestions, "Cycle");
    }

    public HorizontalBarChartModel getForest_model() {
        return generateModel(forestEvaluatedQuestions, "Forest");
    }

    public HorizontalBarChartModel getAll_model() {
        return generateModel(evaluatedQuestions, "All");
    }
    
    
    
    
    private HorizontalBarChartModel generateModel(ArrayList<QuestionEval> evaluated_qs, String shape)
    {
        HorizontalBarChartModel model = new HorizontalBarChartModel();
        ArrayList<String> corr = new ArrayList<>();
        ArrayList<String> incorr = new ArrayList<>();
        for (QuestionEval q : evaluated_qs) {
            String prop = "(" + q.properties.type + ", " + q.properties.keywords + ", T=" + q.properties.triples + ")";
            if (q.F_q >= threshold) {
                corr.add(prop);
            } else {
                incorr.add(prop);
            }
        }
        propertiesSet = new HashSet<>(corr);
        propertiesSet.addAll(incorr);

        
        ChartSeries correct = new ChartSeries();
        for (String property : propertiesSet) {
            correct.set(property, count(property, corr));
        }

        ChartSeries incorrect = new ChartSeries();
        for (String property : propertiesSet) {
            incorrect.set(property, count(property, incorr));
        }

        correct.setLabel("Correct");

        incorrect.setLabel("InCorrect");

        model.addSeries(correct);

        model.addSeries(incorrect);

        model.setTitle( shape + " Questions");
        model.setLegendPosition("e");
        model.setStacked(true);

        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Frequency");
        xAxis.setMin(0);

        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Properties");
        //yAxis.setTickAngle(-45);
        return model;
    }

    public void setSingle_model(HorizontalBarChartModel single_model) {
        this.single_model = single_model;
    }

    private int count(String s, ArrayList<String> list) {
        int c = 0;
        for (String l : list) {
            if (l.equals(s)) {
                c++;
            }
        }
        return c;
    }

    public Set<String> getPropertiesSet() {
        return propertiesSet;
    }

    public void setPropertiesSet(Set<String> propertiesSet) {
        this.propertiesSet = propertiesSet;
    }

    
    
}
