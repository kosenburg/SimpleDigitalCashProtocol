package PlaceHolderForBetterName;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordSplitterTest {
    @Test
    public void getPieces() throws Exception {
        //TODO develop test for pieces function
        String evenTest = "EvenLength";
        String oddTest = "OddLengthsOddLengthss";

        WordSplitter wordSplitter = new WordSplitter(oddTest);

//        Assert.assertEquals(evenTest.length() / 4, wordSplitter.getPieces().size());
        wordSplitter.getPieces();


        /*String oddTest = "OddLengthsOddLengthss";
        WordSplitter wordSplitter = new WordSplitter(oddTest);
        Assert.assertEquals(oddTest.length() / 2 + 1, wordSplitter.getPieces().size());
*/

    }

}