package ShallowAnalysis;

import DataSet.Benchmark;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.apache.jena.query.Query;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Abdelghny Orogat
 */
@ManagedBean
@RequestScoped
public class Keywords {

    ArrayList<Query> qs = new ArrayList<>();
    ArrayList<Keyword> kws = new ArrayList<>();
    PieChartModel pieModelSelect_Ask = new PieChartModel();

    LineChartModel model = new LineChartModel();
    LineChartModel model2 = new LineChartModel();

    int select = 0, ask = 0, describe = 0, construct = 0;

    int distinct = 0, limit = 0, offset = 0, orderBy = 0;

    int filter = 0, and = 0, union = 0, opt = 0, graph = 0, notExists = 0, minus = 0, exists = 0;

    int aggregators = 0, groupBy = 0, having = 0;
    
    int count = 0, min = 0, max = 0, 
            sum = 0, avg = 0, sample = 0, groupconcat = 0 ;

    public Keywords() {
    }

    public ArrayList<Keyword> keywordsAnalysis(Benchmark benchmark) {
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
        
        qs = benchmark.queries;

        for (Query q : benchmark.queries) {
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
//                System.out.println("not exists ==="+q.toString());
                notExists++;
            } else if (q.toString().toLowerCase().replace("\n", "").replaceAll(" ", "").contains("exists{")) {
//                System.out.println("exists ==="+q.toString());
                exists++;
            }
            if (q.toString().toLowerCase().replace("\n", "").replaceAll(" ", "").contains("minus{")) {
//                System.out.println("minus ==="+q.toString());
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
        return kws;
    }

    private void createCharts() {

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
        //yAxis.setMax(qs.size() + (qs.size() / 5));

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
        model2.setTitle("Query Keywords");
        model2.setLegendPosition("e");
        model2.setShowPointLabels(true);
        model2.getAxes().put(AxisType.X, new CategoryAxis("Keyword"));
        Axis xAxis2 = model2.getAxis(AxisType.X);
        xAxis2.setTickAngle(-30);

        Axis yAxis2 = model2.getAxis(AxisType.Y);
        yAxis2.setMin(0);
        //yAxis2.setMax(qs.size() + (qs.size() / 5));
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
        this.qs = qs;
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
        this.model = model;
    }

    public LineChartModel getModel2() {
        return model2;
    }

    public void setModel2(LineChartModel model) {
        this.model2 = model;
    }

}
