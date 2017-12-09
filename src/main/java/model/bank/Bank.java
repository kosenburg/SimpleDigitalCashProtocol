package model.bank;

import model.datastructures.Order;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.signers.PSSSigner;

import java.util.HashMap;
import java.util.LinkedList;

public class Bank {
    private AsymmetricCipherKeyPair keys;
    private int invalidFlag = 0;
    private HashMap<String, Integer> accounts;
    private HashMap<String, String> seenStrings;


    public Bank(AsymmetricCipherKeyPair keys) {
        this.keys = keys;
        setupAccounts();
        setSeenStrings();

    }

    private void setSeenStrings() {
        seenStrings = new HashMap<>();
    }

    private void setupAccounts() {
        this.accounts = new HashMap<>();
        accounts.put("Customer", 100);
        accounts.put("Vendor", 100);
    }

    public RSAKeyParameters getPublicKey() {
        return (RSAKeyParameters) keys.getPublic();
    }

    public Order sign(LinkedList<Order> orders) {
        checkOrderAmounts(orders);

        Order signedOrder = null;

        if (invalidFlag == 0) {
            signedOrder = orders.getLast();
            deductFromCustomerAccount(signedOrder.getAmount());

            byte[] message = signedOrder.getMessage();
            RSAEngine engine = new RSAEngine();
            engine.init(true, keys.getPrivate());
            signedOrder.setSignature(engine.processBlock(message, 0, message.length));
        }

        return signedOrder;

    }

    public void displayAccountValues() {
        System.out.println("Account values: ");
        System.out.println(" Customer: " + accounts.get("Customer"));
        System.out.println("Vendor: " + accounts.get("Vendor") + "\n");
    }

    private void deductFromCustomerAccount(int amount) {
        int newAccountValue = accounts.get("Customer") - amount;
        accounts.put("Customer", newAccountValue);
    }

    private void checkOrderAmounts(LinkedList<Order> orders) {
        Order firstOrder = orders.getFirst();
        for (int i = 0; i <= orders.size() - 2; i++) {
            if (orders.get(i).getAmount() != firstOrder.getAmount()) {
                //TODO something to fail and halt execution
                invalidFlag = 1;
            }
        }

    }

    public boolean verify(Order signedOrder) {
        byte[] message = signedOrder.getMessage()
        byte[] signature = signedOrder.getSignature();

        PSSSigner signer = new PSSSigner(new RSAEngine(), new SHA1Digest(), 20);
        signer.init(false, keys.getPublic());

        signer.update(message, 0, message.length);

        return signer.verifySignature(signature);
    }
}
