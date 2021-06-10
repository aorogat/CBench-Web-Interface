package model;

import DataSet.Benchmark;
import NLQAnalysis.NLQCategoraizer;
import NLQAnalysis.NlqComparison;
import ShallowAnalysis.Keyword;
import ShallowAnalysis.Keywords;
import ShallowAnalysis.KeywordsComparison;
import ShallowAnalysis.NoOfTriples;
import ShallowAnalysis.NoOfTriplesComparison;
import ShallowAnalysis.OperatorDistribution;
import ShallowAnalysis.OperatorDistributionComparison;
import ShapeAnalysis.CategorizedQuestions;
import ShapeAnalysis.ShapesComparison;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import systemstesting.BenchmarkEval;
import systemstesting.EvaluatorInterface;
import systemstesting.Evaluator_NewQASystem;
import systemstesting.Evaluator_QAsparqlBean;
import systemstesting.Evaluator_WDAqua;

/**
 *
 * @author aorogat
 */
@ManagedBean
@SessionScoped
public class MainBean {

    Keywords ks = new Keywords();
    NoOfTriples nu = new NoOfTriples();
    OperatorDistribution ope = new OperatorDistribution();
    CategorizedQuestions categorizedQuestions;
    NLQCategoraizer nlqCategoraizer;
    
    KeywordsComparison keywordsComparison;
    NoOfTriplesComparison noOfTriplesComparison;
    OperatorDistributionComparison operatorDistributionComparison;
    ShapesComparison shapesComparison;
    NlqComparison nlqComparison;

    public static String knowledgebase = "-- Knowledgebase --";
    public static String benchmarkAnalysis = "-- Benchmark --";
    public static String benchmark = "-- Benchmark --";
    public static String analysisType = "-- Analysis Type --";
    public static boolean update = false;

    public ArrayList knowledgebases = new ArrayList();
    public ArrayList benchmarks = new ArrayList();
    public ArrayList analysisTypes = new ArrayList();

    public static boolean propertiesDefined = false;
    public boolean[] propertiesNLQTypes = new boolean[10];
    public boolean[] propertiesQueryShapes = new boolean[10];

    public ArrayList propertiesNLQTypesValues = new ArrayList();
    public ArrayList propertiesQueryShapesValues = new ArrayList();

    public static String eval_knowledgebase = "-- Knowledgebase --";
    public static String eval_benchmarkAnalysis = "-- Benchmark --";
    public static String eval_benchmark = "-- Benchmark --";
    public static boolean eval_Properties_defined = false;
    public static boolean eval_update_answers = false;
    public static String eval_SPARQL_URL;
    public static int eval_thresould = 0;

    public static Benchmark benchmarkData;
    public ArrayList<String> selectedBenchmarks = new ArrayList();
    ArrayList<EvaluatorInterface> qaSystems = new ArrayList<>();
    
    boolean compareToQasparql;
    boolean compareToWdaqua;
    
    String qaSystemName;
    
    LineChartModel differentThetalineChartModel = new LineChartModel();

    public MainBean() throws IOException {
        
        for (int i = 0; i < propertiesNLQTypes.length; i++) {
            propertiesNLQTypes[i] = false;
        }
        for (int i = 0; i < propertiesQueryShapes.length; i++) {
            propertiesQueryShapes[i] = false;
        }
    }

    public String evaluate() throws IOException {
        Benchmark bench = getBenchmarkData(this.eval_benchmark);
        
        qaSystems.add(new Evaluator_NewQASystem());
        
        EvaluatorInterface qasparql = new Evaluator_QAsparqlBean();
        EvaluatorInterface wdaqua = new Evaluator_WDAqua();
        
        if(compareToWdaqua)
            qaSystems.add(wdaqua);
        if(compareToQasparql)
            qaSystems.add(qasparql);
        //EvaluatorInterface wDAqua = new Evaluator_WDAqua();
        //wDAqua.evaluate(bench);
        //Evaluator_QAsparqlBean qaSparql = new Evaluator_QAsparqlBean();
        //qaSparql.evaluate(bench);
        for (EvaluatorInterface qaSystem : qaSystems) {
            qaSystem.evaluate(bench);
        }
        return "evalute.xhtml?faces-redirect=true";
    }

    public String analysis() throws IOException {
        getBenchmarkData(benchmark);
        ks.keywordsAnalysis(benchmarkData);
        nu.triplesAnalysis(benchmarkData);
        ope.analysis(benchmarkData);
        categorizedQuestions = new CategorizedQuestions(benchmarkData);
        nlqCategoraizer = new NLQCategoraizer(benchmarkData);
        return "analysis.xhtml?faces-redirect=true";
    }

    public String compare() throws IOException {
        ArrayList<Benchmark> bs = new ArrayList<>();
        for (String s : selectedBenchmarks) {
            bs.add(getBenchmarkData(s));
        }
        keywordsComparison = new KeywordsComparison(bs);
        noOfTriplesComparison = new NoOfTriplesComparison(bs);
        operatorDistributionComparison = new OperatorDistributionComparison(bs);
        shapesComparison = new ShapesComparison(bs);
        nlqComparison = new NlqComparison(bs);
        return "compare.xhtml?faces-redirect=true";
    }
    
    
    ////////////////////////////////////////////////////////////////////////////

    public String getKnowledgebase() {
        return knowledgebase;
    }

    public void setKnowledgebase(String knowledgebase) {
        this.knowledgebase = knowledgebase;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    public ArrayList<String> getKnowledgebases() {
        knowledgebases.clear();
        knowledgebases.add("-- Select KG --");
        knowledgebases.add("DBpedia");
        knowledgebases.add("Wikidata");
        knowledgebases.add("Freebase");
        knowledgebases.add("Another KG");
        return knowledgebases;
    }

    public void setKnowledgebases(ArrayList<String> knowledgebases) {
        this.knowledgebases = knowledgebases;
    }

    public ArrayList<String> getBenchmarks() {
        benchmarks.clear();
        benchmarks.add("-- Select Benchmark --");
        benchmarks.add("QALD-1");
        benchmarks.add("QALD-2");
        benchmarks.add("QALD-3");
        benchmarks.add("QALD-4");
        benchmarks.add("QALD-5");
        benchmarks.add("QALD-6");
        benchmarks.add("QALD-7");
        benchmarks.add("QALD-8");
        benchmarks.add("QALD-9");
        benchmarks.add("QALD-ALL");
        benchmarks.add("LC-QUAD");
        benchmarks.add("WebQuestions");
        benchmarks.add("GraphQuestions");
        benchmarks.add("SimpleQuestions");
        benchmarks.add("SimpleDBpediaQA");
        benchmarks.add("TempQuestions");
        benchmarks.add("ComplexQuestions");
        benchmarks.add("ComQA");
        //benchmarks.add("UserDefined");
        return benchmarks;
    }

    public void setBenchmarks(ArrayList<String> benchmarks) {
        this.benchmarks = benchmarks;
    }

    public String getBenchmarkAnalysis() {
        return benchmarkAnalysis;
    }

    public void setBenchmarkAnalysis(String benchmarkAnalysis) {
        MainBean.benchmarkAnalysis = benchmarkAnalysis;
    }

    public ArrayList<String> getAnalysisTypes() {
        analysisTypes.clear();
        analysisTypes.add("-- Analysis Type --");
        analysisTypes.add("Compact");
        analysisTypes.add("Deep");
        return analysisTypes;
    }

    public void setAnalysisTypes(ArrayList analysisTypes) {
        this.analysisTypes = analysisTypes;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        MainBean.update = update;
    }

    public boolean[] getPropertiesNLQTypes() {
        return propertiesNLQTypes;
    }

    public void setPropertiesNLQTypes(boolean[] propertiesNLQTypes) {
        this.propertiesNLQTypes = propertiesNLQTypes;
    }

    public boolean[] getPropertiesQueryShapes() {
        return propertiesQueryShapes;
    }

    public void setPropertiesQueryShapes(boolean[] propertiesQueryShapes) {
        this.propertiesQueryShapes = propertiesQueryShapes;
    }

    public ArrayList getPropertiesNLQTypesValues() {
        propertiesNLQTypesValues.clear();
        propertiesNLQTypesValues.add("What");
        propertiesNLQTypesValues.add("When");
        propertiesNLQTypesValues.add("Where");
        propertiesNLQTypesValues.add("Who");
        propertiesNLQTypesValues.add("Whom");
        propertiesNLQTypesValues.add("Whose");
        propertiesNLQTypesValues.add("Which");
        propertiesNLQTypesValues.add("How-Adj");
        propertiesNLQTypesValues.add("Yes-No");
        propertiesNLQTypesValues.add("Request");
        propertiesNLQTypesValues.add("Topical");

        return propertiesNLQTypesValues;
    }

    public void setPropertiesNLQTypesValues(ArrayList propertiesNLQTypesValues) {
        this.propertiesNLQTypesValues = propertiesNLQTypesValues;
    }

    public ArrayList getPropertiesQueryShapesValues() {
        propertiesQueryShapesValues.clear();
        propertiesQueryShapesValues.add("Single-Edge");
        propertiesQueryShapesValues.add("Chain");
        propertiesQueryShapesValues.add("Chain-Set");
        propertiesQueryShapesValues.add("Tree");
        propertiesQueryShapesValues.add("Star");
        propertiesQueryShapesValues.add("Forest");
        propertiesQueryShapesValues.add("Flower");
        propertiesQueryShapesValues.add("Flower-Set");
        propertiesQueryShapesValues.add("Cycle");

        return propertiesQueryShapesValues;
    }

    public void setPropertiesQueryShapesValues(ArrayList propertiesQueryShapesValues) {
        this.propertiesQueryShapesValues = propertiesQueryShapesValues;
    }

    public boolean isPropertiesDefined() {
        return propertiesDefined;
    }

    public void setPropertiesDefined(boolean propertiesDefined) {
        MainBean.propertiesDefined = propertiesDefined;
    }

    public String getEval_knowledgebase() {
        return eval_knowledgebase;
    }

    public void setEval_knowledgebase(String eval_knowledgebase) {
        MainBean.eval_knowledgebase = eval_knowledgebase;
    }

    public String getEval_benchmarkAnalysis() {
        return eval_benchmarkAnalysis;
    }

    public void setEval_benchmarkAnalysis(String eval_benchmarkAnalysis) {
        MainBean.eval_benchmarkAnalysis = eval_benchmarkAnalysis;
    }

    public String getEval_benchmark() {
        return eval_benchmark;
    }

    public void setEval_benchmark(String eval_benchmark) {
        MainBean.eval_benchmark = eval_benchmark;
    }

    public boolean isEval_Properties_defined() {
        return eval_Properties_defined;
    }

    public void setEval_Properties_defined(boolean eval_Properties_defined) {
        MainBean.eval_Properties_defined = eval_Properties_defined;
    }

    public boolean isEval_update_answers() {
        return eval_update_answers;
    }

    public void setEval_update_answers(boolean eval_update_answers) {
        MainBean.eval_update_answers = eval_update_answers;
    }

    public String getEval_SPARQL_URL() {
        return eval_SPARQL_URL;
    }

    public void setEval_SPARQL_URL(String eval_SPARQL_URL) {
        MainBean.eval_SPARQL_URL = eval_SPARQL_URL;
    }

    public int getEval_thresould() {
        return eval_thresould;
    }

    public void setEval_thresould(int eval_thresould) {
        MainBean.eval_thresould = eval_thresould;
    }

    int number8;
    int number9;

    public int getNumber8() {
        return number8;
    }

    public void setNumber8(int number8) {
        this.number8 = number8;
    }

    public int getNumber9() {
        return number9;
    }

    public void setNumber9(int number9) {
        this.number9 = number9;
    }

    public ArrayList getSelectedBenchmarks() {
        return selectedBenchmarks;
    }

    public void setSelectedBenchmarks(ArrayList selectedBenchmarks) {
        this.selectedBenchmarks = selectedBenchmarks;
    }

    public Keywords getKs() {
        return ks;
    }

    public void setKs(Keywords ks) {
        this.ks = ks;
    }

    public NoOfTriples getNu() {
        return nu;
    }

    public void setNu(NoOfTriples nu) {
        this.nu = nu;
    }

    public OperatorDistribution getOpe() {
        return ope;
    }

    public void setOpe(OperatorDistribution ope) {
        this.ope = ope;
    }

    public CategorizedQuestions getCategorizedQuestions() {
        return categorizedQuestions;
    }

    public void setCategorizedQuestions(CategorizedQuestions categorizedQuestions) {
        this.categorizedQuestions = categorizedQuestions;
    }

    public NLQCategoraizer getNlqCategoraizer() {
        return nlqCategoraizer;
    }

    public void setNlqCategoraizer(NLQCategoraizer nlqCategoraizer) {
        this.nlqCategoraizer = nlqCategoraizer;
    }

    public static Benchmark getBenchmarkData(String benchmarkName) {
        benchmarkData = new Benchmark();
        try {
            if (benchmarkName.equals("QALD-1")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.QALD_1);
            } else if (benchmarkName.equals("QALD-2")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.QALD_2);
            } else if (benchmarkName.equals("QALD-3")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.QALD_3);
            } else if (benchmarkName.equals("QALD-4")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.QALD_4);
            } else if (benchmarkName.equals("QALD-5")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.QALD_5);
            } else if (benchmarkName.equals("QALD-6")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.QALD_6);
            } else if (benchmarkName.equals("QALD-7")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.QALD_7);
            } else if (benchmarkName.equals("QALD-8")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.QALD_8);
            } else if (benchmarkName.equals("QALD-9")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.QALD_9);
            } else if (benchmarkName.equals("QALD-ALL")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.QALD_ALL);
            } else if (benchmarkName.equals("LC-QUAD")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.LC_QUAD);
            } else if (benchmarkName.equals("WebQuestions")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.WebQuestions);
            } else if (benchmarkName.equals("GraphQuestions")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.GraphQuestions);
            } else if (benchmarkName.equals("SimpleDBpediaQA")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.SimpleDBpediaQA);
            } else if (benchmarkName.equals("SimpleQuestions")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.SimpleQuestions);
            } else if (benchmarkName.equals("ComplexQuestions")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.ComplexQuestions);
            } else if (benchmarkName.equals("ComQA")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.ComQA);
            } else if (benchmarkName.equals("TempQuestions")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.TempQuestions);
            } else if (benchmarkName.equals("UserDefined")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.UserDefined);
            } else if (benchmarkName.equals("PropertiesDefined")) {
                benchmarkData.parseBenchmarkFiles(Benchmark.PropertiesDefined);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return benchmarkData;
    }

    public static void setBenchmarkData(Benchmark benchmarkData) {
        MainBean.benchmarkData = benchmarkData;
    }

    public KeywordsComparison getKeywordsComparison() {
        return keywordsComparison;
    }

    public void setKeywordsComparison(KeywordsComparison keywordsComparison) {
        this.keywordsComparison = keywordsComparison;
    }

    public NoOfTriplesComparison getNoOfTriplesComparison() {
        return noOfTriplesComparison;
    }

    public void setNoOfTriplesComparison(NoOfTriplesComparison noOfTriplesComparison) {
        this.noOfTriplesComparison = noOfTriplesComparison;
    }

    public OperatorDistributionComparison getOperatorDistributionComparison() {
        return operatorDistributionComparison;
    }

    public void setOperatorDistributionComparison(OperatorDistributionComparison operatorDistributionComparison) {
        this.operatorDistributionComparison = operatorDistributionComparison;
    }

    public ShapesComparison getShapesComparison() {
        return shapesComparison;
    }

    public void setShapesComparison(ShapesComparison shapesComparison) {
        this.shapesComparison = shapesComparison;
    }

    public NlqComparison getNlqComparison() {
        return nlqComparison;
    }

    public void setNlqComparison(NlqComparison nlqComparison) {
        this.nlqComparison = nlqComparison;
    }

    public ArrayList<EvaluatorInterface> getQaSystems() {
        return qaSystems;
    }

    public void setQaSystems(ArrayList<EvaluatorInterface> qaSystems) {
        this.qaSystems = qaSystems;
    }

    public boolean isCompareToQasparql() {
        return compareToQasparql;
    }

    public void setCompareToQasparql(boolean compareToQasparql) {
        this.compareToQasparql = compareToQasparql;
    }

    public boolean isCompareToWdaqua() {
        return compareToWdaqua;
    }

    public void setCompareToWdaqua(boolean compareToWdaqua) {
        this.compareToWdaqua = compareToWdaqua;
    }

    public String getQaSystemName() {
        return qaSystemName;
    }

    public void setQaSystemName(String qaSystemName) {
        this.qaSystemName = qaSystemName;
    }
    
    
    private ChartSeries buildSeries(BenchmarkEval eval, String systemName)
    {
        ChartSeries mainSystem = new ChartSeries();
        mainSystem.setLabel(systemName);
        mainSystem.set("0.0", eval.F_G(0.0001));
        mainSystem.set("0.1", eval.F_G(0.1));
        mainSystem.set("0.2", eval.F_G(0.2));
        mainSystem.set("0.3", eval.F_G(0.3));
        mainSystem.set("0.4", eval.F_G(0.4));
        mainSystem.set("0.5", eval.F_G(0.5));
        mainSystem.set("0.6", eval.F_G(0.6));
        mainSystem.set("0.7", eval.F_G(0.7));
        mainSystem.set("0.8", eval.F_G(0.8));
        mainSystem.set("0.9", eval.F_G(0.9));
        mainSystem.set("1.0", eval.F_G(1));
        
        return mainSystem;
    }

    public LineChartModel getDifferentThetalineChartModel() {
        differentThetalineChartModel = new LineChartModel();
        
        
        //Main System Series
        Evaluator_NewQASystem newQASystem = (Evaluator_NewQASystem) qaSystems.get(0);
        differentThetalineChartModel.addSeries(buildSeries(newQASystem.getEvaluatedBenchmark(), qaSystemName));
        
        //QASparql and WDAqua
        Evaluator_WDAqua wDAqua = null;
        Evaluator_QAsparqlBean qAsparqlBean;
        
        if(qaSystems.size()==2)
        {
            if(qaSystems.get(1) instanceof Evaluator_WDAqua)
            {
                wDAqua = (Evaluator_WDAqua) qaSystems.get(1);
                differentThetalineChartModel.addSeries(buildSeries(wDAqua.getEvaluatedBenchmark(), "WDAqua"));
            }
            else if(qaSystems.get(1) instanceof Evaluator_QAsparqlBean)
            {
                qAsparqlBean = (Evaluator_QAsparqlBean) qaSystems.get(1);
                differentThetalineChartModel.addSeries(buildSeries(qAsparqlBean.getEvaluatedBenchmark(), "QASparql"));
            }
        }
        
        else if(qaSystems.size()==3)
        {
            if(qaSystems.get(2) instanceof Evaluator_WDAqua)
            {
                wDAqua = (Evaluator_WDAqua) qaSystems.get(2);
                differentThetalineChartModel.addSeries(buildSeries(wDAqua.getEvaluatedBenchmark(), "WDAqua"));
            }
            else if(qaSystems.get(2) instanceof Evaluator_QAsparqlBean)
            {
                qAsparqlBean = (Evaluator_QAsparqlBean) qaSystems.get(2);
                differentThetalineChartModel.addSeries(buildSeries(qAsparqlBean.getEvaluatedBenchmark(), "QASparql"));
            }
        }
        
        
        differentThetalineChartModel.setTitle("Different Thresholds");
        differentThetalineChartModel.setLegendPosition("e");
        //model2.setShowPointLabels(true);
        differentThetalineChartModel.getAxes().put(AxisType.X, new CategoryAxis("Threshold"));
        Axis xAxis2 = differentThetalineChartModel.getAxis(AxisType.X);
        xAxis2.setTickAngle(-30);

        Axis yAxis2 = differentThetalineChartModel.getAxis(AxisType.Y);
        yAxis2.setMin(0);
        yAxis2.setMax(1);
        yAxis2.setTickCount(11);
        
        return differentThetalineChartModel;
    }

    public void setDifferentThetalineChartModel(LineChartModel differentThetalineChartModel) {
        this.differentThetalineChartModel = differentThetalineChartModel;
    }

    
    
    
    
    
    
}
