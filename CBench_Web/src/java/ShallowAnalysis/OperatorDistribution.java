package ShallowAnalysis;

import DataSet.Benchmark;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import model.MainBean;
import org.apache.jena.query.Query;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author Abdelghny Orogat
 */
@ManagedBean
@RequestScoped
public class OperatorDistribution {

    ArrayList<Query> qs;
    ArrayList<OperatorOneDistribution> distributions = new ArrayList<>();

    public int none = 0, F = 0, A = 0, AF = 0, CPF = 0,
            O = 0, OF = 0, AO = 0, AOF = 0, CPF_O = 0,
            G = 0, CPF_G = 0,
            U = 0, UF = 0, AU = 0, AUF = 0, CPF_U = 0,
            FGP = 0, FGU = 0, FAG = 0, AOUF = 0, AOUFG = 0;

    LineChartModel model2 = new LineChartModel();

    public void analysis(Benchmark benchmark) {
        NumberFormat formatter = new DecimalFormat("#0.00");

        none = 0;
        F = 0;
        A = 0;
        AF = 0;
        CPF = 0;
        O = 0;
        OF = 0;
        AO = 0;
        AOF = 0;
        CPF_O = 0;
        G = 0;
        CPF_G = 0;
        U = 0;
        UF = 0;
        AU = 0;
        AUF = 0;
        CPF_U = 0;

//        String benchmark = MainBean.benchmark;
//        try {
//
//            if (benchmark.equals("QALD-1")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.QALD_1, false, false, false);
//            } else if (benchmark.equals("QALD-2")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.QALD_2, false, false, false);
//            } else if (benchmark.equals("QALD-3")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.QALD_3, false, false, false);
//            } else if (benchmark.equals("QALD-4")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.QALD_4, false, false, false);
//            } else if (benchmark.equals("QALD-5")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.QALD_5, false, false, false);
//            } else if (benchmark.equals("QALD-6")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.QALD_6, false, false, false);
//            } else if (benchmark.equals("QALD-7")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.QALD_7, false, false, false);
//            } else if (benchmark.equals("QALD-8")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.QALD_8, false, false, false);
//            } else if (benchmark.equals("QALD-9")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.QALD_9, false, false, false);
//            } else if (benchmark.equals("QALD-ALL")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.QALD_ALL, false, false, false);
//            } else if (benchmark.equals("LC-QUAD")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.LC_QUAD, true, false, false);
//            } else if (benchmark.equals("WebQuestions")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.WebQuestions, false, true, true);
//            } else if (benchmark.equals("GraphQuestions")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.GraphQuestions, false, true, true);
//            } else if (benchmark.equals("SimpleDBpediaQA")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.SimpleDBpediaQA, false, true, true);
//            } else if (benchmark.equals("SimpleQuestions")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.SimpleQuestions, false, true, true);
//            } else if (benchmark.equals("ComplexQuestions")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.ComplexQuestions, false, true, true);
//            } else if (benchmark.equals("ComQA")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.ComQA, false, true, true);
//            } else if (benchmark.equals("TempQuestions")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.TempQuestions, false, true, true);
//            } else if (benchmark.equals("UserDefined")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.UserDefined, false, true, true);
//            }
//             else if (benchmark.equals("PropertiesDefined")) {
//                qs = Benchmark.getQueriesWithoutDuplicates(Benchmark.PropertiesDefined, false, true, true);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        for (Query q : benchmark.queries) {
            boolean FILTER = q.toString().toLowerCase().
                    replace("\n", "").replace("\r", "").replaceAll(" ", "").contains("filter("),
                    AND = q.toString().toLowerCase().contains(" .")
                    || q.toString().toLowerCase().contains(";"),
                    OPT = q.toString().toLowerCase().replaceAll(" ", "").
                    replace("\n", "").replace("\r", "").contains("optional{"),
                    GRAPH = q.toString().toLowerCase().replaceAll(" ", "").
                    replace("\n", "").replace("\r", "").contains("graph{"),
                    UNION = q.toString().toLowerCase().replace("\n", "").replaceAll(" ", "").
                    replace("\n", "").replace("\r", "").contains("union{");

            if (FILTER && !AND && !OPT && !GRAPH && !UNION) {
                F++;
            }
            if (!FILTER && AND && !OPT && !GRAPH && !UNION) {
                A++;
            }
            if (FILTER && AND && !OPT && !GRAPH && !UNION) {
                AF++;
            }

            if (!FILTER && !AND && OPT && !GRAPH && !UNION) {
                O++;
            }
            if (FILTER && !AND && OPT && !GRAPH && !UNION) {
                OF++;
            }
            if (!FILTER && AND && OPT && !GRAPH && !UNION) {
                AO++;
            }
            if (FILTER && AND && OPT && !GRAPH && !UNION) {
                AOF++;
            }

            if (!FILTER && !AND && !OPT && GRAPH && !UNION) {
                G++;
            }

            if (!FILTER && !AND && !OPT && !GRAPH && UNION) {
                U++;
            }
            if (FILTER && !AND && !OPT && !GRAPH && UNION) {
                UF++;
            }
            if (!FILTER && AND && !OPT && !GRAPH && UNION) {
                AU++;
            }
            if (FILTER && AND && !OPT && !GRAPH && UNION) {
                AUF++;
            }

            if (!FILTER && !AND && !OPT && !GRAPH && !UNION) {
                none++;
            }

            if (FILTER && !AND && OPT && GRAPH && !UNION) {
                FGP++;
            }
            if (FILTER && !AND && !OPT && GRAPH && UNION) {
                FGU++;
            }
            if (FILTER && AND && !OPT && GRAPH && !UNION) {
                FAG++;
            }
            if (FILTER && AND && OPT && !GRAPH && UNION) {
                AOUF++;
            }
            if (FILTER && AND && OPT && GRAPH && UNION) {
                AOUFG++;
            }

        }

        CPF = none + A + F + AF;
        CPF_O = O + OF + AO + AOF;
        CPF_G = G;
        CPF_U = U + UF + AU + AUF;

        qs = benchmark.queries;
        distributions.clear();
        distributions.add(new OperatorOneDistribution("none", none, qs.size()));
        distributions.add(new OperatorOneDistribution("F", F, qs.size()));
        distributions.add(new OperatorOneDistribution("A", A, qs.size()));
        distributions.add(new OperatorOneDistribution("AF", AF, qs.size()));
        distributions.add(new OperatorOneDistribution("CPF", CPF, qs.size()));
        distributions.add(new OperatorOneDistribution("O", O, qs.size()));
        distributions.add(new OperatorOneDistribution("O, F", OF, qs.size()));
        distributions.add(new OperatorOneDistribution("A, O", AO, qs.size()));
        distributions.add(new OperatorOneDistribution("A, O, F", AOF, qs.size()));
        distributions.add(new OperatorOneDistribution("CPF + O", CPF_O, qs.size()));
        distributions.add(new OperatorOneDistribution("G", G, qs.size()));
        distributions.add(new OperatorOneDistribution("CPF + G", CPF_G, qs.size()));
        distributions.add(new OperatorOneDistribution("U", U, qs.size()));
        distributions.add(new OperatorOneDistribution("U, F", UF, qs.size()));
        distributions.add(new OperatorOneDistribution("A, U", AU, qs.size()));
        distributions.add(new OperatorOneDistribution("A, U, F", AUF, qs.size()));
        distributions.add(new OperatorOneDistribution("CPF + U", CPF_U, qs.size()));

    }

    public ArrayList<Query> getQs() {
        return qs;
    }

    public void setQs(ArrayList<Query> qs) {
        this.qs = qs;
    }

    public ArrayList<OperatorOneDistribution> getDistributions() {
        return distributions;
    }

    public void setDistributions(ArrayList<OperatorOneDistribution> distributions) {
        this.distributions = distributions;
    }

    public LineChartModel getModel2() {
        return model2;
    }

    public void setModel2(LineChartModel model2) {
        this.model2 = model2;
    }

}
