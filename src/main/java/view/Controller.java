package view;

import javafx.scene.Scene;
import model.bank.Bank;
import model.bank.Util;
import model.datastructures.CustomerInfo;
import model.client.TransactionProcessor;
import model.vendor.Vendor;

public class Controller {
    private SceneGenerator sceneGenerator;
    private Bank bank;
    private Vendor vendor;

    public Controller() {
        setSceneGenerator();
        setBank();
        setVendor();
    }

    private void setVendor() {
        vendor = new Vendor();
    }

    private void setBank() {
        bank = new Bank(Util.generateKeyPair());
    }

    private void setSceneGenerator() {
        sceneGenerator = new SceneGenerator(this);
    }

    public Scene getScene() {
        return sceneGenerator.getScene();
    }

    public void processTransaction(CustomerInfo customerInfo) {
        TransactionProcessor transactionProcessor = new TransactionProcessor(customerInfo, bank, vendor);
        transactionProcessor.process();
    }
}
