package NLQAnalysis;

import DataSet.Benchmark;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;
import org.primefaces.model.chart.PieChartModel;
import qa.dataStructures.Question;

/**
 *
 * @author aorogat
 */
public class NLQCategoraizer {

    ArrayList<Question> all_Qs = new ArrayList<Question>();
    ArrayList<Question> what_Qs = new ArrayList<Question>();
    ArrayList<Question> when_Qs = new ArrayList<Question>();
    ArrayList<Question> where_Qs = new ArrayList<Question>();
    ArrayList<Question> which_Qs = new ArrayList<Question>();
    ArrayList<Question> who_Qs = new ArrayList<Question>();
    ArrayList<Question> whom_Qs = new ArrayList<Question>();
    ArrayList<Question> whose_Qs = new ArrayList<Question>();
    ArrayList<Question> how_Qs = new ArrayList<Question>();
    ArrayList<Question> yes_no_Qs = new ArrayList<Question>();
    ArrayList<Question> request_Qs = new ArrayList<Question>();
    ArrayList<Question> Topical_Qs = new ArrayList<Question>();
    NLQSummary nlqSummary = new NLQSummary();
    int total;
    

    PieChartModel pieModelSelect = new PieChartModel();

    public NLQCategoraizer(Benchmark benchmark) throws IOException {
        total = benchmark.questionsWithoutDuplicates.size();
        all_Qs = benchmark.questionsWithoutDuplicates;
        what_Qs = new ArrayList<Question>();
        when_Qs = new ArrayList<Question>();
        where_Qs = new ArrayList<Question>();
        which_Qs = new ArrayList<Question>();
        who_Qs = new ArrayList<Question>();
        whom_Qs = new ArrayList<Question>();
        whose_Qs = new ArrayList<Question>();
        how_Qs = new ArrayList<Question>();
        yes_no_Qs = new ArrayList<Question>();
        request_Qs = new ArrayList<Question>();
        Topical_Qs = new ArrayList<Question>();

        pieModelSelect = new PieChartModel();

        for (Question q : all_Qs) {
            q.setQuestionString(q.getQuestionString().replaceAll("<b><u>", "").
                    replaceAll("<b><u>", "</u></b>"));
        }

        for (Question q : all_Qs) {
            String queryString = q.getQuestionQuery();
            try {
                Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
                //q.setQuestionQuery(query.toString());
                String current = query.toString();
                if (OneNLQAnalysis.whatQuestion(q.getQuestionString())) {
                    q.setQuestionString(q.getQuestionString().toLowerCase().
                            replaceFirst("what", "<b><u>What</u></b>"));
                    what_Qs.add(q);
                } else if (OneNLQAnalysis.whenQuestion(q.getQuestionString())) {
                    q.setQuestionString(q.getQuestionString().toLowerCase().
                            replaceFirst("when", "<b><u>When</u></b>"));
                    when_Qs.add(q);
                } else if (OneNLQAnalysis.whereQuestion(q.getQuestionString())) {
                    q.setQuestionString(q.getQuestionString().toLowerCase().
                            replaceFirst("where", "<b><u>Where</u></b>"));
                    where_Qs.add(q);
                } else if (OneNLQAnalysis.whichQuestion(q.getQuestionString())) {
                    q.setQuestionString(q.getQuestionString().toLowerCase().
                            replaceFirst("which", "<b><u>Which</u></b>"));
                    which_Qs.add(q);
                } else if (OneNLQAnalysis.whomQuestion(q.getQuestionString())) {
                    q.setQuestionString(q.getQuestionString().toLowerCase().
                            replaceFirst("whom", "<b><u>Whom</u></b>"));
                    whom_Qs.add(q);
                } else if (OneNLQAnalysis.whoseQuestion(q.getQuestionString())) {
                    q.setQuestionString(q.getQuestionString().toLowerCase().
                            replaceFirst("whose", "<b><u>Whose</u></b>"));
                    whose_Qs.add(q);
                } else if (OneNLQAnalysis.whoQuestion(q.getQuestionString())) {
                    q.setQuestionString(q.getQuestionString().toLowerCase().
                            replaceFirst("who", "<b><u>Who</u></b>"));
                    who_Qs.add(q);
                } else if (OneNLQAnalysis.howQuestion(q.getQuestionString())) {
                    q.setQuestionString(q.getQuestionString().toLowerCase().
                            replaceFirst("how", "<b><u>How</u></b>"));
                    how_Qs.add(q);
                } else if (OneNLQAnalysis.yesNoQuestion(q.getQuestionString())) {
                    yes_no_Qs.add(q);
                } else if (OneNLQAnalysis.requestQuestion(q.getQuestionString())) {
                    request_Qs.add(q);
                } else {
                    Topical_Qs.add(q);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                //System.out.println(queryString);
            }
        }

        pieModelSelect.set("What(" + what_Qs.size() + ")", what_Qs.size());
        pieModelSelect.set("When(" + when_Qs.size() + ")", when_Qs.size());
        pieModelSelect.set("Where(" + where_Qs.size() + ")", where_Qs.size());
        pieModelSelect.set("Which(" + which_Qs.size() + ")", which_Qs.size());
        pieModelSelect.set("Who(" + who_Qs.size() + ")", who_Qs.size());
        pieModelSelect.set("Whom(" + whom_Qs.size() + ")", whom_Qs.size());
        pieModelSelect.set("Whose(" + whose_Qs.size() + ")", whose_Qs.size());
        pieModelSelect.set("How(" + how_Qs.size() + ")", how_Qs.size());
        pieModelSelect.set("Yes/No(" + yes_no_Qs.size() + ")", yes_no_Qs.size());
        pieModelSelect.set("Request(" + request_Qs.size() + ")", request_Qs.size());
        pieModelSelect.set("Topical(" + Topical_Qs.size() + ")", Topical_Qs.size());

        pieModelSelect.setTitle("Question Type");
        pieModelSelect.setLegendPosition("e");
        pieModelSelect.setShowDataLabels(true);
        pieModelSelect.setDiameter(150);
        pieModelSelect.setShadow(false);
    }

    public ArrayList<Question> getWhat_Qs() {
        return what_Qs;
    }

    public ArrayList<Question> getWhen_Qs() {
        return when_Qs;
    }

    public ArrayList<Question> getWhere_Qs() {
        return where_Qs;
    }

    public ArrayList<Question> getWhich_Qs() {
        return which_Qs;
    }

    public ArrayList<Question> getWho_Qs() {
        return who_Qs;
    }

    public ArrayList<Question> getWhom_Qs() {
        return whom_Qs;
    }

    public ArrayList<Question> getWhose_Qs() {
        return whose_Qs;
    }

    public ArrayList<Question> getHow_Qs() {
        return how_Qs;
    }

    public ArrayList<Question> getYes_no_Qs() {
        return yes_no_Qs;
    }

    public ArrayList<Question> getRequest_Qs() {
        return request_Qs;
    }

    public ArrayList<Question> getTopical_Qs() {
        return Topical_Qs;
    }

    public PieChartModel getPieModelSelect() {
        return pieModelSelect;
    }

    public void setPieModelSelect(PieChartModel pieModelSelect) {
        this.pieModelSelect = pieModelSelect;
    }
    
    public NLQSummary getNlqSummary() {
        nlqSummary.summarys.add(new OneNLQSummary("What", what_Qs.size(), total));
        nlqSummary.summarys.add(new OneNLQSummary("When", when_Qs.size(), total));
        nlqSummary.summarys.add(new OneNLQSummary("Where", where_Qs.size(), total));
        nlqSummary.summarys.add(new OneNLQSummary("Which", which_Qs.size(), total));
        nlqSummary.summarys.add(new OneNLQSummary("Who", who_Qs.size(), total));
        nlqSummary.summarys.add(new OneNLQSummary("Whom", whom_Qs.size(), total));
        nlqSummary.summarys.add(new OneNLQSummary("Whose", whose_Qs.size(), total));
        nlqSummary.summarys.add(new OneNLQSummary("How", how_Qs.size(), total));
        nlqSummary.summarys.add(new OneNLQSummary("Yes/No", yes_no_Qs.size(), total));
        nlqSummary.summarys.add(new OneNLQSummary("Request", request_Qs.size(), total));
        nlqSummary.summarys.add(new OneNLQSummary("Topical", Topical_Qs.size(), total));
        
                
        return nlqSummary;
    }

    public void setNlqSummary(NLQSummary nlqsSummary) {
        this.nlqSummary = nlqsSummary;
    }

}
