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
    private HashMap<String, Integer> accounts;
    private HashMap<String, ArrayList<Pair>> seenStrings;
    private String cheater;


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
        boolean areValidOrders = checkOrderAmounts(orders);

        if (!areValidOrders) {
            return null;
        } else {
            Order signedOrder = orders.get(orders.size() - 1);
            deductFromCustomerAccount(signedOrder.getAmount());

            byte[] message = signedOrder.getMessage();
            RSAEngine engine = new RSAEngine();
            engine.init(true, keys.getPrivate());
            signedOrder.setSignature(engine.processBlock(message, 0, message.length));
            return signedOrder;
        }
    }

    public int getAccountBalance(String name) {
        if (name.equals("Customer")) {
            return accounts.get("Customer");
        } else if (name.equals("Vendor")) {
            return accounts.get("Vendor");
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getCheater() {
        return cheater;
    }

    public void displayCheater() {
        System.out.println("Cheater: " + cheater);
    }

    public void displayAccountValues() {
        System.out.println("Account values: ");
        System.out.println("Customer: " + accounts.get("Customer"));
        System.out.println("Vendor: " + accounts.get("Vendor") + "\n");
    }

    public String getAccountValues() {
        return ("Account values:\n Customer:" + getAccountBalance("Customer") + "\nVendor: " + getAccountBalance("Vendor"));
    }

    public boolean deductFromCustomerAccount(int amount) {
        int newAccountValue = accounts.get("Customer") - amount;
        if (newAccountValue < 0) {
            return false;
        } else {
            accounts.put("Customer", newAccountValue);
            return true;
        }
    }

    public void addToVendorAccount(int amount) {
        int newAccountValue = accounts.get("Vendor") + amount;
        accounts.put("Vendor", newAccountValue);
    }

    private boolean checkOrderAmounts(ArrayList<Order> orders) {
        Order firstOrder = orders.get(0);
        for (int i = 0; i <= orders.size() - 2; i++) {
            if (orders.get(i).getAmount() != firstOrder.getAmount()) {
                return false;
            }
        }
        return true;
    }

    public boolean verify(Order signedOrder, ArrayList<Pair> revealedStrings) {
        boolean isValidOrder = checkInfo(signedOrder.getSerialNumber(), revealedStrings);

        if (isValidOrder) {
            return isValidSignature(signedOrder);
        } else {
            return false;
        }
    }


    private boolean isValidSignature(Order signedOrder) {
        PSSSigner signer = new PSSSigner(new RSAEngine(), new SHA1Digest(), 20);
        signer.init(false, keys.getPublic());

        signer.update(signedOrder.getMessage(), 0, signedOrder.getMessage().length);
        return signer.verifySignature(signedOrder.getSignature());
    }


    private boolean checkInfo(String serialNumber, ArrayList<Pair> revealedStrings) {
        cheater = null;
        if (seenStrings.get(serialNumber) == null) {
            seenStrings.put(serialNumber, revealedStrings);
            return true;
        } else {
            determineCheater(seenStrings.get(serialNumber), revealedStrings);
            return false;
        }
    }


    private void determineCheater(ArrayList<Pair> storedStrings, ArrayList<Pair> revealedStrings) {
        if (isVendorCheating(storedStrings, revealedStrings)) {
            cheater = "Vendor";
        } else {
            fillStoredStringsWithNewIDInfo(storedStrings, revealedStrings);
            constructIdentity(storedStrings);
            cheater = "Customer";
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
        for (int i = 0; i < revealedStrings.size(); i++) {
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




