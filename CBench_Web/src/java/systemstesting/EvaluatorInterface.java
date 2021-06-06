/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemstesting;

import DataSet.Benchmark;
import java.io.IOException;

/**
 *
 * @author aorogat
 */
public interface EvaluatorInterface {
    //static BenchmarkEval evaluatedBenchmark = new BenchmarkEval();
    
    
    public void evaluate(Benchmark benchmark) throws IOException;
    public void periodicPoll() throws IOException;
    
}
