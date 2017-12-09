package model.client;


import model.client.WordSplitter;
import org.junit.jupiter.api.Test;

class WordSplitterTest {
    @Test
    public void getPieces() throws Exception {
        //TODO develop test for pieces function
        String evenTest = "EvenLength";
        //String oddTest = "OddLengthsOddLengthss";

        WordSplitter wordSplitter = new WordSplitter(evenTest);

//        Assert.assertEquals(evenTest.length() / 4, wordSplitter.getPieces().size());
        System.out.println(wordSplitter.getPieces());


        /*String oddTest = "OddLengthsOddLengthss";
        WordSplitter wordSplitter = new WordSplitter(oddTest);
        Assert.assertEquals(oddTest.length() / 2 + 1, wordSplitter.getPieces().size());
*/

    }

}