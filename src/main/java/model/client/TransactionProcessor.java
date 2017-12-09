package model.client;

import model.bank.Bank;
import model.datastructures.CustomerInfo;
import model.datastructures.Order;
import model.datastructures.Pair;
import model.vendor.Vendor;

import java.util.ArrayList;
import java.util.LinkedList;

public class TransactionProcessor {
    private final CustomerInfo customerInfo;
    private Bank bank;
    private MoneyOrderBuilder moneyOrderBuilder;
    private ArrayList<Order> moneyOrders;
    private Order signedOrder;
    private Vendor vendor;
    private ArrayList<Pair> revealedStrings;


    public TransactionProcessor(CustomerInfo customerInfo, Bank bank, Vendor vendor) {
        this.customerInfo = customerInfo;
        this.bank = bank;
        this.vendor = vendor;
    }

    public void process() {
        buildMoneyOrders();
        sendToBankForSig();
        removeBlind();
        sendToVendor();
        sendToBankForVerification();
    }

    private void buildMoneyOrders() {
        moneyOrderBuilder = new MoneyOrderBuilder(customerInfo, bank.getPublicKey());
        moneyOrders = moneyOrderBuilder.generateMoneyOrders();
    }

    private void sendToBankForSig() {
        signedOrder = bank.sign(moneyOrders);
    }

    private void removeBlind() {
        moneyOrderBuilder.removeBlind(signedOrder);
    }

    private void sendToVendor() {
        revealedStrings = moneyOrderBuilder.getRequestedIdentityStrings(vendor.getIdentityStringChoices());
    }

    private void sendToBankForVerification() {
        boolean result = bank.verify(signedOrder, revealedStrings);
    }
}
