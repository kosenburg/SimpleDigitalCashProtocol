package PlaceHolderForBetterName;

public class Order {
    private String serialNumber;
    private long amount;
    private long commitmentL;
    private long commitmentR;


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
}
