package nlptest;

import cleaning.Cleaner;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.trees.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 *
 * @author aorogat
 */
public class QA {

    public static ArrayList<String> qs = new ArrayList<>();
    public static ArrayList<String> ps = new ArrayList<>();
    public static ArrayList<String> posList = new ArrayList<>();
    public static String text = "";

    public static void main(String[] args) throws IOException {
        text = ("Give all swimmers that were born in Moscow.\n"
                + "Give me a list of all bandleaders that play trumpet.\n"
                + "Give me a list of all trumpet players that were bandleaders.\n"
                + "Give me all actors starring in movies directed by William Shatner.\n"
                + "Give me all actors who were born in Berlin.\n"
                + "Give me all B-sides of the Ramones.\n"
                + "Give me all books written by Danielle Steel.\n"
                + "Give me all Danish films.\n"
                + "Give me all Danish movies.\n"
                + "Give me all ESA astronauts.\n"
                + "Give me all films produced by Hal Roach.\n"
                + "Give me all islands that belong to Japan.\n"
                + "Give me all launch pads operated by NASA.\n"
                + "Give me all libraries established earlier than 1400.\n"
                + "Give me all Methodist politicians.\n"
                + "Give me all movies directed by Francis Ford Coppola.\n"
                + "Give me all movies with Tom Cruise.\n"
                + "Give me all people that were born in Vienna and died in Berlin.\n"
                + "Give me all soccer clubs in the Premier League.\n"
                + "Give me all Swedish holidays.\n"
                + "Give me all Swedish oceanographers.\n"
                + "Give me all the TV shows with Neil Patrick Harris.\n"
                + "Give me all video games published by Mean Hamster Software.\n"
                + "Give me all writers that won the Nobel Prize in literature.\n"
                + "Give me the birthdays of all actors of the television show Charmed.\n"
                + "Give me the capitals of all countries that the Himalayas run through.\n"
                + "Give me the grandchildren of Bruce Lee.\n"
                + "Give me the grandchildren of Elvis Presley.");
        
        //1- Cleaning the questions
        String[] qss = Cleaner.nlqsPrefixRemoval(text.split("\n"));
        text = "";
        for (String q : qss) {
            text = text + q + "\n";
        }

        // set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref,kbp,quote");
        // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
        props.setProperty("coref.algorithm", "neural");
        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // create a document object
        CoreDocument document = new CoreDocument(text);
        // annnotate the document
        pipeline.annotate(document);

        
        
        
        
        List<CoreSentence> sentences = document.sentences();
        
        
        for (CoreSentence sentence1 : sentences) {
            // constituency parse for the second sentence
            Tree constituencyParse = sentence1.constituencyParse();
            
            constituencyParse.indentedListPrint();

//            Iterator<Tree> iterator = constituencyParse.iterator();
//            while (iterator.hasNext()) {
//                System.out.println(((Tree) iterator.next()).label().toString());
//            }

            System.out.println("-----");
            
            
            //POS
            posList.add(sentence1.posTags().toString());
            
            qs.add(sentence1.text());
            ps.add(constituencyParse.toString());
            
        }

        createCSVFile(qs, ps, posList);

    }

    public static void createCSVFile(ArrayList<String> questions, ArrayList<String> parses, ArrayList<String> pos) throws IOException {
        FileWriter out = new FileWriter("ParseTrees.csv");
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
            int i = 0;
            for (String parse : parses) {
                printer.printRecord(questions.get(i), parse, pos.get(i));
                i++;
            }
        }
    }
}
