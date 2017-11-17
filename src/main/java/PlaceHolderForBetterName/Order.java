package PlaceHolderForBetterName;


import java.io.Serializable;
import java.util.ArrayList;

//TODO redo order class so is POJO and serializable for transfer across network
public class Order {
    private String serialNumber;
    private long amount;
    private ArrayList<String> commitments;


    public Order(long amount, String serialNumber) {
        setAmount(amount);
        setSerialNumber(serialNumber);
        setCommitments();
    }

    private void setCommitments() {
        commitments = new ArrayList<String>();
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

}
