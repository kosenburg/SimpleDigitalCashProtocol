package model;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class TransactionProcessor {
    private final CustomerInfo customerInfo;
    private LinkedList<Order> moneyOrders;
    private ArrayList<String> identityPieces;
    private ArrayList<byte[]> commits;
    private String signature;

    public TransactionProcessor(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public void process() {
        generateMoneyOrders();
    }

    private void generateMoneyOrders() {
        setIdentityPieces();
        setCommits();
        setMoneyOrders();
        sendToBank();
        setOrderSignatures();
        sendToVendor();
        System.out.println("Done");
    }

    private void setOrderSignatures() {
        for (Order order: moneyOrders) {
            order.setSignature(signature);
        }
    }

    private void setMoneyOrders() {
        moneyOrders = new LinkedList<>();
        for (int i = 0; i < customerInfo.getamount(); i++) {
            Order tempOrder = new Order(customerInfo.getamount(), SerialNumberFactory.getSerialNumber());
            for(int j = 0; j < commits.size(); j+=2) {
                tempOrder.addCommitment(commits.get(j), commits.get(j+1));
            }
            moneyOrders.add(tempOrder);
        }
    }

    private void setCommits() {
        BigInteger randomKey  = new BigInteger(128,new Random());
        String key = new String(randomKey.toByteArray());

        Committer committer = new Committer(key);


        commits = new ArrayList<>();
        for(int i = 0; i < identityPieces.size(); i+=2) {
            commits.add(i, committer.commit(identityPieces.get(i)));
            commits.add(i +1, committer.commit(identityPieces.get(i+1)));
        }
    }

    private void setIdentityPieces() {
        WordSplitter wordSplitter = new WordSplitter(customerInfo.getCustomerID());
        identityPieces = wordSplitter.getPieces();
    }

    private void sendToBank() {
        try {
            Socket socket = new Socket(customerInfo.getBankIP(), customerInfo.getBankPort());
            sendMoneyOrders(socket.getOutputStream());
            setSignature(socket.getInputStream());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMoneyOrders(OutputStream outputStream) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(moneyOrders);
    }

    private void sendToVendor() {
        try {
            Socket socket = new Socket(customerInfo.getVendorIP(), customerInfo.getVendorPort());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(moneyOrders);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSignature(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        signature = bufferedReader.readLine();
        System.out.println(signature);
    }
}
