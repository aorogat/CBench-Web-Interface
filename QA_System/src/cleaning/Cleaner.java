package cleaning;

public class Cleaner {

    public static String nlqPrefixRemoval(String nlq) {
        String nlqSmall = nlq.toLowerCase();
        
        
        if (nlqSmall.startsWith("give me a list of all")) {
            return nlq.substring("give me a list of all".length()+1);
        } 
        else if (nlqSmall.startsWith("give me all the")) {
            return nlq.substring("give me all the".length()+1);
        }
        else if (nlqSmall.startsWith("give me all")) {
            return nlq.substring("give me all".length()+1);
        }
        else if (nlqSmall.startsWith("give me")) {
            return nlq.substring("give me".length()+1);
        }
        else if (nlqSmall.startsWith("give all")) {
            return nlq.substring("give all".length()+1);
        }
        else {
            return nlq;
        }
    }

    public static void main(String[] args) {
        String [] qs = 
        (
        "Give all swimmers that were born in Moscow.\n" +
        "Give me a list of all bandleaders that play trumpet.\n" +
        "Give me a list of all trumpet players that were bandleaders.\n" +
        "Give me all actors starring in movies directed by William Shatner.\n" +
        "Give me all actors who were born in Berlin.\n" +
        "Give me all B-sides of the Ramones.\n" +
        "Give me all books written by Danielle Steel.\n" +
        "Give me all Danish films.\n" +
        "Give me all Danish movies.\n" +
        "Give me all ESA astronauts.\n" +
        "Give me all films produced by Hal Roach.\n" +
        "Give me all islands that belong to Japan.\n" +
        "Give me all launch pads operated by NASA.\n" +
        "Give me all libraries established earlier than 1400.\n" +
        "Give me all Methodist politicians.\n" +
        "Give me all movies directed by Francis Ford Coppola.\n" +
        "Give me all movies with Tom Cruise.\n" +
        "Give me all people that were born in Vienna and died in Berlin.\n" +
        "Give me all soccer clubs in the Premier League.\n" +
        "Give me all Swedish holidays.\n" +
        "Give me all Swedish oceanographers.\n" +
        "Give me all the TV shows with Neil Patrick Harris.\n" +
        "Give me all video games published by Mean Hamster Software.\n" +
        "Give me all writers that won the Nobel Prize in literature.\n" +
        "Give me the birthdays of all actors of the television show Charmed.\n" +
        "Give me the capitals of all countries that the Himalayas run through.\n" +
        "Give me the grandchildren of Bruce Lee.\n" +
        "Give me the grandchildren of Elvis Presley."
        ).split("\n");
        ;
        
        for (String q : qs) 
            System.out.println(
                nlqPrefixRemoval(q));
        
    }

}
