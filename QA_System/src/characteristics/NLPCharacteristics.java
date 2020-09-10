package characteristics;

public class NLPCharacteristics 
{
    boolean requestExplicity = true; //Explicit ex(How many .....),, Implicit ex(TV shows with Neil Patrick Harris.)
    int requestMultiplicity = RequestMultiplicity.ONE_REQUEST;      //one or multiple
    int requestCategory = RequestCategory.LIST_OF_UNIQUE_ANSWERS;
    int expectedAnswer = ExpectedAnswer.NAME;
    private String prefix;

    public NLPCharacteristics(String nlq) //nlq: Natural Language Question
    {
        
    }

    public String getPrefix(String nlq) {
        prefix = "";
        for (String p : QuestionPrefix.getPrefixes()) {
            if(nlq.toLowerCase().startsWith(p))
            {
                prefix = p;
                break;
            }
        }
        return prefix;
    }
    
    
    
    
    
}
