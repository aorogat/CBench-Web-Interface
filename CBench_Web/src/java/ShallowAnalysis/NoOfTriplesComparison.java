package ShallowAnalysis;

import DataSet.Benchmark;
import java.util.ArrayList;
import java.util.Map;
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
public class NoOfTriplesComparison {

    ArrayList<Benchmark> benchmarks;
    BarChartModel model2 = new BarChartModel();
    
    ChartSeries zeros = new ChartSeries();
    ChartSeries ones = new ChartSeries();
    ChartSeries twos = new ChartSeries();
    ChartSeries threes = new ChartSeries();
    ChartSeries fours = new ChartSeries();
    ChartSeries fives = new ChartSeries();
    ChartSeries sixs = new ChartSeries();
    ChartSeries sevens = new ChartSeries();
    ChartSeries eights = new ChartSeries();
    ChartSeries nines = new ChartSeries();
    ChartSeries tens = new ChartSeries();
    ChartSeries elevens = new ChartSeries();
    
    

    public NoOfTriplesComparison(ArrayList<Benchmark> benchmarks) {
        this.benchmarks = benchmarks;
        for (Benchmark benchmark : benchmarks) {
            NoOfTriples k = new NoOfTriples();
            Map<String, Integer> triples = k.triplesAnalysis(benchmark);
            double size = benchmark.queries.size()/(double)100;
            zeros.set(benchmark.name, triples.get("0")/size);
            ones.set(benchmark.name, triples.get("1")/size);
            twos.set(benchmark.name, triples.get("2")/size);
            threes.set(benchmark.name, triples.get("3")/size);
            fours.set(benchmark.name, triples.get("4")/size);
            fives.set(benchmark.name, triples.get("5")/size);
            sixs.set(benchmark.name, triples.get("6")/size);
            sevens.set(benchmark.name, triples.get("7")/size);
            eights.set(benchmark.name, triples.get("8")/size);
            nines.set(benchmark.name, triples.get("9")/size);
            tens.set(benchmark.name, triples.get("10")/size);
            elevens.set(benchmark.name, triples.get("11+")/size);
            
        }

        zeros.setLabel("0");
        ones.setLabel("1");
        twos.setLabel("2");
        threes.setLabel("3");
        fours.setLabel("4");
        fives.setLabel("5");
        sixs.setLabel("6");
        sevens.setLabel("7");
        eights.setLabel("8");
        nines.setLabel("9");
        tens.setLabel("10");
        elevens.setLabel("11+");
        
        
        model2.addSeries(zeros);
        model2.addSeries(ones);
        model2.addSeries(twos);
        model2.addSeries(threes);
        model2.addSeries(fours);
        model2.addSeries(fives);
        model2.addSeries(sixs);
        model2.addSeries(sevens);
        model2.addSeries(eights);
        model2.addSeries(nines);
        model2.addSeries(tens);
        model2.addSeries(elevens);
        
        model2.setTitle("Query Number Of Triple Patterns");
        model2.setLegendPosition("e");
        //model2.setShowPointLabels(true);
        model2.setStacked(true);
        model2.getAxes().put(AxisType.X, new CategoryAxis("#Triple Patterns"));
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

    public BarChartModel getModel2() {
        return model2;
    }

    public void setModel2(BarChartModel model2) {
        this.model2 = model2;
    }

    public ChartSeries getZeros() {
        return zeros;
    }

    public void setZeros(ChartSeries zeros) {
        this.zeros = zeros;
    }

    public ChartSeries getOnes() {
        return ones;
    }

    public void setOnes(ChartSeries ones) {
        this.ones = ones;
    }

    public ChartSeries getTwos() {
        return twos;
    }

    public void setTwos(ChartSeries twos) {
        this.twos = twos;
    }

    public ChartSeries getThrees() {
        return threes;
    }

    public void setThrees(ChartSeries threes) {
        this.threes = threes;
    }

    public ChartSeries getFours() {
        return fours;
    }

    public void setFours(ChartSeries fours) {
        this.fours = fours;
    }

    public ChartSeries getFives() {
        return fives;
    }

    public void setFives(ChartSeries fives) {
        this.fives = fives;
    }

    public ChartSeries getSixs() {
        return sixs;
    }

    public void setSixs(ChartSeries sixs) {
        this.sixs = sixs;
    }

    public ChartSeries getSevens() {
        return sevens;
    }

    public void setSevens(ChartSeries sevens) {
        this.sevens = sevens;
    }

    public ChartSeries getEights() {
        return eights;
    }

    public void setEights(ChartSeries eights) {
        this.eights = eights;
    }

    public ChartSeries getNines() {
        return nines;
    }

    public void setNines(ChartSeries nines) {
        this.nines = nines;
    }

    public ChartSeries getTens() {
        return tens;
    }

    public void setTens(ChartSeries tens) {
        this.tens = tens;
    }

    public ChartSeries getElevens() {
        return elevens;
    }

    public void setElevens(ChartSeries elevens) {
        this.elevens = elevens;
    }

    

    
    
    
}
