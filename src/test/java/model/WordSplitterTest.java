package model;


import model.client.WordSplitter;

public class WordSplitterTest {
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