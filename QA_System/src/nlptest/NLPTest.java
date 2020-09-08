package nlptest;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
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
public class NLPTest {

    public static ArrayList<String> qs = new ArrayList<>();
    public static ArrayList<String> ps = new ArrayList<>();
    public static String text = ""
            + "Did Arnold Schwarzenegger attend a university?\n"
            + "Do Prince Harry and Prince William have the same parents?\n"
            + "Do Urdu and Persian have a common root?\n"
            + "Does Abraham Lincoln's death place have a website?\n"
            + "Does the Ford Motor Company have a manufacturing plant in Malaysia?\n"
            + "Does the Isar flow into a lake?\n"
            + "VB    NP     NP  ------> 2nd level\n"
            + "Give all swimmers that were born in Moscow.\n"
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
            + "Give me the grandchildren of Elvis Presley.\n"
            + "How heavy is Jupiter's lightest moon?\n"
            + "How many airlines are members of the Star Alliance?\n"
            + "How many films did Leonardo DiCaprio star in?\n"
            + "How many inhabitants does the largest city in Canada have?\n"
            + "How many languages are spoken in Colombia?\n"
            + "How many museums does Paris have?\n"
            + "How many people live in the capital of Australia?\n"
            + "How many politicians graduated from Columbia University?\n"
            + "In what city is the Heineken brewery?\n"
            + "In which city are the headquarters of the United Nations?\n"
            + "In which city did John F. Kennedy die?\n"
            + "In which city did Nikos Kazantzakis die?\n"
            + "In which city does Sylvester Stallone live?\n"
            + "In which city was the former Dutch queen Juliana buried?\n"
            + "In which countries do people speak Japanese?\n"
            + "In which U.S. state is Area 51 located?\n"
            + "In which U.S. state is Fort Knox located?\n"
            + "In which UK city are the headquarters of the MI6?\n"
            + "Is Egypts largest city also its capital?\n"
            + "Is the wife of president Obama called Michelle?\n"
            + "Is there a video game called Battle Chess?\n"
            + "List all episodes of the first season of the HBO television series The Sopranos!\n"
            + "List all episodes of the first season of the HBO television series The Sopranos.\n"
            + "List all the musicals with music by Elton John.\n"
            + "List all the musicals with music by Leonard Bernstein.\n"
            + "Show a list of soccer clubs that play in the Bundesliga.\n"
            + "Show me all basketball players that are higher than 2 meters.\n"
            + "Show me all museums in London.\n"
            + "Show me Hemingway's autobiography.\n"
            + "Show me the book that Muhammad Ali wrote.\n"
            + "To which artistic movement did the painter of The Three Dancers belong?\n"
            + "To which party does the mayor of Paris belong?\n"
            + "Was Natalie Portman born in the United States?\n"
            + "Was the wife of president Lincoln called Mary?\n"
            + "What are the top-10 action role-playing video games according to IGN?\n"
            + "What country is Mount Everest in?\n"
            + "What is the bridge with the longest span?\n"
            + "What is the highest mountain in Saxony Germany?\n"
            + "What is the highest mountain?\n"
            + "What is the largest country in the world?\n"
            + "What is the longest river in the world?\n"
            + "What is the second highest mountain on Earth?\n"
            + "What is the smallest city by area in Germany?\n"
            + "What movies does Jesse Eisenberg play in?\n"
            + "Where did the architect of the Eiffel Tower study?\n"
            + "Where in France is sparkling wine produced?\n"
            + "Where is Sungkyunkwan University?\n"
            + "Which beer originated in Ireland?\n"
            + "Which bridges cross the Seine?\n"
            + "Which caves have more than 3 entrances?\n"
            + "Which city has the least inhabitants?\n"
            + "Which city has the most inhabitants?\n"
            + "Which companies produce hovercrafts?\n"
            + "Which countries have more than two official languages?\n"
            + "Which countries have places with more than two caves?\n"
            + "Which country does the creator of Miffy come from?\n"
            + "Which movies star both Liz Taylor and Richard Burton?\n"
            + "Which music albums contain the song Last Christmas?\n"
            + "Which of Tim Burton's films had the highest budget?\n"
            + "Which other weapons did the designer of the Uzi develop?\n"
            + "Which people were born in Heraklion?\n"
            + "Which presidents were born in 1945?\n"
            + "Which programming languages influenced Javascript?\n"
            + "Which rockets were launched from Baikonur?\n"
            + "Which soccer players were born on Malta?\n"
            + "Which state of the USA has the highest population density?\n"
            + "Which television shows were created by John Cleese?\n"
            + "Which television shows were created by Walt Disney?\n"
            + "Which U.S. states are in the same time zone as Utah?\n"
            + "Which U.S. states are in the same timezone as Utah?\n"
            + "Which U.S. states possess gold minerals?\n"
            + "Which weapons did Heckler & Koch develop?\n"
            + "Who is 8th president of US?\n"
            + "Who is the daughter of Ingrid Bergman married to?\n"
            + "Who is the Formula 1 race driver with the most races?\n"
            + "Who is the king of the Netherlands?\n"
            + "Who is the mayor of the capital of French Polynesia?\n"
            + "Who is the oldest child of Meryl Streep?\n"
            + "Who is the president of Eritrea?\n"
            + "Who is the son of Sonny and Cher?\n"
            + "Who is the tallest basketball player?\n"
            + "Who is the tallest player of the Atlanta Falcons?\n"
            + "Who is the youngest Darts player?\n"
            + "Who killed John Lennon?\n"
            + "Who was president of Pakistan in 1978?\n"
            + "Who was the pope that founded the Vatican Television?";

//      public static String text = "Joe Smith was born in California. " +
//      "In 2017, he went to Paris, France in the summer. " +
//      "His flight left at 3:00pm on July 10th, 2017. " +
//      "After eating some escargot for the first time, Joe said, \"That was delicious!\" " +
//      "He sent a postcard to his sister Jane Smith. " +
//      "After hearing about Joe's trip, Jane decided she might go to France one day.";
    public static void main(String[] args) throws IOException {
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
    // examples

//    // 10th token of the document
//    CoreLabel token = document.tokens().get(10);
//    System.out.println("Example: token");
//    System.out.println(token);
//    System.out.println();
        // text of the first sentence
        String sentenceText = document.sentences().get(0).text();
        System.out.println("Example: sentence");
        System.out.println(sentenceText);
        System.out.println();

        // second sentence
        CoreSentence sentence = document.sentences().get(0);

        // list of the part-of-speech tags for the second sentence
//    List<String> posTags = sentence.posTags();
//    System.out.println("Example: pos tags");
//    System.out.println(posTags);
//    System.out.println();
//
//    // list of the ner tags for the second sentence
//    List<String> nerTags = sentence.nerTags();
//    System.out.println("Example: ner tags");
//    System.out.println(nerTags);
//    System.out.println();
//
        List<CoreSentence> sentences = document.sentences();
        for (CoreSentence sentence1 : sentences) {
            // constituency parse for the second sentence
            Tree constituencyParse = sentence1.constituencyParse();
//            System.out.println("Example: constituency parse: " + sentence1.text());
//            System.out.println(constituencyParse);
            constituencyParse.indentedListPrint();
            
            Iterator<Tree> iterator = constituencyParse.iterator();
//            while (iterator.hasNext()) {
//                System.out.println(((Tree) iterator.next()).label().toString());
//            }

            System.out.println("-----");
//            System.out.println();
//            System.out.println();
            qs.add(sentence1.text());
            ps.add(constituencyParse.toString());
        }
        
//        createCSVFile(qs, ps);

//    // dependency parse for the second sentence
//    SemanticGraph dependencyParse = sentence.dependencyParse();
//    System.out.println("Example: dependency parse");
//    System.out.println(dependencyParse);
//    System.out.println();
//
//    // kbp relations found in fifth sentence
//    List<RelationTriple> relations =
//        document.sentences().get(4).relations();
//    System.out.println("Example: relation");
//    System.out.println(relations.get(0));
//    System.out.println();
//
//    // entity mentions in the second sentence
//    List<CoreEntityMention> entityMentions = sentence.entityMentions();
//    System.out.println("Example: entity mentions");
//    System.out.println(entityMentions);
//    System.out.println();
//
//    // coreference between entity mentions
//    CoreEntityMention originalEntityMention = document.sentences().get(3).entityMentions().get(1);
//    System.out.println("Example: original entity mention");
//    System.out.println(originalEntityMention);
//    System.out.println("Example: canonical entity mention");
//    System.out.println(originalEntityMention.canonicalEntityMention().get());
//    System.out.println();
//
//    // get document wide coref info
//    Map<Integer, CorefChain> corefChains = document.corefChains();
//    System.out.println("Example: coref chains for document");
//    System.out.println(corefChains);
//    System.out.println();
//
//    // get quotes in document
//    List<CoreQuote> quotes = document.quotes();
//    CoreQuote quote = quotes.get(0);
//    System.out.println("Example: quote");
//    System.out.println(quote);
//    System.out.println();
//
//    // original speaker of quote
//    // note that quote.speaker() returns an Optional
//    System.out.println("Example: original speaker of quote");
//    System.out.println(quote.speaker().get());
//    System.out.println();
//
//    // canonical speaker of quote
//    System.out.println("Example: canonical speaker of quote");
//    System.out.println(quote.canonicalSpeaker().get());
//    System.out.println();
    }

    public static void createCSVFile(ArrayList<String> questions, ArrayList<String> parses) throws IOException {
        FileWriter out = new FileWriter("ParseTrees.csv");
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
            int i = 0;
            for (String parse : parses) {
                printer.printRecord(questions.get(i++), parse);
            }
        }
    }
}
