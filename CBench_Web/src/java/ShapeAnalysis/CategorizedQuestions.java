/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShapeAnalysis;

import DataSet.DataSetPreprocessing;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;
import qa.dataStructures.Question;
import java.io.StringWriter;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
 
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
 

/**
 *
 * @author aorogat
 */
@ManagedBean
@RequestScoped
public class CategorizedQuestions {

    ArrayList<Question> singleShape_Qs = new ArrayList<Question>();
    ArrayList<Question> chain_Qs = new ArrayList<Question>();
    ArrayList<Question> chainSet_Qs = new ArrayList<Question>();
    ArrayList<Question> star_Qs = new ArrayList<Question>();
    ArrayList<Question> tree_Qs = new ArrayList<Question>();
    ArrayList<Question> forest_Qs = new ArrayList<Question>();
    ArrayList<Question> cycle_Qs = new ArrayList<Question>();
    ArrayList<Question> flower_Qs = new ArrayList<Question>();
    ArrayList<Question> flowerSet_Qs = new ArrayList<Question>();

    public CategorizedQuestions() throws IOException {
        DataSetPreprocessing.getQueriesWithoutDuplicates(9, false, false, false);
        for (Question q : DataSetPreprocessing.questionsWithoutDuplicates) {
            String queryString = q.getQuestionQuery();
            try {
                Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
                q.setQuestionQuery(query.toString());
                String current = query.toString();
                if (QueryShapeType.isSingleEdge(current)) {
                    singleShape_Qs.add(q);
                }
                if (QueryShapeType.isChain(current)) {
                    chain_Qs.add(q);
                }
                if (QueryShapeType.isChainSet(current)) {
                    chainSet_Qs.add(q);
                }
                if (QueryShapeType.isCycle(current)) {
                    cycle_Qs.add(q);
                }
                if (QueryShapeType.isFlower(current)) {
                    flower_Qs.add(q);
                }
                if (QueryShapeType.isFlowerSet(current)) {
                    flowerSet_Qs.add(q);
                }
                if (QueryShapeType.isForest(current)) {
                    forest_Qs.add(q);
                }
                if (QueryShapeType.isStar(current)) {
                    star_Qs.add(q);
                }
                if (QueryShapeType.isTree(current)) {
                    tree_Qs.add(q);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                //System.out.println(queryString);
            }
        }
    }

    public static void main(String[] args) throws IOException {

        CategorizedQuestions categories = new CategorizedQuestions();
        System.out.println();
        
        PrintWriter writer;
        try {
            writer = new PrintWriter("1- Qald - 9-- Single Edge questions.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(categories.singleShape_Qs));
            writer.close();
            
            writer = new PrintWriter("2- Qald - 9-- Chain questions.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(categories.chain_Qs));
            writer.close();
            
            writer = new PrintWriter("3- Qald - 9-- Chain set questions.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(categories.chainSet_Qs));
            writer.close();
           
            writer = new PrintWriter("4- Qald - 9-- Star questions.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(categories.star_Qs));
            writer.close();
            
            writer = new PrintWriter("5- Qald - 9-- Tree questions.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(categories.tree_Qs));
            writer.close();
            
            writer = new PrintWriter("6- Qald - 9-- Forest questions.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(categories.forest_Qs));
            writer.close();
            
            writer = new PrintWriter("7- Qald - 9-- Cycle questions.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(categories.cycle_Qs));
            writer.close();
            
             
            writer = new PrintWriter("8- Qald - 9-- Flower questions.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(categories.flower_Qs));
            writer.close();
            
            writer = new PrintWriter("9- Qald - 9-- Flowwerset questions.xml", "UTF-8");
            writer.println(categories.printQuestionsAsXML(categories.flowerSet_Qs));
            writer.close();
            
            
        } catch (FileNotFoundException ex) {
        } catch (UnsupportedEncodingException ex) {
        }
        
    }

    String printQuestionsAsXML(ArrayList<Question> qs) {
        String file = ("<questions>"  + "\n");
        int id = 0;
        for (Question q : qs) {
            id++;
            q.setId(id);
            file += jaxbObjectToXML(q);
        }
        file += ("</questions>"  + "\n");
        return file;
    }

    
    private static String jaxbObjectToXML(Question question) 
    {
        try
        {
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

    public ArrayList<Question> getSingleShape_Qs() {
        return singleShape_Qs;
    }

    public void setSingleShape_Qs(ArrayList<Question> singleShape_Qs) {
        this.singleShape_Qs = singleShape_Qs;
    }

    public ArrayList<Question> getChain_Qs() {
        return chain_Qs;
    }

    public void setChain_Qs(ArrayList<Question> chain_Qs) {
        this.chain_Qs = chain_Qs;
    }

    public ArrayList<Question> getChainSet_Qs() {
        return chainSet_Qs;
    }

    public void setChainSet_Qs(ArrayList<Question> chainSet_Qs) {
        this.chainSet_Qs = chainSet_Qs;
    }

    public ArrayList<Question> getStar_Qs() {
        return star_Qs;
    }

    public void setStar_Qs(ArrayList<Question> star_Qs) {
        this.star_Qs = star_Qs;
    }

    public ArrayList<Question> getTree_Qs() {
        return tree_Qs;
    }

    public void setTree_Qs(ArrayList<Question> tree_Qs) {
        this.tree_Qs = tree_Qs;
    }

    public ArrayList<Question> getForest_Qs() {
        return forest_Qs;
    }

    public void setForest_Qs(ArrayList<Question> forest_Qs) {
        this.forest_Qs = forest_Qs;
    }

    public ArrayList<Question> getCycle_Qs() {
        return cycle_Qs;
    }

    public void setCycle_Qs(ArrayList<Question> cycle_Qs) {
        this.cycle_Qs = cycle_Qs;
    }

    public ArrayList<Question> getFlower_Qs() {
        return flower_Qs;
    }

    public void setFlower_Qs(ArrayList<Question> flower_Qs) {
        this.flower_Qs = flower_Qs;
    }

    public ArrayList<Question> getFlowerSet_Qs() {
        return flowerSet_Qs;
    }

    public void setFlowerSet_Qs(ArrayList<Question> flowerSet_Qs) {
        this.flowerSet_Qs = flowerSet_Qs;
    }
    
    
    
    
    
}
