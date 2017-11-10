package PlaceHolderForBetterName;

public class Order {
    private long serialNumber;
    private long amount;
    private long commitmentL;
    private long commitmentR;


    public Order(long amount, long serialNumber) {
        setAmount(amount);
        setSerialNumber(serialNumber);
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    private void setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public long getAmount() {
        return amount;
    }

    private void setAmount(long amount) {
        this.amount = amount;
    }
}
