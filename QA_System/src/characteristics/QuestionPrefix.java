package characteristics;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionPrefix 
{
    private static ArrayList<String> prefixes = new ArrayList<>();
    static ComparatorbyLength comparator = new ComparatorbyLength("");
    
    public static ArrayList<String> getPrefixes() {
        if(prefixes.size() >= 1)
            return prefixes;
        prefixes.clear();
        prefixes.add("give me");
        prefixes.add("give me a");
        prefixes.add("give me the");
        prefixes.add("give me a list of all");
        prefixes.add("give me a list of all the");
        prefixes.add("give me all the");
        prefixes.add("give me all");
        prefixes.add("give all");
        prefixes.add("give all the");
        
        //Sort strings by length to ensure for eample that "give me all the" handeled before "give me"
        java.util.Collections.sort(prefixes, Collections.reverseOrder(comparator));
        
        return prefixes;
    }
    
    
    
    
    
}
