package ShallowAnalysis;

import DataSet.Benchmark;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author aorogat
 */
@ManagedBean
@RequestScoped
public class KeywordsComparison {

    ArrayList<Benchmark> benchmarks;
    LineChartModel model2 = new LineChartModel();

    public KeywordsComparison(ArrayList<Benchmark> benchmarks) {
        this.benchmarks = benchmarks;
        for (Benchmark benchmark : benchmarks) {
            Keywords k = new Keywords();
            ArrayList<Keyword> keywords = k.keywordsAnalysis(benchmark);
            ChartSeries patternKys = new ChartSeries();
            patternKys.setLabel(benchmark.name);
            for (Keyword keyword : keywords) {
                patternKys.set(keyword.key, keyword.relative);
            }
            model2.addSeries(patternKys);
        }

        model2.setTitle("Query Keywords");
        model2.setLegendPosition("e");
        model2.setShowPointLabels(false);
        model2.getAxes().put(AxisType.X, new CategoryAxis("Keyword"));
        Axis xAxis2 = model2.getAxis(AxisType.X);
        xAxis2.setTickAngle(-30);

        Axis yAxis2 = model2.getAxis(AxisType.Y);
        yAxis2.setMin(0);
        yAxis2.setMax(100);
    }

    public ArrayList<Benchmark> getBenchmarks() {
        return benchmarks;
    }

    public void setBenchmarks(ArrayList<Benchmark> benchmarks) {
        this.benchmarks = benchmarks;
    }

    public LineChartModel getModel2() {
        return model2;
    }

    public void setModel2(LineChartModel model2) {
        this.model2 = model2;
    }

    
    
    
}
