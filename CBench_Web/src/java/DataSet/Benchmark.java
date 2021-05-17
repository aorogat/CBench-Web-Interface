package DataSet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.io.IOUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;
import qa.dataStructures.Question;
import qa.parsers.JSONParser;
import qa.parsers.Parser;
import qa.parsers.XMLParser;

public class Benchmark {
    //////////////////////  Static numbers  //////////////////////////////////////
    public final static int QALD_1 = 1;
    public final static int QALD_2 = 2;
    public final static int QALD_3 = 3;
    public final static int QALD_4 = 4;
    public final static int QALD_5 = 5;
    public final static int QALD_6 = 6;
    public final static int QALD_7 = 7;
    public final static int QALD_8 = 8;
    public final static int QALD_9 = 9;
    
    public final static int QALD_ALL = 100;
    public final static int LC_QUAD = 101;
    public final static int GraphQuestions = 102;
    public final static int WebQuestions = 103;
    public final static int SimpleQuestions = 104;
    public final static int SimpleDBpediaQA = 105;
    public final static int Free917 = 106;
    public final static int TempQuestions = 107;
    public final static int ComQA = 108;
    public final static int ComplexQuestions = 109;
    public final static int UserDefined = 110;
    
    public final static int PropertiesDefined = 1000;
    ////////////////////////////////////////////////////////////////////
    
    public ArrayList<Question> questions = new ArrayList<>();
    public ArrayList<Question> questionsWithoutDuplicates = new ArrayList<>();
    public ArrayList<Query> queries = new ArrayList<>();
    public static String currentDirectory;


    public void parseBenchmarkFiles(int benchmarkID) throws IOException {
        questions.clear();
        questionsWithoutDuplicates.clear();
        queries.clear();
        //Dataset
        if(benchmarkID == Benchmark.QALD_1 || benchmarkID == Benchmark.QALD_ALL)
        {
            questions.addAll(Parser.parseQald1((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/1/data/dbpedia-test.xml")), "QALD-1", "dbpedia", false));
            questions.addAll(Parser.parseQald1((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/1/data/dbpedia-train.xml")), "QALD-1", "dbpedia", false));
            questions.addAll(Parser.parseQald1((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/1/data/musicbrainz-test.xml")), "QALD-1", "dbpedia", false));
            questions.addAll(Parser.parseQald1((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/1/data/musicbrainz-train.xml")), "QALD-1", "dbpedia", false));
        }
        if(benchmarkID == Benchmark.QALD_2 || benchmarkID == Benchmark.QALD_ALL)
        {
            questions.addAll(Parser.parseQald1((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-test.xml")), "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-train.xml")), "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/2/data/dbpedia-train-answers.xml")), "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-test.xml")), "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-train.xml")), "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/2/data/musicbrainz-train-answers.xml")), "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/2/data/participants-challenge.xml")), "QALD-2", "dbpedia", false));
            questions.addAll(Parser.parseQald1((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/2/data/participants-challenge-answers.xml")), "QALD-2", "dbpedia", false));
        }
        if(benchmarkID == Benchmark.QALD_3 || benchmarkID == Benchmark.QALD_ALL)
        {
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-test.xml")), "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-test-answers.xml")), "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-train.xml")), "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/3/data/dbpedia-train-answers.xml")), "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-test.xml")), "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-test-answers.xml")), "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-train.xml")), "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/3/data/esdbpedia-train-answers.xml")), "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-test.xml")), "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-test-answers.xml")), "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-train.xml")), "QALD-3", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/3/data/musicbrainz-train-answers.xml")), "QALD-3", "dbpedia", true));
        }
        if(benchmarkID == Benchmark.QALD_4 || benchmarkID == Benchmark.QALD_ALL)
        {
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_test.xml")), "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_train.xml")), "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_test_withanswers.xml")), "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/4/data/qald-4_multilingual_train_withanswers.xml")), "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_test.xml")), "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_test_withanswers.xml")), "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_train.xml")), "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/4/data/qald-4_biomedical_train_withanswers.xml")), "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4Hy((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/4/data/qald-4_hybrid_train.xml")), "QALD-4", "dbpedia", true));
            questions.addAll(XMLParser.parseQald4Hy((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/4/data/qald-4_hybrid_test_withanswers.xml")), "QALD-4", "dbpedia", true));
        }
        if(benchmarkID == Benchmark.QALD_5 || benchmarkID == Benchmark.QALD_ALL)
        {
            questions.addAll(XMLParser.parseQald5((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/5/data/qald-5_train.xml")), "QALD-5", "dbpedia"));
            questions.addAll(XMLParser.parseQald5((Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/5/data/qald-5_test.xml")), "QALD-5", "dbpedia"));
        }
        if(benchmarkID == Benchmark.QALD_6 || benchmarkID == Benchmark.QALD_ALL)
        {
            
            
            questions.addAll(JSONParser.parseQald7File2(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-multilingual.json"), StandardCharsets.UTF_8), "QALD-6", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File2(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/6/data/qald-6-test-multilingual.json"), StandardCharsets.UTF_8), "QALD-6", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File4(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-datacube.json"), StandardCharsets.UTF_8), "QALD-6", "linkedspending"));
            questions.addAll(JSONParser.parseQald7File6(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/6/data/qald-6-train-hybrid.json"), StandardCharsets.UTF_8), "QALD-6", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File6(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/6/data/qald-6-test-hybrid.json"), StandardCharsets.UTF_8), "QALD-6", "dbpedia"));
        }
        if(benchmarkID == Benchmark.QALD_7 || benchmarkID == Benchmark.QALD_ALL)
        {
            questions.addAll(JSONParser.parseQald7File2(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/7/data/qald-7-test-en-wikidata.json"), StandardCharsets.UTF_8), "QALD-7", "wikidata"));
            questions.addAll(JSONParser.parseQald7File2(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-largescale.json"), StandardCharsets.UTF_8), "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File1(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-multilingual-extended-json.json"), StandardCharsets.UTF_8), "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File2(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-en-wikidata.json"), StandardCharsets.UTF_8), "QALD-7", "wikidata"));
            questions.addAll(JSONParser.parseQald7File3(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-hybrid-extended-json.json"), StandardCharsets.UTF_8), "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File4(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-hybrid.json"), StandardCharsets.UTF_8), "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File3(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/7/data/qald-7-train-multilingual-extended-json.json"), StandardCharsets.UTF_8), "QALD-7", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File2(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/7/data/qald-7-test-multilingual.json"), StandardCharsets.UTF_8), "QALD-7", "dbpedia"));
        }
        if(benchmarkID == Benchmark.QALD_8 || benchmarkID == Benchmark.QALD_ALL)
        {
            questions.addAll(JSONParser.parseQald8File(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/8/data/qald-8-test-multilingual.json"), StandardCharsets.UTF_8), "QALD-8", "dbpedia"));
            questions.addAll(JSONParser.parseQald8File(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/8/data/qald-8-train-multilingual.json"), StandardCharsets.UTF_8), "QALD-8", "dbpedia"));
            questions.addAll(JSONParser.parseQald7File2(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/8/data/wikidata-train-7.json"), StandardCharsets.UTF_8), "QALD-8", "wikidata"));
        }
        if(benchmarkID == Benchmark.QALD_9 || benchmarkID == Benchmark.QALD_ALL)
        {
            questions.addAll(JSONParser.parseQald9File(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/QALD-master/9/data/qald-9-train-multilingual.json"), StandardCharsets.UTF_8), "QALD-9", "dbpedia"));
        }
        if(benchmarkID == Benchmark.LC_QUAD)
        {
            questions.addAll(JSONParser.parseQuADFile(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/LC-QuAD-data/test-data.json"), StandardCharsets.UTF_8), "QUAD", "dbpedia"));
            questions.addAll(JSONParser.parseQuADFile(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/SPARQL/LC-QuAD-data/train-data.json"), StandardCharsets.UTF_8), "QUAD", "dbpedia"));
            
            questions.addAll(JSONParser.parseNo(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/No_SPARQL/V1/train.json"), StandardCharsets.UTF_8), "QUAD", "dbpedia"));
            questions.addAll(JSONParser.parseNo(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/No_SPARQL/V1/test.json"), StandardCharsets.UTF_8), "QUAD", "dbpedia"));
            questions.addAll(JSONParser.parseNo(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/DBpedia/No_SPARQL/V1/valid.json"), StandardCharsets.UTF_8), "QUAD", "dbpedia"));
        }      
        if(benchmarkID == Benchmark.GraphQuestions)
        {
            questions.addAll(JSONParser.parseGra(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/Freebase/SPARQL/GraphQuestions-master/freebase13/graphquestions.testing.json"), StandardCharsets.UTF_8), "Freebase_Graph", "Freebase"));
            questions.addAll(JSONParser.parseGra(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/Freebase/SPARQL/GraphQuestions-master/freebase13/graphquestions.training.json"), StandardCharsets.UTF_8), "Freebase_Graph", "Freebase"));
        }
        if(benchmarkID == Benchmark.WebQuestions)
        {
            questions.addAll(JSONParser.parseWeb(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/Freebase/SPARQL/WebQuestionsSP/WebQSP.test.json"), StandardCharsets.UTF_8), "Freebase_Web", "Freebase"));
            questions.addAll(JSONParser.parseWeb(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/Freebase/SPARQL/WebQuestionsSP/WebQSP.test.partial.json"), StandardCharsets.UTF_8), "Freebase_Web", "Freebase"));
            questions.addAll(JSONParser.parseWeb(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/Freebase/SPARQL/WebQuestionsSP/WebQSP.train.json"), StandardCharsets.UTF_8), "Freebase_Web", "Freebase"));
            questions.addAll(JSONParser.parseWeb(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/Freebase/SPARQL/WebQuestionsSP/WebQSP.train.partial.json"), StandardCharsets.UTF_8), "Freebase_Web", "Freebase"));
        }
        
        
        //Reverse List to remain most recent
        Collections.reverse(questions);
        //Remove duplicates
        String format = "%-10s%-10s%-20s%-30s%-70s%-50s%n";
        System.out.format(format, "#\t", "Status\t", "Source\t", "File name\t", "Question\t", "Answers\t");
        System.out.format(format, "======\t", "======\t", "======\t", "=========\t", "========\t", "========\t");
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.format(format,
                    (i + 1)+"\t",
                    "Remain\t",
                    questions.get(i).getQuestionSource()+"\t",
                    questions.get(i).getFilepath().substring(questions.get(i).getFilepath().lastIndexOf("/") + 1)+"\t",
                    questions.get(i).getQuestionString()+"\t",
                    questions.get(i).getAnswers().toString()+"\t");
            for (int j = 0; j < questions.size(); j++) {
                if (q.getQuestionString().equals(questions.get(j).getQuestionString()) && i != j) {

                    System.out.format(format,
                            "-"+"\t",
                            "Removed"+"\t",
                            questions.get(j).getQuestionSource()+"\t",
                            questions.get(j).getFilepath().substring(questions.get(j).getFilepath().lastIndexOf("/") + 1)+"\t",
                            questions.get(j).getQuestionString()+"\t",
                            questions.get(j).getAnswers().toString()+"\t");
                    questions.remove(j);
                }
            }
            //System.out.println("");
            questionsWithoutDuplicates = questions;
        }

        ArrayList<String> queriesWithProblems = new ArrayList<>();
        

        //Extract the queries
        
        /*
        Some queries are not valid SPARQL 1.1
        to support non-standard SPARQL via Jena we can turn on extended syntax when we parse the query
                Query q = QueryFactory.create("Query", Syntax.syntaxARQ);
        Note that even with this turned on there are still lots of non-standard SPARQL syntactic 
        constructs that Virtuoso supports that ARQ will still reject.
        */
        
        for (int i = 0; i < questions.size(); i++) {
            String queryString = questions.get(i).getQuestionQuery();
            try {
                Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
                queries.add(query);
            } catch (Exception e) {
                queriesWithProblems.add(queryString);
            }
        }
    }
    
}
