package model.datastructures;


import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable{
    private String serialNumber;
    private int amount;
    private ArrayList<byte[]> commitments;
    private byte[] message;
    private byte[] signature;


    public Order(int amount, String serialNumber, byte[] message) {
        setAmount(amount);
        setSerialNumber(serialNumber);
        setMessage(message);
        setCommitments();
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
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

    public int getAmount() {
        return amount;
    }

    private void setAmount(int amount) {
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
