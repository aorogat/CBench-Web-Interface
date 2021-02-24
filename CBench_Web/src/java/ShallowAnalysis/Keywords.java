package ShallowAnalysis;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import model.MainBean;
import org.apache.jena.query.Query;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;
//import org.primefaces.model.charts.bubble.BubbleChartModel;

/**
 *
 * @author Abdelghny Orogat
 */
@ManagedBean
@RequestScoped
public class Keywords {

    static ArrayList<Query> qs = new ArrayList<>();
    static ArrayList<Keyword> kws = new ArrayList<>();
    static PieChartModel pieModelSelect_Ask = new PieChartModel();
    //static BubbleChartModel bubbleModel1 = new BubbleChartModel();

    static LineChartModel model = new LineChartModel();
    static LineChartModel model2 = new LineChartModel();

    static int select = 0, ask = 0, describe = 0, construct = 0;

    static int distinct = 0, limit = 0, offset = 0, orderBy = 0;

    static int filter = 0, and = 0, union = 0, opt = 0, graph = 0, notExists = 0, minus = 0, exists = 0;

    static int aggregators = 0, groupBy = 0, having = 0;
    
    static int count = 0, min = 0, max = 0, 
            sum = 0, avg = 0, sample = 0, groupconcat = 0 ;

    public Keywords() {
    }

    public static void keywordsAnalysis() {
        select = 0;
        ask = 0;
        describe = 0;
        construct = 0;

        distinct = 0;
        limit = 0;
        offset = 0;
        orderBy = 0;

        filter = 0;
        and = 0;
        union = 0;
        opt = 0;
        graph = 0;
        notExists = 0;
        minus = 0;
        exists = 0;

        aggregators = 0;
        groupBy = 0;
        having = 0;
        
        count = 0; min = 0; max = 0; 
        sum = 0; avg = 0; sample = 0; groupconcat = 0 ;
        
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
            if (q.isSelectType()) {
                select++;
            } else if (q.isAskType()) {
                ask++;
            } else if (q.isDescribeType()) {
                describe++;
            } else if (q.isConstructType()) {
                construct++;
            }

            if (q.isDistinct()) {
                distinct++;
            }
            if (q.hasLimit()) {
                limit++;
            }
            if (q.hasOffset()) {
                offset++;
            }
            if (q.hasOrderBy()) {
                orderBy++;
            }

            if (q.toString().toLowerCase().
                    replace("\n", "").replace("\r", "").replaceAll(" ", "").contains("filter(")) {
                filter++;
            }
            if (q.toString().toLowerCase().contains(" .")
                    || q.toString().toLowerCase().contains(";")) {
                and++;
            }
            if (q.toString().toLowerCase().replace("\n", "").replaceAll(" ", "").
                    replace("\n", "").replace("\r", "").contains("union{")) {
                union++;
            }
            if (q.toString().toLowerCase().replace("\n", "").replaceAll(" ", "").
                    replace("\n", "").replace("\r", "").contains("optional{")) {
                opt++;
            }
            if (q.toString().toLowerCase().replaceAll(" ", "").
                    replace("\n", "").replace("\r", "").contains("graph{")) {
                graph++;
            }
            if (q.toString().toLowerCase().replace("\n", "").replaceAll(" ", "").contains("notexists{")) {
                System.out.println("not exists ==="+q.toString());
                notExists++;
            } else if (q.toString().toLowerCase().replace("\n", "").replaceAll(" ", "").contains("exists{")) {
                System.out.println("exists ==="+q.toString());
                exists++;
            }
            if (q.toString().toLowerCase().replace("\n", "").replaceAll(" ", "").contains("minus{")) {
                System.out.println("minus ==="+q.toString());
                minus++;
            }

            if (q.hasGroupBy()) {
                groupBy++;
            }
            if (q.hasHaving()) {
                having++;
            }

            if (q.toString().toLowerCase().contains("count(")) {
                count++;
                aggregators++;
            }
            if (q.toString().toLowerCase().contains("min(")) {
                min++;
                aggregators++;
            }
            if (q.toString().toLowerCase().contains("max(")) {
                max++;
                aggregators++;
            }
            if (q.toString().toLowerCase().contains("sum(")) {
                sum++;
                aggregators++;
            }
            if (q.toString().toLowerCase().contains("avg(")) {
                avg++;
                aggregators++;
            }
            if (q.toString().toLowerCase().contains("groupconcat(")) {
                groupconcat++;
                aggregators++;
            }
            if (q.toString().toLowerCase().contains("sample(")) {
                sample++;
                aggregators++;
            }

        }
        
        kws.clear();
        kws.add(new Keyword("select", select, qs.size()));
        kws.add(new Keyword("ask", ask, qs.size()));
        kws.add(new Keyword("describe", describe, qs.size()));
        kws.add(new Keyword("construct", construct, qs.size()));
        kws.add(new Keyword("distinct", distinct, qs.size()));
        kws.add(new Keyword("limit", limit, qs.size()));
        kws.add(new Keyword("offset", offset, qs.size()));
        kws.add(new Keyword("orderBy", orderBy, qs.size()));
        kws.add(new Keyword("filter", filter, qs.size()));
        kws.add(new Keyword("and", and, qs.size()));
        kws.add(new Keyword("union", union, qs.size()));
        kws.add(new Keyword("optional", opt, qs.size()));
        kws.add(new Keyword("notExists", notExists, qs.size()));
        kws.add(new Keyword("minus", minus, qs.size()));
        kws.add(new Keyword("exists", exists, qs.size()));
        
        kws.add(new Keyword("groupBy", groupBy, qs.size()));
        kws.add(new Keyword("having", having, qs.size()));
        
        kws.add(new Keyword("aggregators", aggregators, qs.size()));
        kws.add(new Keyword("count()", count, qs.size()));
        kws.add(new Keyword("min()", min, qs.size()));
        kws.add(new Keyword("max()", max, qs.size()));
        kws.add(new Keyword("sum()", sum, qs.size()));
        kws.add(new Keyword("avg()", avg, qs.size()));
        kws.add(new Keyword("sample()", sample, qs.size()));
        kws.add(new Keyword("groupconcat()", groupconcat, qs.size()));
        
        createCharts();
    }

    private static void createCharts() {

        pieModelSelect_Ask.clear();
        model.clear();
        model2.clear();

        pieModelSelect_Ask.set("Select", select);
        pieModelSelect_Ask.set("Ask", ask);
        pieModelSelect_Ask.set("Describe", describe);
        pieModelSelect_Ask.set("Construct", construct);

        pieModelSelect_Ask.setTitle("Query Type");
        pieModelSelect_Ask.setLegendPosition("e");
        pieModelSelect_Ask.setShowDataLabels(true);
        pieModelSelect_Ask.setDiameter(150);
        pieModelSelect_Ask.setShadow(false);

        //////////////////////////////////////////
        ChartSeries modifiers = new ChartSeries();
        modifiers.setLabel("Modifiers");
        modifiers.set("distinct", distinct);
        modifiers.set("limit", limit);
        modifiers.set("offset", offset);
        modifiers.set("orderBy", orderBy);

        model.addSeries(modifiers);
        model.setTitle("Solution Sequences and Modifier");
        model.setLegendPosition("e");
        model.setShowPointLabels(true);
        model.getAxes().put(AxisType.X, new CategoryAxis("Modifier"));
        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setTickAngle(-30);
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(qs.size() + (qs.size() / 5));

        ChartSeries patternKys = new ChartSeries();
        patternKys.setLabel("Pattern Keywords");
        patternKys.set("filter", filter);
        patternKys.set("and", and);
        patternKys.set("union", union);
        patternKys.set("optional", opt);
        patternKys.set("notExists", notExists);
        patternKys.set("minus", minus);
        patternKys.set("exists", exists);
        
        patternKys.set("groupBy", groupBy);
        patternKys.set("having", having);
        patternKys.set("aggregators", aggregators);
        patternKys.set("count()", count);
        patternKys.set("min()", min);
        patternKys.set("max()", max);
        patternKys.set("sum()", sum);
        patternKys.set("avg()", avg);
        patternKys.set("sample()", sample);
        patternKys.set("groupconcat()", groupconcat);

        model2.addSeries(patternKys);
        model2.setTitle("Pattern Keywords");
        model2.setLegendPosition("e");
        model2.setShowPointLabels(true);
        model2.getAxes().put(AxisType.X, new CategoryAxis("Pattern Keywords"));
        Axis xAxis2 = model2.getAxis(AxisType.X);
        xAxis2.setTickAngle(-30);

        Axis yAxis2 = model2.getAxis(AxisType.Y);
        yAxis2.setMin(0);
        yAxis2.setMax(qs.size() + (qs.size() / 5));
    }

    public ArrayList<Keyword> getKws() {
        return kws;
    }

    public void setKws(ArrayList<Keyword> kws) {
        this.kws = kws;
    }

    public ArrayList<Query> getQs() {
        return qs;
    }

    public void setQs(ArrayList<Query> qs) {
        Keywords.qs = qs;
    }

    public PieChartModel getPieModelSelect_Ask() {
        return pieModelSelect_Ask;
    }

    public void setPieModelSelect_Ask(PieChartModel pieModelSelect_Ask) {
        this.pieModelSelect_Ask = pieModelSelect_Ask;
    }

    public LineChartModel getModel() {
        return model;
    }

    public void setModel(LineChartModel model) {
        Keywords.model = model;
    }

    public LineChartModel getModel2() {
        return model2;
    }

    public void setModel2(LineChartModel model) {
        Keywords.model2 = model;
    }

}
