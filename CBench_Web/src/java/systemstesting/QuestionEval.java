package systemstesting;

import ShapeAnalysis.QueryProperties;
import ShapeAnalysis.QuestionByQuestionAnalysis;
import java.util.ArrayList;
import qa.dataStructures.Question;

public class QuestionEval {
    Question question;
    QueryProperties properties ;
    ArrayList<String> G; //Gold(Correct) Answers
    ArrayList<String> A; //System Answers
    int A_intersect_G_length;
    double P_q = 0;
    double R_q = 0;
    double F_q = 0;

    public QuestionEval(String questionString, Question question, ArrayList<String> G, ArrayList<String> A) {
        this.question = question;
        this.G = (ArrayList)G.clone();
        this.A = (ArrayList)A.clone();
        
        A_intersect_G_length = intersectLength(A,G);
        
        if(A.size()>0)
            P_q = A_intersect_G_length/(double)A.size();
        if(G.size()>0)
            R_q = A_intersect_G_length/(double)G.size();
        if(P_q>0 || R_q>0)
            F_q = (2*P_q*R_q)/(P_q+R_q);
        
        
        properties = new QueryProperties();
        try {
            properties = QuestionByQuestionAnalysis.QuestionQueryAnalysis(question);
        } catch (Exception e) {
            properties.type = "---";
            properties.triples = -1;
            properties.keywords = "---";
        }
        
    }

    private int intersectLength(ArrayList<String> A, ArrayList<String> G) {
        int intersectCount = 0;
        for (String g : G) {
            for (String a : A) {
                if(a.toLowerCase().equals(g.toLowerCase()))
                    intersectCount++;
            }
        }
        return intersectCount;
    }

    @Override
    public String toString() {
        properties = new QueryProperties();
        try {
            properties = QuestionByQuestionAnalysis.QuestionQueryAnalysis(question);
        } catch (Exception e) {
            properties.type = "---";
            properties.triples = -1;
            properties.keywords = "---";
        }
        String q = "R : " + R_q + " \t " + 
                         "P : " + P_q + " \t " + 
                         "F1 : " + F_q + " \t " 
                + question.getQuestionString();
//                         question.getQuestionQuery().replace("\n", "").replace("\r", "") + " \t " +
//                         properties.type + " \t " +
//                         properties.keywords + " \t " +
//                         properties.triples;
        return q;
    }

    public Question getQuestion() {
        return question;
    }

    public QueryProperties getProperties() {
        return properties;
    }

    public ArrayList<String> getG() {
        return G;
    }

    public ArrayList<String> getA() {
        return A;
    }

    public double getP_q() {
        return P_q;
    }

    public double getR_q() {
        return R_q;
    }

    public double getF_q() {
        return F_q;
    }
    
    
    
    
}
