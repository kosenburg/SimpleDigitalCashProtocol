package model.datastructures;


import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable{
    private String serialNumber;
    private int amount;
    private ArrayList<Pair> identityCommits;
    private byte[] message = null;
    private byte[] signature = null;

    public Order(int amount, String serialNumber, byte[] message, ArrayList<Pair> identityCommits) {
        setAmount(amount);
        setSerialNumber(serialNumber);
        setMessage(message);
        setIdentityCommits(identityCommits);
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    // Testing only
    public Order(byte[] message) {
        this.message = message;
    }


    private void setIdentityCommits(ArrayList<Pair> identityCommits) {
        this.identityCommits = identityCommits;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    private void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getAmount() {
        return amount;
    }

    private void setAmount(int amount) {
        this.amount = amount;
    }

    public byte[] getCommitment(int pair, int piece) {
        Pair identityCommit = identityCommits.get(pair);

        if (piece == 0) {
            return identityCommit.getRight();
        } else if (piece == 1) {
            return identityCommit.getLeft();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getNumberOfCommitments() {
        return identityCommits.size();
    }

    public byte[] getSignature() {
        return signature;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }
}
