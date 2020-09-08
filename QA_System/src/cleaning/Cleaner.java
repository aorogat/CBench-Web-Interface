package cleaning;

import characteristics.QuestionPrefix;
import java.util.ArrayList;

public class Cleaner {

    public static String nlqPrefixRemoval(String nlq) {
        String nlqSmall = nlq.toLowerCase();
        
        //Faciliate Parser, Entity-Relationship components work by removing unnecessary words.
        //Remove prefixes: example, give me the president of USA ---> remove "give me the"
        ArrayList<String> prefixes = QuestionPrefix.getPrefixes();
        for (String prefix : prefixes) {
            if (nlqSmall.startsWith(prefix)) {
                return nlq.substring(prefix.length() + 1);
            }
        }
        return nlq;
    }
    
    public static String[] nlqsPrefixRemoval(String[] nlqs)
    {
        String[] nlqs_ = new String[nlqs.length];
        for (int i=0; i<nlqs.length; i++) {
            System.out.println(
                    nlqs_[i] = nlqPrefixRemoval(nlqs[i]));
        }
        return nlqs_;
    }

    public static void main(String[] args) {
        String[] qs
                = ("Give all swimmers that were born in Moscow.\n"
                + "Give me a list of all bandleaders that play trumpet.\n"
                + "Give me a list of all trumpet players that were bandleaders.\n"
                + "Give me all actors starring in movies directed by William Shatner.\n"
                + "Give me all actors who were born in Berlin.\n"
                + "Give me all B-sides of the Ramones.\n"
                + "Give me all books written by Danielle Steel.\n"
                + "Give me all Danish films.\n"
                + "Give me all Danish movies.\n"
                + "Give me all ESA astronauts.\n"
                + "Give me all films produced by Hal Roach.\n"
                + "Give me all islands that belong to Japan.\n"
                + "Give me all launch pads operated by NASA.\n"
                + "Give me all libraries established earlier than 1400.\n"
                + "Give me all Methodist politicians.\n"
                + "Give me all movies directed by Francis Ford Coppola.\n"
                + "Give me all movies with Tom Cruise.\n"
                + "Give me all people that were born in Vienna and died in Berlin.\n"
                + "Give me all soccer clubs in the Premier League.\n"
                + "Give me all Swedish holidays.\n"
                + "Give me all Swedish oceanographers.\n"
                + "Give me all the TV shows with Neil Patrick Harris.\n"
                + "Give me all video games published by Mean Hamster Software.\n"
                + "Give me all writers that won the Nobel Prize in literature.\n"
                + "Give me the birthdays of all actors of the television show Charmed.\n"
                + "Give me the capitals of all countries that the Himalayas run through.\n"
                + "Give me the grandchildren of Bruce Lee.\n"
                + "Give me the grandchildren of Elvis Presley.").split("\n");
        ;

        nlqsPrefixRemoval(qs);
        System.out.println(qs);

    }

}
