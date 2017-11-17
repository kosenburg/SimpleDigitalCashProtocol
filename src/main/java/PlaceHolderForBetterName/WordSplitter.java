package PlaceHolderForBetterName;

import Utils.Tuple;

import java.util.ArrayList;

public class WordSplitter {
    private static ArrayList<Tuple> pieces;
    private static final int SPLIT_AMT = 5;
    private String personalString;

    public WordSplitter(String personalInfo) {
        this.personalString = personalInfo;
    }

    public ArrayList<Tuple> getPieces(String personalString) {
        splitIntoPieces();
        return pieces;
    }

    private void splitIntoPieces() {
        if (personalString.length() < SPLIT_AMT) {
            throw new IllegalArgumentException();
        } else {
            int index = 0;
            while(index < personalString.length()) {
                personalString.substring(index, )
            }
        }
    }
}
