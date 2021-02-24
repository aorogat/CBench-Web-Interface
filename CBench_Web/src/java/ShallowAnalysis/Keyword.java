package ShallowAnalysis;

/**
 *
 * @author aorogat
 */
public class Keyword {

    String key;
    int occuurances;
    double relative;

    public Keyword(String key, int occuurances, int totoal) {
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
