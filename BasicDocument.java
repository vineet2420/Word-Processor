import java.util.ArrayList;
import java.util.List;

/**
 * A naive implementation of the Document abstract class. 
 */
public class BasicDocument extends Document
{
    /** Create a new BasicDocument object
     *
     * @param text The full text of the Document.
     */
    public BasicDocument(String text)
    {
        super(text);
    }


    /**
     * Get the number of words in the document.
     * A "word" is defined as a contiguous string of alphabetic characters
     * i.e. any upper or lower case characters a-z or A-Z.  This method completely
     * ignores numbers when you count words, and assumes that the document does not have
     * any strings that combine numbers and letters.
     *
     * Check the examples in the main method below for more information.
     *
     * This method should process the entire text string each time it is called.
     *
     * @return The number of words in the document.
     */
    @Override
    public int getNumWords()
    {
        //create a new array list to hold the regex specified words
        List<String> myWordsArray = new ArrayList<String>();

        //get the size if the array indexes from 1 to n-1
        int myArraySize;

        //similar to \w but that will capture words, numbers and an _
        //we are looking for words so we limit the search to letters
        //the + operator matchers one or more of the letters
        //we also exclude spaces so we do not need to worry about multiple spaces after a word
        myWordsArray = getTokens("[A-Za-z]+");

        //actually set the array size to its int variable
        myArraySize = myWordsArray.size();

//        System.out.println("Words without space: " + myArray);


        return myArraySize;
    }

    /**
     * Get the number of sentences in the document.
     * Sentences are defined as contiguous strings of characters ending in an
     * end of sentence punctuation (. ! or ?) or the last contiguous set of
     * characters in the document, even if they don't end with a punctuation mark.
     *
     *
     * This method should process the entire text string each time it is called.
     *
     * @return The number of sentences in the document.
     */
    @Override
    public int getNumSentences()
    {

        //Counter for the array size
        int sentenceArraySize = 0;

        //create an array list
        List<String> mySentencesArray = new ArrayList<String>();

        //initial regex to check for the last word in the sentence
        //this checks the last word only from A-Za-z_ so there is no punctuation
        //this is a corner case for the sentences that do not end with a . ! or ?
        mySentencesArray = getTokens("(\\w+)$");

//        System.out.println("output: "+mySentencesArray);

        //if we find that the array does contain a word with no punctuation in the last index, then increase the counter by 1
        if(mySentencesArray.size()>=1){
            sentenceArraySize+=1;
        }

        //the correct regex to find the punctuation *within* a sentence, and at the end of a sentence
        mySentencesArray = getTokens("[.|!|?]+");

        //increase the counter to have the correct number of punctuation occurrences from the last word (from the previous regex) and the punctuation within the sentences.
        sentenceArraySize += mySentencesArray.size();

//        System.out.println("My sentences array: "+mySentencesArray);

        return sentenceArraySize;
    }

    /**
     * Get the total number of syllables in the document (the stored text).
     * To count the number of syllables in a word, it uses the following rules:
     *       Each contiguous sequence of one or more vowels is a syllable,
     *       with the following exception: a lone "e" at the end of a word
     *       is not considered a syllable unless the word has no other syllables.
     *       You should consider y a vowel.
     *
     * Check the examples in the main method below for more information.
     *
     *
     * @return The number of syllables in the document.
     */
    @Override
    public int getNumSyllables()
    {


        //allocating memory for a new List of String type
        List<String> mySyllableArray = new ArrayList<String>();

        //Adding the contents of only words into the array
        mySyllableArray = getTokens("[A-Za-z]+");

        //System.out.println("mySyllableArray: "+ mySyllableArray);

        //counter
        int total = 0;

        for(int i = 0; i<mySyllableArray.size(); i++){
           //System.out.println("Looking at this word: "+mySyllableArray.get(i));
           total += countSyllables(mySyllableArray.get(i));
        }

        return total;
    }



    public static void main(String[] args)
    {


        /* Each of the test cases below uses the method testCase.  The first
         * argument to testCase is a Document object, created with the string shown.
         * The next three arguments are the number of syllables, words and sentences
         * in the string, respectively.  You can use these examples to help clarify
         * your understanding of how to count syllables, words, and sentences.
         */
        testCase(new BasicDocument("This is a test.  How many???  "
                        + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
                16, 13, 5);
        testCase(new BasicDocument(""), 0, 0, 0);
        testCase(new BasicDocument("sentence, with, lots, of, commas.!  "
                + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
        testCase(new BasicDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
        testCase(new BasicDocument("Here is a series of test sentences. Your program should "
                + "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
                + "the correct amount of syllables (example, for example), "
                + "but most of them will."), 49, 33, 3);
        testCase(new BasicDocument("Segue"), 2, 1, 1);
        testCase(new BasicDocument("Sentence"), 2, 1, 1);
        testCase(new BasicDocument("Sentences?!"), 3, 1, 1);
        testCase(new BasicDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
                32, 15, 1);
    }

}