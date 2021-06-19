/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShapeAnalysis;

/**
 *
 * @author aorogat
 */
public class OneShapeSummary {
    String key;
    int occuurances;
    double relative;

    public OneShapeSummary(String key, int occuurances, int totoal) {
        this.key = key;
        this.occuurances = occuurances;
        relative = ((double)occuurances/(double)totoal)*100;
    }

    
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getOccuurances() {
        return occuurances;
    }

    public void setOccuurances(int occuurances) {
        this.occuurances = occuurances;
    }

    public double getRelative() {
        return relative;
    }

    public void setRelative(double relative) {
        this.relative = relative;
    }
     
}
