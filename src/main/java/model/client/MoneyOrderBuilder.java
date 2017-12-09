package model.client;

import model.datastructures.CustomerInfo;
import model.datastructures.Order;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.params.RSAKeyParameters;

import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class MoneyOrderBuilder {

    private final CustomerInfo customerInfo;
    private final RSAKeyParameters bankPubkey;
    private LinkedList<Order> moneyOrders;
    private ArrayList<String> identityPieces;
    private ArrayList<byte[]> commits;
    private static final String MESSAGE = "aMessage";
    private BlindingFactory blindingFactory;
    private Committer committer;

    public MoneyOrderBuilder(CustomerInfo customerInfo, RSAKeyParameters publicKey) {
        this.customerInfo = customerInfo;
        this.bankPubkey = publicKey;
    }


    public Order removeBlind(Order signedOrder) {
        return blindingFactory.removeBlind(signedOrder);
    }

    public LinkedList<Order> generateMoneyOrders() {
        try {
            createIdentityPieces();
            commitIdentityPieces();
            createBlind();
            setMoneyOrders();
        } catch (Exception e) {
            System.err.println("Error while generating money orders due to: " + e.getMessage());
        }

        return moneyOrders;
    }

    private void createBlind() {
        blindingFactory = new BlindingFactory(bankPubkey);
    }

    private void setMoneyOrders() throws CryptoException {
        moneyOrders = new LinkedList<>();
        for (int i = 0; i < customerInfo.getamount(); i++) {
            Order tempOrder = new Order(customerInfo.getamount(), SerialNumberFactory.getSerialNumber(), blindingFactory.getBlindedMessage());
            for(int j = 0; j < commits.size(); j+=2) {
                tempOrder.addCommitment(commits.get(j), commits.get(j+1));
            }
            moneyOrders.add(tempOrder);
        }
    }

    private void commitIdentityPieces() {
        BigInteger randomKey  = new BigInteger(128,new Random());
        String key = new String(randomKey.toByteArray());

        committer = new Committer(key);

        commits = new ArrayList<>();
        for(int i = 0; i < identityPieces.size(); i+=2) {
            commits.add(i, committer.commit(identityPieces.get(i)));
            commits.add(i +1, committer.commit(identityPieces.get(i+1)));
        }
    }

    private void createIdentityPieces() {
        WordSplitter wordSplitter = new WordSplitter(customerInfo.getCustomerID());
        identityPieces = wordSplitter.getPieces();
    }
}
