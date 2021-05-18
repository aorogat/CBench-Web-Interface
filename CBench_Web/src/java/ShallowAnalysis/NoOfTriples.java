package ShallowAnalysis;

import DataSet.Benchmark;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.apache.jena.query.Query;
import org.apache.jena.sparql.syntax.Element;
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

    ArrayList<Query> qs;
    public int zero = 0, one = 0, two = 0, three = 0, four = 0, five = 0, six = 0, seven = 0,
            eight = 0, nine = 0, ten = 0, elevenOrMore = 0, total = 0;
    public static int counter = 0;
    LineChartModel model2 = new LineChartModel();
    LineChartModel modelNLQ = new LineChartModel();
    Map<String, Integer> triplesCounts = new HashMap<>();

    public Map<String, Integer> triplesAnalysis(Benchmark benchmark) {
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

        qs = benchmark.queries;
        
        for (Query q : benchmark.queries) {

            try {
                counter = 0;
                total++;
                Element e = q.getQueryPattern();

                ElementVisitorBase visitor = new ElementVistorImpl();
                ElementWalker.walk(e, visitor);

                switch (counter) {
                    case 0:
                        zero++;
                        break;
                    case 1:
                        one++;
                        break;
                    case 2:
                        two++;
                        break;
                    case 3:
                        three++;
                        break;
                    case 4:
                        four++;
                        break;
                    case 5:
                        five++;
                        break;
                    case 6:
                        six++;
                        break;
                    case 7:
                        seven++;
                        break;
                    case 8:
                        eight++;
                        break;
                    case 9:
                        nine++;
                        break;
                    case 10:
                        ten++;
                        break;
                    default:
                        elevenOrMore++;
                        break;

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        createCharts(benchmark);
        return triplesCounts;
    }

    private void createCharts(Benchmark benchmark) {

        
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
        
        triplesCounts.clear();
        triplesCounts.put("0", zero);
        triplesCounts.put("1", one);
        triplesCounts.put("2", two);
        triplesCounts.put("3", three);
        triplesCounts.put("4", four);
        triplesCounts.put("5", five);
        triplesCounts.put("6", six);
        triplesCounts.put("7", seven);
        triplesCounts.put("8", eight);
        triplesCounts.put("9", nine);
        triplesCounts.put("10", ten);
        triplesCounts.put("11+", elevenOrMore);

        model2.addSeries(triples);
        model2.setTitle("Number of Triple Patterns");
        model2.setLegendPosition("e");
        model2.setShowPointLabels(true);
        model2.getAxes().put(AxisType.X, new CategoryAxis("Triple Patterns"));
        Axis xAxis2 = model2.getAxis(AxisType.X);
        //xAxis2.setTickAngle(-30);

        Axis yAxis2 = model2.getAxis(AxisType.Y);
        yAxis2.setMin(0);
        //yAxis2.setMax(qs.size() + (qs.size() / 5));
        
        
        modelNLQ.clear();
        
        ChartSeries tokens = new ChartSeries();
        int[] tokensNum = new int[30];
        tokens.setLabel("#Tokens");
        
        for (Question qu : benchmark.questionsWithoutDuplicates) {
            StringTokenizer t = new StringTokenizer(qu.getQuestionString().replace("?", ""));
            tokensNum[(t.countTokens()<30)? t.countTokens():29]++;
        }
        
        int s=0;
        int max=tokensNum[0];
        for (int i = 0; i < 29; i++) {
            if(max<tokensNum[i])max=tokensNum[i];
            s+=tokensNum[i];
            tokens.set(""+i, tokensNum[i]);
            if(s>=benchmark.questionsWithoutDuplicates.size())
                break;
        }
        tokens.set("..+", tokensNum[29]);

        modelNLQ.addSeries(tokens);
        modelNLQ.setTitle("Number of Tokens");
        modelNLQ.setLegendPosition("e");
        //modelNLQ.setShowPointLabels(true);
        modelNLQ.getAxes().put(AxisType.X, new CategoryAxis("Tokens"));
        Axis xAxis3 = modelNLQ.getAxis(AxisType.X);
        //xAxis2.setTickAngle(-30);

        Axis yAxis3 = modelNLQ.getAxis(AxisType.Y);
        yAxis3.setMin(0);
        //yAxis3.setMax(max+max/10);
    }

    public ArrayList<Query> getQs() {
        return qs;
    }

    public void setQs(ArrayList<Query> qs) {
        qs = qs;
    }

    public int getZero() {
        return zero;
    }

    public void setZero(int zero) {
        this.zero = zero;
    }

    public int getOne() {
        return one;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public int getTwo() {
        return two;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public int getThree() {
        return three;
    }

    public void setThree(int three) {
        this.three = three;
    }

    public int getFour() {
        return four;
    }

    public void setFour(int four) {
        this.four = four;
    }

    public int getFive() {
        return five;
    }

    public void setFive(int five) {
        this.five = five;
    }

    public int getSix() {
        return six;
    }

    public void setSix(int six) {
        this.six = six;
    }

    public int getSeven() {
        return seven;
    }

    public void setSeven(int seven) {
        this.seven = seven;
    }

    public int getEight() {
        return eight;
    }

    public void setEight(int eight) {
        this.eight = eight;
    }

    public int getNine() {
        return nine;
    }

    public void setNine(int nine) {
        this.nine = nine;
    }

    public int getTen() {
        return ten;
    }

    public void setTen(int ten) {
        this.ten = ten;
    }

    public int getElevenOrMore() {
        return elevenOrMore;
    }

    public void setElevenOrMore(int elevenOrMore) {
        this.elevenOrMore = elevenOrMore;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public LineChartModel getModel2() {
        return model2;
    }

    public void setModel2(LineChartModel model2) {
        this.model2 = model2;
    }

    public LineChartModel getModelNLQ() {
        return modelNLQ;
    }

    public void setModelNLQ(LineChartModel modelNLQ) {
        this.modelNLQ = modelNLQ;
    }

    
    
}
