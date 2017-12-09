package model.datastructures;

public class Pair {
    private byte[] right = null;
    private byte[] left = null;


    public Pair(byte[] left, byte[] right) {
        this.right = right;
        this.left = left;
    }

    public Pair() {
    }

    public void setRight(byte[] right) {
        this.right = right;
    }

    public void setLeft(byte[] left) {
        this.left = left;
    }

    public byte[] getRight() {
        return right;
    }

    public byte[] getLeft() {
        return left;
    }

}