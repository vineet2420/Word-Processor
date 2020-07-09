/**
 * A class that represents a text document
 */
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document {

    private String text;

    /** Create a new document from the given text.
     * Because this class is abstract, this is used only from subclasses.
     * @param text The text of the document.
     */
    protected Document(String text)
    {
        this.text = text;
    }

    /** Returns the tokens that match the regex pattern from the document
     * text string.
     * @param pattern A regular expression string specifying the
     *   token pattern desired
     * @return A List of tokens from the document text that match the regex
     *   pattern
     */
    protected List<String> getTokens(String pattern)
    {
        ArrayList<String> tokens = new ArrayList<String>();
        Pattern tokSplitter = Pattern.compile(pattern);
        Matcher m = tokSplitter.matcher(text);

        while (m.find()) {
            tokens.add(m.group());
        }

        return tokens;
    }





    /** This is a helper function that returns the number of syllables
     * in a word.  You should write this and use it in your
     * BasicDocument class.
     *
     *
     * @param word  The word to count the syllables in
     * @return The number of syllables in the given word, according to
     * this rule: Each contiguous sequence of one or more vowels is a syllable,
     *       with the following exception: a lone "e" at the end of a word
     *       is not considered a syllable unless the word has no other syllables.
     *       You should consider y a vowel.
     */
    protected int countSyllables(String word) {
        //String myWord[] = word.split("");

        int c = 0;

        //pointer variables- prev1, prev2, prev3
        //this is how I detect two vowels next to each other
        char p1,p2,p3;

        //string of vowels to check if the word's index is contained within this string
        String vowels = "aeiouyAEIOUY";

        //return 1 syllable for 3 letter words or less
        if(word.length()<4){
            c=1;
        }
        else {
           //loop for cycling through the word elements
            for (int i = 0; i < word.length(); i++) {
                //loop for the vowels string
                for (int j = 0; j < vowels.length(); j++) {
                    //checks if each vowel in vowels string is equal to the i'th index in the specific word
                    //once the j loop finishes and compares every single vowel to the word's index, then move onto the second index in the word
                    if ((word.charAt(i) == vowels.charAt(j))) {
                        //System.out.println("Incrementing because "+word.charAt(i)+" = "+vowels.charAt(j));
                        c++;
                    }

                }
            }


            //two consecutive vowels or more and edge cases
                for (int z = 2; z < word.length(); z++) {
                    //first letter
                    p1 = word.charAt(z - 2);
                    String prev1 = String.valueOf(p1);

                    //next letter
                    p2 = word.charAt(z - 1);
                    String prev2 = String.valueOf(p2);

                    //next next letter
                    p3 = word.charAt(z);
                    String prev3 = String.valueOf(p3);

                    //how come setting prev1 == prev2 to check if it is true does not work?

                    //System.out.println("P1: " + prev1 + " P2: " + prev2 + " P3: " + prev3);

                    //to detect two contiguous vowels
                        if( (vowels.indexOf(p1)>=0) && (vowels.indexOf(p2)>=0) ){
                           // if( (vowels.indexOf(p3)>=1) ){
                                //System.out.println("P1 P2 P3 are vowels");
                                 //System.out.println("P1: " + p1 + " P2: " + p2 + " P3: " + p3);
                                c--;
                            //}
                        }

                    }




            //cover the case for silent 'e' at the end of a word
            if ( (word.charAt(word.length() - 1) == 'e')  ){
                //System.out.println("In this loop for word: " + word);
                c--;
            }

        }//else loop from words >3 letter words







        //System.out.println("Word: "+ word + ", Syllables(s): "+c);
        //System.out.println("Value of C: "+ c);



        // getNumSyllables method in BasicDocument

        return c;
    }

    /** A method for testing
     *
     * @param doc The Document object to test
     * @param syllables The expected number of syllables
     * @param words The expected number of words
     * @param sentences The expected number of sentences
     * @return true if the test case passed.  False otherwise.
     */
    public static boolean testCase(Document doc, int syllables, int words, int sentences)
    {
        doc.getFleschScore();
        System.out.println("Testing text: ");
        System.out.print(doc.getText() + "\n....");
        boolean passed = true;
        int syllFound = doc.getNumSyllables();
        int wordsFound = doc.getNumWords();
        int sentFound = doc.getNumSentences();
        if (syllFound != syllables) {
            System.out.println("\nIncorrect number of syllables.  Found " + syllFound
                    + ", expected " + syllables);
            passed = false;
        }
        if (wordsFound != words) {
            System.out.println("\nIncorrect number of words.  Found " + wordsFound
                    + ", expected " + words);
            passed = false;
        }
        if (sentFound != sentences) {
            System.out.println("\nIncorrect number of sentences.  Found " + sentFound
                    + ", expected " + sentences);
            passed = false;
        }

        if (passed) {
            System.out.println("passed.\n");
        }
        else {
            System.out.println("FAILED.\n");
        }
        return passed;
    }


    /** Return the number of words in this document */
    public abstract int getNumWords();

    /** Return the number of sentences in this document */
    public abstract int getNumSentences();

    /** Return the number of syllables in this document */
    public abstract int getNumSyllables();

    /** Return the entire text of this document */
    public String getText()
    {
        return this.text;
    }

    /** return the Flesch readability score of this document */
    public double getFleschScore()
    {

        //need doubles since if we had an int, 5/2 = still 2
        double numWords = getNumWords();
        //System.out.println(numWords);
        double numSentences = getNumSentences();
        double numSyllables = getNumSyllables();

        double fleschScore = -1;

        //cover the divide by zero case- 0 words or sentences
        if(numWords == 0 | numSentences == 0){
            fleschScore = 0;
        }
        else{
            fleschScore = (206.835 - (1.015*(numWords/numSentences)) - (84.6*(numSyllables/numWords)) );
        }

        System.out.println("FleschScore: "+fleschScore);
        // TODO: Implement this method.
        return fleschScore;
    }



}