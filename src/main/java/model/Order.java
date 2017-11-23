package model;


import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable{
    private String serialNumber;
    private long amount;
    private ArrayList<byte[]> commitments;
    private String blind;
    private String signature;


    public Order(long amount, String serialNumber) {
        setAmount(amount);
        setSerialNumber(serialNumber);
        setCommitments();
    }

    private void setBlind(String message) {

    }

    private void setCommitments() {
        commitments = new ArrayList<byte[]>();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    private void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public long getAmount() {
        return amount;
    }

    private void setAmount(long amount) {
        this.amount = amount;
    }

    public void addCommitment(byte[] leftCommit, byte[] rightCommit) {
        commitments.add(leftCommit);
        commitments.add(rightCommit);
    }

    //TODO fix
    public ArrayList<byte[]> getCommitment() {
    //    return new byte [][] {commitments.get(i), commitments.get(i+1)};
        return commitments;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
