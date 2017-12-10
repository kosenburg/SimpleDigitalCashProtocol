package model.datastructures;

public class CustomerInfo {
    private String bankIP;
    private int bankPort;
    private String vendorIP;
    private int vendorPort;
    private String customerID;
    private int amount;

    public CustomerInfo(String bankIP, int bankPort, String vendorIP, int vendorPort, String customerID, int amount) {
        this.bankIP = bankIP;
        this.bankPort = bankPort;
        this.vendorIP = vendorIP;
        this.vendorPort = vendorPort;
        this.customerID = customerID;
        this.amount = amount;
    }

    public CustomerInfo(String customerID, int amount) {
        this.customerID = customerID;
        this.amount = amount;
    }

    public CustomerInfo(String bankIP, int bankPort, String customerID, int amount) {
        this.bankIP = bankIP;
        this.bankPort = bankPort;
        this.customerID = customerID;
        this.amount = amount;
    }

    public String getBankIP() {
        return bankIP;
    }

    public int getBankPort() {
        return bankPort;
    }

    public String getVendorIP() {
        return vendorIP;
    }

    public int getVendorPort() {
        return vendorPort;
    }

    public String getCustomerID() {
        return customerID;
    }

    public int getamount() {
        return amount;
    }
}
