package model;

import java.util.ArrayList;

//TODO place constants in enum?
public class WordSplitter {
    private static ArrayList<String> pieces;
    private static final int PIECE_SIZE = 2;
    private static final int MIN_SIZE = 10;
    private static final int MAX_SPLITS= 10;
    private String personalString;

    public WordSplitter(String personalInfo) {
        this.personalString = personalInfo;
        pieces = new ArrayList<String>();
    }

    public ArrayList<String> getPieces() {
        splitIntoPieces();
        return pieces;
    }

    private void splitIntoPieces() {
        if (personalString.length() < MIN_SIZE || personalString.length() % 2 != 0) {
            throw new IllegalArgumentException();
        } else {
            split(personalString, 0);
        }
        displayPieces();
    }

    public void displayPieces() {
        for(String string: pieces) {
            System.out.println(string);
        }
    }

    private void split(String string, int num_splits) {
        if (string.length() > PIECE_SIZE && num_splits <= MAX_SPLITS) {
            num_splits++;
            split(string.substring(0,string.length() / 2), num_splits);
            split(string.substring(string.length() / 2, string.length()), num_splits);
        } else {
            pieces.add(string);
        }
    }
}
