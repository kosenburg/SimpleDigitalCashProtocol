package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.datastructures.CustomerInfo;

public class SceneGenerator {
    private GridPane gridPane;
    private Label bankLabel;
    private Label amountLabel;
    private Label vendorLabel;
    private TextField bankIP;
    private TextField amount;
    private TextField vendorIP;
    private TextField bankPort;
    private TextField vendorPort;
    private TextField customerID;
    private Button submitBtn;
    private Controller controller;
    private Label customerIDLabel;


    public SceneGenerator(Controller controller) {
        this.controller = controller;
        setGridPane();
        setTitle();
        setLabels();
        setTextFields();
        setButton();
    }

    private void setTitle() {
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(scenetitle, 0, 0, 2, 1);
    }


    public Scene getScene() {
        return new Scene(gridPane,600,400);
    }

    private void setGridPane() {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void setButton() {
        submitBtn = new Button("Submit");
        setActionListener();
        gridPane.add(submitBtn,1,5);
    }

    private void setActionListener() {
        submitBtn.setOnAction(actionEvent -> {
            CustomerInfo customerInfo = new CustomerInfo(bankIP.getText(), Integer.parseInt(bankPort.getText()), vendorIP.getText(), Integer.parseInt(vendorPort.getText()), customerID.getText(), Integer.parseInt(amount.getText()));
            controller.processTransaction(customerInfo);
        });
    }

    private void setTextFields() {
        bankIP = new TextField();
        bankIP.setPromptText("Bank IP");
        bankPort = new TextField();
        bankPort.setPromptText("Bank Port");

        vendorIP = new TextField();
        vendorIP.setPromptText("Vendor IP");
        vendorPort = new TextField();
        vendorPort.setPromptText("Vendor Port");

        amount = new TextField();
        amount.setPromptText("Money Order Amount");

        customerID  = new TextField();
        customerID.setPromptText("Customer Identity String");

        gridPane.add(bankIP, 1, 1);
        gridPane.add(bankPort, 2, 1);
        gridPane.add(vendorPort,2,2);
        gridPane.add(vendorIP, 1, 2);
        gridPane.add(amount,1,3);
        gridPane.add(customerID, 1, 4);
    }

    private void setLabels() {
        bankLabel = new Label("Bank Info:");
        amountLabel = new Label("Amount:");
        vendorLabel = new Label("Vendor Info:");
        customerIDLabel = new Label("Customer ID");
        gridPane.add(bankLabel, 0, 1);
        gridPane.add(vendorLabel, 0, 2);
        gridPane.add(amountLabel,0,3);
        gridPane.add(customerIDLabel, 0, 4);
    }

}
