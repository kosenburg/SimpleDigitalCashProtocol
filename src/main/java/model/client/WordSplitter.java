package model.client;

import model.datastructures.Pair;

import java.util.ArrayList;

//TODO place constants in enum?
public class WordSplitter {
    private static ArrayList<String> pieces;
    private static final int PIECE_SIZE = 2;
    private static final int MIN_SIZE = 10;
    private static final int MAX_SPLITS= 10;
    private String personalString;
    private ArrayList<Pair> pairs;

    public WordSplitter(String personalInfo) {
        this.personalString = personalInfo;
        pieces = new ArrayList<>();
    }

    public ArrayList<Pair> getPieces() {
        splitIntoPieces();
        return pairs;
    }

    private void splitIntoPieces() {
        if (personalString.length() < MIN_SIZE || personalString.length() % 2 != 0) {
            throw new IllegalArgumentException();
        } else {
            split(personalString, 0);
            fillListOfPairs();
            pieces = null;
            System.out.println("Pairs list filled.");
        }
    }

    private void fillListOfPairs() {
        System.out.println("Filling list of pairs..");
        pairs = new ArrayList<>();
        for (int i = 0; i < pieces.size(); i+=2) {
            System.out.println("Left: " + pieces.get(i)+ " Right: " + pieces.get(i+1));
            pairs.add(new Pair(pieces.get(i).getBytes(), pieces.get(i+1).getBytes()));
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
