package model.client;

import model.datastructures.CustomerInfo;
import model.datastructures.Order;
import model.datastructures.Pair;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.params.RSAKeyParameters;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class MoneyOrderBuilder {
    private final CustomerInfo customerInfo;
    private final RSAKeyParameters bankPubkey;
    private ArrayList<Order> moneyOrders;
    private ArrayList<Pair> identityPieces;
    private ArrayList<Pair> commits;
    private BlindingFactory blindingFactory;
    private Committer committer;
    private String commitKey;

    public MoneyOrderBuilder(CustomerInfo customerInfo, RSAKeyParameters publicKey) {
        this.customerInfo = customerInfo;
        this.bankPubkey = publicKey;
        setCommitKey();
        setCommiter();
        setBlindingFactory();
        setMoneyOrders();
    }

    private void setMoneyOrders() {
        moneyOrders = new ArrayList<>();
    }

    private void setCommitKey() {
        BigInteger randomKey  = new BigInteger(128,new Random());
        commitKey = new String(randomKey.toByteArray());
    }

    private void setCommiter() {
        committer = new Committer(commitKey);
    }

    private void setBlindingFactory() {
        blindingFactory = new BlindingFactory(bankPubkey);
    }

    public Order removeBlind(Order signedOrder) {
        return blindingFactory.removeBlind(signedOrder);
    }

    public ArrayList<Order> generateMoneyOrders() {
        try {
            createIdentityPieces();
            commitIdentityPieces();
            createOrders();
        } catch (Exception e) {
            System.err.println("Error while generating money orders due to: " + e.getMessage());
        }

        return moneyOrders;
    }

    private void createOrders() throws CryptoException {

        for (int i = 0; i < customerInfo.getamount(); i++) {
            Order tempOrder = new Order(customerInfo.getamount(), SerialNumberFactory.getSerialNumber(), blindingFactory.getBlindedMessage(), commits);
            moneyOrders.add(tempOrder);
        }
    }

    private void commitIdentityPieces() {
        commits = new ArrayList<>();
        for(int i = 0; i < identityPieces.size(); i++) {
            Pair pair = identityPieces.get(i);
            commits.add(new Pair(committer.commit(pair.getLeft()), committer.commit(pair.getRight())));
        }
    }

    public ArrayList<Pair> getRequestedIdentityStrings(int[] chosenPieces) {
        ArrayList<Pair> revealedStrings = new ArrayList<>();

        for(int i = 0; i < chosenPieces.length; i++) {
            Pair tempPair = new Pair();
            if(chosenPieces[i] == 0) {
                tempPair.setRight(identityPieces.get(i).getRight());
            } else if (chosenPieces[i] == 1) {
                tempPair.setLeft(identityPieces.get(i).getLeft());
            } else {
                throw new IllegalArgumentException();
            }
            revealedStrings.add(tempPair);
        }

        return revealedStrings;
    }

    private void createIdentityPieces() {
        WordSplitter wordSplitter = new WordSplitter(customerInfo.getCustomerID());
        identityPieces = wordSplitter.getPieces();
    }
}
