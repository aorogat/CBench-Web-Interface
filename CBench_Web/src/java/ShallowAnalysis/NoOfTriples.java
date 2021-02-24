package ShallowAnalysis;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import model.MainBean;
import org.apache.jena.query.Query;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementVisitorBase;
import org.apache.jena.sparql.syntax.ElementWalker;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import qa.dataStructures.Question;

/**
 *
 * @author Abdelghny Orogat
 */
@ManagedBean
@RequestScoped
public class NoOfTriples {

    static ArrayList<Query> qs;
    public static int zero = 0, one = 0, two = 0, three = 0, four = 0, five = 0, six = 0, seven = 0,
            eight = 0, nine = 0, ten = 0, elevenOrMore = 0, total = 0, counter = 0;
    static LineChartModel model2 = new LineChartModel();
    static LineChartModel modelNLQ = new LineChartModel();

    public static void triplesAnalysis() {
        NumberFormat formatter = new DecimalFormat("#0.00");

        zero = 0;
        one = 0;
        two = 0;
        three = 0;
        four = 0;
        five = 0;
        six = 0;
        seven = 0;
        eight = 0;
        nine = 0;
        ten = 0;
        elevenOrMore = 0;
        total = 0;
        counter = 0;

        String benchmark = MainBean.benchmark;
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
            }
             else if (benchmark.equals("PropertiesDefined")) {
                qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.PropertiesDefined, false, true, true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Query q : qs) {

            try {
                NoOfTriples.counter = 0;
                NoOfTriples.total++;
                Element e = q.getQueryPattern();

                ElementVisitorBase visitor = new ElementVistorImpl();
                ElementWalker.walk(e, visitor);

                switch (NoOfTriples.counter) {
                    case 0:
                        NoOfTriples.zero++;
                        break;
                    case 1:
                        NoOfTriples.one++;
                        break;
                    case 2:
                        NoOfTriples.two++;
                        break;
                    case 3:
                        NoOfTriples.three++;
                        break;
                    case 4:
                        NoOfTriples.four++;
                        break;
                    case 5:
                        NoOfTriples.five++;
                        break;
                    case 6:
                        NoOfTriples.six++;
                        break;
                    case 7:
                        NoOfTriples.seven++;
                        break;
                    case 8:
                        NoOfTriples.eight++;
                        break;
                    case 9:
                        NoOfTriples.nine++;
                        break;
                    case 10:
                        NoOfTriples.ten++;
                        break;
                    default:
                        NoOfTriples.elevenOrMore++;
                        break;

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        createCharts();
    }

    private static void createCharts() {

        
        model2.clear();
        
        ChartSeries triples = new ChartSeries();
        triples.setLabel("#Triples");
        triples.set("0", zero);
        triples.set("1", one);
        triples.set("2", two);
        triples.set("3", three);
        triples.set("4", four);
        triples.set("5", five);
        triples.set("6", six);
        triples.set("7", seven);
        triples.set("8", eight);
        triples.set("9", nine);
        triples.set("10", ten);
        triples.set("11+", elevenOrMore);

        model2.addSeries(triples);
        model2.setTitle("Number of Triples");
        model2.setLegendPosition("e");
        model2.setShowPointLabels(true);
        model2.getAxes().put(AxisType.X, new CategoryAxis("Triples"));
        Axis xAxis2 = model2.getAxis(AxisType.X);
        //xAxis2.setTickAngle(-30);

        Axis yAxis2 = model2.getAxis(AxisType.Y);
        yAxis2.setMin(0);
        yAxis2.setMax(qs.size() + (qs.size() / 5));
        
        
        modelNLQ.clear();
        
        ChartSeries tokens = new ChartSeries();
        int[] tokensNum = new int[30];
        tokens.setLabel("#Tokens");
        
        for (Question qu : DataSetPreprocessing.questionsWithoutDuplicates) {
            StringTokenizer t = new StringTokenizer(qu.getQuestionString().replace("?", ""));
            tokensNum[(t.countTokens()<30)? t.countTokens():29]++;
        }
        
        int s=0;
        int max=tokensNum[0];
        for (int i = 0; i < 29; i++) {
            if(max<tokensNum[i])max=tokensNum[i];
            s+=tokensNum[i];
            tokens.set(""+i, tokensNum[i]);
            if(s>=DataSetPreprocessing.questionsWithoutDuplicates.size())
                break;
        }
        tokens.set(29+"+", tokensNum[29]);

        modelNLQ.addSeries(tokens);
        modelNLQ.setTitle("Number of Tokens");
        modelNLQ.setLegendPosition("e");
        //modelNLQ.setShowPointLabels(true);
        modelNLQ.getAxes().put(AxisType.X, new CategoryAxis("Tokens"));
        Axis xAxis3 = modelNLQ.getAxis(AxisType.X);
        //xAxis2.setTickAngle(-30);

        Axis yAxis3 = modelNLQ.getAxis(AxisType.Y);
        yAxis3.setMin(0);
        yAxis3.setMax(max+max/10);
    }

    public static ArrayList<Query> getQs() {
        return qs;
    }

    public static void setQs(ArrayList<Query> qs) {
        NoOfTriples.qs = qs;
    }

    public static int getZero() {
        return zero;
    }

    public static void setZero(int zero) {
        NoOfTriples.zero = zero;
    }

    public static int getOne() {
        return one;
    }

    public static void setOne(int one) {
        NoOfTriples.one = one;
    }

    public static int getTwo() {
        return two;
    }

    public static void setTwo(int two) {
        NoOfTriples.two = two;
    }

    public static int getThree() {
        return three;
    }

    public static void setThree(int three) {
        NoOfTriples.three = three;
    }

    public static int getFour() {
        return four;
    }

    public static void setFour(int four) {
        NoOfTriples.four = four;
    }

    public static int getFive() {
        return five;
    }

    public static void setFive(int five) {
        NoOfTriples.five = five;
    }

    public static int getSix() {
        return six;
    }

    public static void setSix(int six) {
        NoOfTriples.six = six;
    }

    public static int getSeven() {
        return seven;
    }

    public static void setSeven(int seven) {
        NoOfTriples.seven = seven;
    }

    public static int getEight() {
        return eight;
    }

    public static void setEight(int eight) {
        NoOfTriples.eight = eight;
    }

    public static int getNine() {
        return nine;
    }

    public static void setNine(int nine) {
        NoOfTriples.nine = nine;
    }

    public static int getTen() {
        return ten;
    }

    public static void setTen(int ten) {
        NoOfTriples.ten = ten;
    }

    public static int getElevenOrMore() {
        return elevenOrMore;
    }

    public static void setElevenOrMore(int elevenOrMore) {
        NoOfTriples.elevenOrMore = elevenOrMore;
    }

    public static int getTotal() {
        return total;
    }

    public static void setTotal(int total) {
        NoOfTriples.total = total;
    }

    public LineChartModel getModel2() {
        return model2;
    }

    public void setModel2(LineChartModel model2) {
        NoOfTriples.model2 = model2;
    }

    public LineChartModel getModelNLQ() {
        return modelNLQ;
    }

    public void setModelNLQ(LineChartModel modelNLQ) {
        NoOfTriples.modelNLQ = modelNLQ;
    }

    
    
}
