package qa.dataStructures;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;

/**
 * The data structure for the question
 *
 * @author ahmed
 *
 */
@XmlRootElement(name = "question")
@XmlType (propOrder={"id","database","questionSource","filepath","questionString","keywords","questionQuery","answers"})
public class Question {

    private int id;
    private String questionString;
    private String questionQuery;
    private String questionSource;
    private ArrayList<String> answers;
    private String keywords;
    private String database;
    private String filePath;

    public Question() {
        answers = new ArrayList<String>();
    }

    public Question(String _questionString, String _questionQuery, String _questionSource) {
        questionString = _questionString;
        questionQuery = _questionQuery;
        questionSource = _questionSource;
        answers = new ArrayList<String>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addAnswer(String answer) {
        answers.add(answer);
    }
    
    public void addKeyword(String keyword) {
        answers.add(keyword);
    }

    public void setFilepath(String newFilePath) {
        filePath = newFilePath;
    }

    public String getFilepath() {
        return filePath;
    }

    public void setDatabase(String newdatabase) {
        database = newdatabase;
    }

    public String getDatabase() {
        return database;
    }

    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public String getQuestionQuery() {
        try{
        Query q = QueryFactory.create(questionQuery);
        return q.toString(Syntax.syntaxARQ);
        }
        catch(Exception e)
        {
            return questionQuery;
        }
    }

    public void setQuestionQuery(String questionQuery) {
        this.questionQuery = questionQuery;
    }

    public String getQuestionSource() {
        return questionSource;
    }

    public void setQuestionSource(String questionSource) {
        this.questionSource = questionSource;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    
    

    @XmlElementWrapper(name = "answers")
    @XmlElements(
            @XmlElement(name = "answer", type = String.class))
    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
    
    

}
