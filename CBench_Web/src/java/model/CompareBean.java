package model;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author aorogat
 */
@ManagedBean
@RequestScoped
public class CompareBean {

    
    public ArrayList selectedBenchmarks = new ArrayList();
    
    public CompareBean() {
    }

    public ArrayList getSelectedBenchmarks() {
        return selectedBenchmarks;
    }

    public void setSelectedBenchmarks(ArrayList selectedBenchmarks) {
        this.selectedBenchmarks = selectedBenchmarks;
    }
    
    
    
}
