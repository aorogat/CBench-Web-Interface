package DataSet;

import ShapeAnalysis.CategorizedQuestions;
import ShapeAnalysis.QueryShapeType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;
import qa.dataStructures.Question;

/**
 *
 * @author aorogat
 */
public class StandardFormt {

    static ArrayList<Question> QALD1_Qs = new ArrayList<Question>();
    static ArrayList<Question> QALD2_Qs = new ArrayList<Question>();
    static ArrayList<Question> QALD3_Qs = new ArrayList<Question>();
    static ArrayList<Question> QALD4_Qs = new ArrayList<Question>();
    static ArrayList<Question> QALD5_Qs = new ArrayList<Question>();
    static ArrayList<Question> QALD6_Qs = new ArrayList<Question>();
    static ArrayList<Question> QALD7_Qs = new ArrayList<Question>();
    static ArrayList<Question> QALD8_Qs = new ArrayList<Question>();
    static ArrayList<Question> QALD9_Qs = new ArrayList<Question>();
    static ArrayList<Question> QALDALL_Qs = new ArrayList<Question>();

    static ArrayList<Question> LC_QUAD = new ArrayList<Question>();
    static ArrayList<Question> FREEBASE = new ArrayList<Question>();

    public StandardFormt() {

    }

    public static void main(String[] args) throws IOException {

        StandardFormt categories = new StandardFormt();
        System.out.println();

        PrintWriter writer;
        try {
            Benchmark d = new Benchmark();
            d.parseBenchmarkFiles(Benchmark.QALD_1);
            QALD1_Qs = d.questionsWithoutDuplicates;
            writer = new PrintWriter("QALD_1.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(QALD1_Qs));
            writer.close();

            d.getQueriesWithoutDuplicates(2, false, false, false);
            QALD2_Qs = d.questionsWithoutDuplicates;
            writer = new PrintWriter("QALD_2.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(QALD2_Qs));
            writer.close();

            d.getQueriesWithoutDuplicates(3, false, false, false);
            QALD3_Qs = d.questionsWithoutDuplicates;
            writer = new PrintWriter("QALD_3.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(QALD3_Qs));
            writer.close();

            d.getQueriesWithoutDuplicates(4, false, false, false);
            QALD4_Qs = d.questionsWithoutDuplicates;
            writer = new PrintWriter("QALD_4.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(QALD4_Qs));
            writer.close();

            d.getQueriesWithoutDuplicates(5, false, false, false);
            QALD5_Qs = d.questionsWithoutDuplicates;
            writer = new PrintWriter("QALD_5.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(QALD5_Qs));
            writer.close();

            d.getQueriesWithoutDuplicates(6, false, false, false);
            QALD6_Qs = d.questionsWithoutDuplicates;
            writer = new PrintWriter("QALD_6.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(QALD6_Qs));
            writer.close();

            d.getQueriesWithoutDuplicates(7, false, false, false);
            QALD7_Qs = d.questionsWithoutDuplicates;
            writer = new PrintWriter("QALD_7.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(QALD7_Qs));
            writer.close();

            d.getQueriesWithoutDuplicates(8, false, false, false);
            QALD8_Qs = d.questionsWithoutDuplicates;
            writer = new PrintWriter("QALD_8.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(QALD8_Qs));
            writer.close();

            d.getQueriesWithoutDuplicates(9, false, false, false);
            QALD9_Qs = d.questionsWithoutDuplicates;
            writer = new PrintWriter("QALD_9.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(QALD9_Qs));
            writer.close();

            d.getQueriesWithoutDuplicates(100, false, false, false);
            QALDALL_Qs = d.questionsWithoutDuplicates;
            writer = new PrintWriter("QALD_ALL.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(QALDALL_Qs));
            writer.close();

            d.getQueriesWithoutDuplicates(-1, true, false, false);
            LC_QUAD = d.questionsWithoutDuplicates;
            writer = new PrintWriter("LC-QUAD.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(LC_QUAD));
            writer.close();

            d.getQueriesWithoutDuplicates(-1, false, true, true);
            FREEBASE = d.questionsWithoutDuplicates;
            writer = new PrintWriter("FREEBASE.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(FREEBASE));
            writer.close();


        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (UnsupportedEncodingException ex) {
            System.out.println("unsupported encoding");
        }

    }

    String printQuestionsAsXML(ArrayList<Question> qs) {
        String file = ("<questions>" + "\n");
        int id = 0;
        for (Question q : qs) {
            id++;
            q.setId(id);
            file += jaxbObjectToXML(q);
        }
        file += ("</questions>" + "\n");
        return file;
    }

    private static String jaxbObjectToXML(Question question) {
        try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(Question.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty("jaxb.fragment", Boolean.TRUE);

            //Print XML String to Console
            StringWriter sw = new StringWriter();

            //Write XML to StringWriter
            jaxbMarshaller.marshal(question, sw);

            //Verify XML Content
            String xmlContent = sw.toString();
            return xmlContent;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return "";
    }

}
