package model.client;

import model.bank.Bank;
import model.datastructures.CustomerInfo;
import model.datastructures.Order;

import java.util.LinkedList;

public class TransactionProcessor {
    private final CustomerInfo customerInfo;
    private Bank bank;
    private MoneyOrderBuilder moneyOrderBuilder;
    private LinkedList<Order> moneyOrders;
    private Order signedOrder;


    public TransactionProcessor(CustomerInfo customerInfo, Bank bank) {
        this.customerInfo = customerInfo;
        this.bank = bank;
        buildMoneyOrders(customerInfo);
    }

    private void buildMoneyOrders(CustomerInfo customerInfo) {
        moneyOrderBuilder = new MoneyOrderBuilder(customerInfo, bank.getPublicKey());
        moneyOrders = moneyOrderBuilder.generateMoneyOrders();
    }

    public void process() {
        sendToBankForSig();
        removeBlind();
        sendToVendor();
    }

    private void removeBlind() {
        moneyOrderBuilder.removeBlind(signedOrder);
    }

    private void sendToBankForSig() {
        signedOrder = bank.sign(moneyOrders);
    }


    private void sendToVendor() {
        sendToBankForVerification();
    }

    private void sendToBankForVerification() {
        boolean result = bank.verify(signedOrder);
    }
}
