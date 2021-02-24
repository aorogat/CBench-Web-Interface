package model;

import NLQAnalysis.NLQCategoraizer;
import ShallowAnalysis.Keywords;
import ShallowAnalysis.NoOfTriples;
import ShallowAnalysis.OperatorDistribution;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author aorogat
 */
@ManagedBean
@SessionScoped
public class MainBean 
{
    public static String knowledgebase = "-- Knowledgebase --";
    public static String benchmarkAnalysis = "-- Benchmark --";
    public static String benchmark = "-- Benchmark --";
    public static String analysisType = "-- Analysis Type --";
    
    public ArrayList knowledgebases = new ArrayList();
    public ArrayList benchmarks = new ArrayList();
    public ArrayList analysisTypes = new ArrayList();

    public MainBean() 
    {
    }
    
    public String evaluate()
    {
        
        return "evalute.xhtml";
    }
    
    public String analysis() throws IOException
    {
        Keywords.keywordsAnalysis();
        NoOfTriples.triplesAnalysis();
        OperatorDistribution.analysis();
        NLQCategoraizer a = new NLQCategoraizer();
        return "analysis.xhtml";
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
        knowledgebases.add("-- Knowledgebase --");
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
        benchmarks.add("-- Benchmark --");
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
    
    
    
}
