package ShallowAnalysis;

import DataSet.Benchmark;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author aorogat
 */
@ManagedBean
@RequestScoped
public class OperatorDistributionComparison {

    ArrayList<Benchmark> benchmarks;
    LineChartModel model2 = new LineChartModel();
    BarChartModel model3 = new BarChartModel();
    ArrayList<ArrayList<OperatorOneDistribution>> allBenchmarks = new ArrayList<>();

    public OperatorDistributionComparison(ArrayList<Benchmark> benchmarks) {
        this.benchmarks = benchmarks;
        for (Benchmark benchmark : benchmarks) {
            OperatorDistribution o = new OperatorDistribution();
            ArrayList<OperatorOneDistribution> distribution = o.analysis(benchmark);
            allBenchmarks.add(distribution);
            ChartSeries patternKys = new ChartSeries();
            patternKys.setLabel(benchmark.name);
            for (OperatorOneDistribution d : distribution) {
                patternKys.set(d.key, d.relative);
            }
            model2.addSeries(patternKys);
            model3.addSeries(patternKys);
        }

        model2.setTitle("Query Operators");
        model2.setLegendPosition("e");
        //model2.setShowPointLabels(true);
        model2.getAxes().put(AxisType.X, new CategoryAxis("Operator"));
        Axis xAxis2 = model2.getAxis(AxisType.X);
        xAxis2.setTickAngle(-30);

        Axis yAxis2 = model2.getAxis(AxisType.Y);
        yAxis2.setMin(0);
        yAxis2.setMax(100);
        
        model3.setTitle("Query Operators");
        model3.setLegendPosition("e");
        //model2.setShowPointLabels(true);
        model3.getAxes().put(AxisType.X, new CategoryAxis("Operator"));
        Axis xAxis3 = model3.getAxis(AxisType.X);
        xAxis3.setTickAngle(-30);

        Axis yAxis3 = model3.getAxis(AxisType.Y);
        yAxis3.setMin(0);
        yAxis3.setMax(100);
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

    public ArrayList<ArrayList<OperatorOneDistribution>> getAllBenchmarks() {
        return allBenchmarks;
    }

    public void setAllBenchmarks(ArrayList<ArrayList<OperatorOneDistribution>> allBenchmarks) {
        this.allBenchmarks = allBenchmarks;
    }

    

    public BarChartModel getModel3() {
        return model3;
    }

    public void setModel3(BarChartModel model3) {
        this.model3 = model3;
    }

    
    
    
}
