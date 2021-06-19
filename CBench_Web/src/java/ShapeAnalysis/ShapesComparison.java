package ShapeAnalysis;

import ShallowAnalysis.*;
import DataSet.Benchmark;
import java.io.IOException;
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
public class ShapesComparison {

    ArrayList<Benchmark> benchmarks;
    LineChartModel lineChartModel = new LineChartModel();
    BarChartModel barChartModel = new BarChartModel();
    ArrayList<ArrayList<OneShapeSummary>> allBenchmarks = new ArrayList<>();

    public ShapesComparison(ArrayList<Benchmark> benchmarks) throws IOException {
        this.benchmarks = benchmarks;
        for (Benchmark benchmark : benchmarks) {
            CategorizedQuestions k = new CategorizedQuestions(benchmark);
            ShapesSummary shapesSummaries = k.getShapesSummary();
            allBenchmarks.add(shapesSummaries.summarys);
            ChartSeries patternKys = new ChartSeries();
            patternKys.setLabel(benchmark.name);
            for (OneShapeSummary shape : shapesSummaries.summarys) {
                patternKys.set(shape.key, shape.relative);
            }
            lineChartModel.addSeries(patternKys);
            barChartModel.addSeries(patternKys);
        }

        lineChartModel.setTitle("Query Shape");
        lineChartModel.setLegendPosition("e");
        //model2.setShowPointLabels(true);
        lineChartModel.getAxes().put(AxisType.X, new CategoryAxis("Shape"));
        Axis xAxis2 = lineChartModel.getAxis(AxisType.X);
        xAxis2.setTickAngle(-30);

        Axis yAxis2 = lineChartModel.getAxis(AxisType.Y);
        yAxis2.setMin(0);
        yAxis2.setMax(100);
        
        barChartModel.setTitle("Query Shape");
        barChartModel.setLegendPosition("e");
        //model2.setShowPointLabels(true);
        barChartModel.getAxes().put(AxisType.X, new CategoryAxis("Shape"));
        Axis xAxis3 = barChartModel.getAxis(AxisType.X);
        xAxis3.setTickAngle(-30);

        Axis yAxis3 = barChartModel.getAxis(AxisType.Y);
        yAxis3.setMin(0);
        yAxis3.setMax(100);
    }

    public ArrayList<Benchmark> getBenchmarks() {
        return benchmarks;
    }

    public void setBenchmarks(ArrayList<Benchmark> benchmarks) {
        this.benchmarks = benchmarks;
    }

    public LineChartModel getLineChartModel() {
        return lineChartModel;
    }

    public void setLineChartModel(LineChartModel lineChartModel) {
        this.lineChartModel = lineChartModel;
    }

    public ArrayList<ArrayList<OneShapeSummary>> getAllBenchmarks() {
        return allBenchmarks;
    }

    public void setAllBenchmarks(ArrayList<ArrayList<OneShapeSummary>> allBenchmarks) {
        this.allBenchmarks = allBenchmarks;
    }

    

    

    public BarChartModel getBarChartModel() {
        return barChartModel;
    }

    public void setBarChartModel(BarChartModel barChartModel) {
        this.barChartModel = barChartModel;
    }

    
    
    
}
