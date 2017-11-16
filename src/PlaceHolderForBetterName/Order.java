package PlaceHolderForBetterName;

import Utils.Tuple;

import java.io.Serializable;
import java.util.ArrayList;

//TODO redo order class so is POJO and serializable for tansfer across network
public class Order {
    private String serialNumber;
    private long amount;
    private ArrayList<Commitment> commitments;


    public Order(long amount, String serialNumber) {
        setAmount(amount);
        setSerialNumber(serialNumber);
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

    public void commitPieces(ArrayList<Tuple> tuples) {
        for (Tuple tuple: tuples) {
            Commitment commitment = new Commitment();
            commitment.setLeftCommit(Committer.commit(tuple.left));
            commitment.setRightCommit(Committer.commit(tuple.right));

            commitments.add(commitment);
        }
    }
}
