package model;

import NLQAnalysis.NLQCategoraizer;
import ShallowAnalysis.Keywords;
import ShallowAnalysis.NoOfTriples;
import ShallowAnalysis.OperatorDistribution;
import ShapeAnalysis.CategorizedQuestions;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import systemstesting.Evaluator_WDAqua;

/**
 *
 * @author aorogat
 */
@ManagedBean
@SessionScoped
public class MainBean 
{
    Keywords ks = new Keywords();
    NoOfTriples nu = new NoOfTriples();
    OperatorDistribution ope = new OperatorDistribution();
    CategorizedQuestions categorizedQuestions;
    NLQCategoraizer nlqCategoraizer;
    
    public static String knowledgebase = "-- Knowledgebase --";
    public static String benchmarkAnalysis = "-- Benchmark --";
    public static String benchmark = "-- Benchmark --";
    public static String analysisType = "-- Analysis Type --";
    public static boolean update=false;
    
    public ArrayList knowledgebases = new ArrayList();
    public ArrayList benchmarks = new ArrayList();
    public ArrayList analysisTypes = new ArrayList();
    public ArrayList selectedBenchmarks = new ArrayList();
    
    public static boolean propertiesDefined=false;
    public boolean[] propertiesNLQTypes = new boolean[10];
    public boolean[] propertiesQueryShapes = new boolean[10];
    
    public ArrayList propertiesNLQTypesValues = new ArrayList();
    public ArrayList propertiesQueryShapesValues = new ArrayList();

    
    public static String eval_knowledgebase = "-- Knowledgebase --";
    public static String eval_benchmarkAnalysis = "-- Benchmark --";
    public static String eval_benchmark = "-- Benchmark --";
    public static boolean eval_Properties_defined=false;
    public static boolean eval_update_answers=false;
    public static String eval_SPARQL_URL;
    public static int eval_thresould=0;
    
    
    
    
    public MainBean() throws IOException 
    {
        for (int i = 0; i < propertiesNLQTypes.length; i++)
            propertiesNLQTypes[i]=false;
        for (int i = 0; i < propertiesQueryShapes.length; i++)
            propertiesQueryShapes[i]=false;
    }
    
    public String evaluate() throws IOException
    {
        Evaluator_WDAqua.evaluate();
        return "evalute.xhtml?faces-redirect=true";
    }
    
    public String analysis() throws IOException
    {
        ks.keywordsAnalysis();
        nu.triplesAnalysis();
        ope.analysis();
        categorizedQuestions = new CategorizedQuestions();
        nlqCategoraizer = new NLQCategoraizer();
        return "analysis.xhtml?faces-redirect=true";
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
    
    
    
}
