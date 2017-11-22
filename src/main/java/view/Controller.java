package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import model.CustomerInfo;
import model.TransactionProcessor;

public class Controller {
    private SceneGenerator sceneGenerator;

    public Controller() {
        setSceneGenerator();
    }

    private void setSceneGenerator() {
        sceneGenerator = new SceneGenerator(this);
    }

    public Scene getScene() {
        return sceneGenerator.getScene();
    }

    public void processTransaction(CustomerInfo customerInfo) {
        TransactionProcessor transactionProcessor = new TransactionProcessor(customerInfo);
        transactionProcessor.process();
    }
}
