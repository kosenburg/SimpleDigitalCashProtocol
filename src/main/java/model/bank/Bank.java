package model.bank;

import model.datastructures.Pair;
import model.datastructures.Order;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.signers.PSSSigner;

import java.util.ArrayList;
import java.util.HashMap;

public class Bank {
    private AsymmetricCipherKeyPair keys;
    private int invalidFlag = 0;
    private HashMap<String, Integer> accounts;
    private HashMap<String, ArrayList<Pair>> seenStrings;


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

    public Order sign(ArrayList<Order> orders) {
        checkOrderAmounts(orders);
        Order signedOrder = null;

        if (invalidFlag == 0) {
            signedOrder = orders.get(orders.size()-1);
            deductFromCustomerAccount(signedOrder.getAmount());

            byte[] message = signedOrder.getMessage();
            RSAEngine engine = new RSAEngine();
            engine.init(true, keys.getPrivate());
            signedOrder.setSignature(engine.processBlock(message, 0, message.length));
        }

        return signedOrder;

    }

    public int getAccountBalance(String name) {
        int value = accounts.get(name);

        if (name.equals("Customer")) {
            return accounts.get("Customer");
        } else if (name.equals("Vendor")) {
            return accounts.get("Vendor");
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void displayAccountValues() {
        System.out.println("Account values: ");
        System.out.println(" Customer: " + accounts.get("Customer"));
        System.out.println("Vendor: " + accounts.get("Vendor") + "\n");
    }

    public void deductFromCustomerAccount(int amount) {
        int newAccountValue = accounts.get("Customer") - amount;
        accounts.put("Customer", newAccountValue);
    }

    public void addToVendorAccount(int amount) {
        int newAccountValue = accounts.get("Vendor") + amount;
        accounts.put("Vendor", newAccountValue);
    }

    private void checkOrderAmounts(ArrayList<Order> orders) {
        Order firstOrder = orders.get(0);
        for (int i = 0; i <= orders.size() - 2; i++) {
            if (orders.get(i).getAmount() != firstOrder.getAmount()) {
                //TODO something to fail and halt execution
                invalidFlag = 1;
            }
        }

    }

    public boolean verify(Order signedOrder, ArrayList<Pair> revealedStrings) {
        checkInfo(signedOrder.getSerialNumber(), revealedStrings);
        byte[] message = signedOrder.getMessage();
        byte[] signature = signedOrder.getSignature();

        PSSSigner signer = new PSSSigner(new RSAEngine(), new SHA1Digest(), 20);
        signer.init(false, keys.getPublic());

        signer.update(message, 0, message.length);

        if (signer.verifySignature(signature)) {
            accounts.put("Vendor", accounts.get("Vendor") + signedOrder.getAmount());
            return true;
        } else
            return false;
    }

    private void checkInfo(String serialNumber, ArrayList<Pair> revealedStrings) {
        if (seenStrings.get(serialNumber) == null) {
            seenStrings.put(serialNumber, revealedStrings);
        } else if (seenStrings.get(serialNumber) != null) {
            checkForFraud(seenStrings.get(serialNumber), revealedStrings);

            //TODO better wait to invalidate
            System.out.println("See order before!");
        }
    }


    private void checkForFraud(ArrayList<Pair> storedStrings, ArrayList<Pair> revealedStrings) {
        if (isVendorCheating(storedStrings, revealedStrings)) {
            // throw exception?
        } else {
            fillStoredStringsWithNewIDInfo(storedStrings,revealedStrings);
            constructIdentity(storedStrings);
            // customer cheating with serial number
        }
    }

    private void constructIdentity(ArrayList<Pair> storedStrings) {
        String identity = "";
        for (Pair pair: storedStrings) {
            identity += "(" + new String(pair.getLeft()) + ", " + new String(pair.getRight()) + ")";
        }
        System.out.println("Identity is:");
        System.out.println(identity);
    }

    private void fillStoredStringsWithNewIDInfo(ArrayList<Pair> storedStrings, ArrayList<Pair> revealedStrings) {
        for (int i = 0; i  < revealedStrings.size(); i++) {
            Pair revealedPair = revealedStrings.get(i);
            Pair storedPair = storedStrings.get(i);
            if (revealedPair.getRight() != null && storedPair.getRight() != null) {
                if (revealedPair.getRight() != storedPair.getRight()) {
                    storedPair.setRight(revealedPair.getRight());
                }
            }

            if (revealedPair.getLeft() != null && storedPair.getLeft() != null) {
                if (revealedPair.getLeft() != storedPair.getLeft()) {
                    storedPair.setLeft(revealedPair.getLeft());
                }
            }
        }
    }

    private boolean isVendorCheating(ArrayList<Pair> storedStrings, ArrayList<Pair> revealedStrings) {
        for (int i = 0; i  < revealedStrings.size(); i++) {
            Pair revealedPair = revealedStrings.get(i);
            Pair storedPair = storedStrings.get(i);
            if (revealedPair.getRight() != null && storedPair.getRight() != null) {
                if (revealedPair.getRight() != storedPair.getRight()) {
                    return false;
                }
            }

            if (revealedPair.getLeft() != null && storedPair.getLeft() != null) {
                if (revealedPair.getLeft() != storedPair.getLeft()) {
                    return false;
                }
            }
        }
        return true;

    }
}


