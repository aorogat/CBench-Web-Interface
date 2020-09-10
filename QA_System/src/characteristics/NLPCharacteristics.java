package characteristics;

public class NLPCharacteristics 
{
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
