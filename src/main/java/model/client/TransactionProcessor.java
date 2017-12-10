package model.client;

import model.bank.Bank;
import model.datastructures.CustomerInfo;
import model.datastructures.Order;
import model.datastructures.Pair;
import model.vendor.Vendor;
import view.MessageCollector;

import java.util.ArrayList;
import java.util.LinkedList;

public class TransactionProcessor {
    private CustomerInfo customerInfo;
    private MessageCollector messageCollector;
    private Bank bank;
    private MoneyOrderBuilder moneyOrderBuilder;
    private ArrayList<Order> moneyOrders;
    private Order signedOrder;
    private Vendor vendor;
    private ArrayList<Pair> revealedStrings;


    public TransactionProcessor(CustomerInfo customerInfo, Bank bank, Vendor vendor, MessageCollector messageCollector) {
        this.customerInfo = customerInfo;
        this.bank = bank;
        this.vendor = vendor;
        this.messageCollector = messageCollector;
    }

    public void process() {
        buildMoneyOrders();
        sendToBankForSig();
        removeBlind();
        sendToVendor();
        sendToBankForVerification();
        bank.displayAccountValues();
        messageCollector.display("Transaction complete.");
    }

    private void buildMoneyOrders() {
        moneyOrderBuilder = new MoneyOrderBuilder(customerInfo, bank.getPublicKey());
        moneyOrders = moneyOrderBuilder.generateMoneyOrders();
        messageCollector.display("Generated " + moneyOrders.size() + " money orders.");
    }

    private void sendToBankForSig() {
        messageCollector.display("Sending to bank for signature..");
        signedOrder = bank.sign(moneyOrders);
    }

    private void removeBlind() {
        messageCollector.display("Signature obtained, de-blinding message..");
        moneyOrderBuilder.removeBlind(signedOrder);
    }

    private void sendToVendor() {
        messageCollector.display("Order has been sent to the vendor.");
        vendor.setOrder(signedOrder);
        revealedStrings = moneyOrderBuilder.getRequestedIdentityStrings(vendor.getIdentityStringChoices());
        messageCollector.display("The vendor has the following identity string components revealed: ");
        displayRevealedStrings();
    }

    private void displayRevealedStrings() {
        for (Pair pair: revealedStrings) {
            messageCollector.display(pair.toString());
        }
    }

    private void sendToBankForVerification() {
        messageCollector.display("Sending to bank for verification...");
        boolean result = bank.verify(signedOrder, revealedStrings);
        if (result) {
            messageCollector.display("Bank has found the signature to be correct.");
            bank.addToVendorAccount(signedOrder.getAmount());
            messageCollector.display("Updated bank accounts:\n" + bank.getAccountValues());
        } else {
            messageCollector.display("The bank has found the signature to be incorrect.");
            messageCollector.display("The cheater is: " + bank.getCheater());
            bank.displayCheater();
        }
    }
}
