package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.datastructures.CustomerInfo;

public class SceneGenerator {
    private GridPane gridPane;
    private Label amountLabel;
    private TextField amount;
    private TextField customerID;
    private Button submitBtn;
    private Controller controller;
    private Label customerIDLabel;
    private TextArea textArea;


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
        gridPane.add(submitBtn,1,3);
    }

    private void setActionListener() {
        submitBtn.setOnAction(actionEvent -> {
            CustomerInfo customerInfo = new CustomerInfo(customerID.getText(), Integer.parseInt(amount.getText()));
            controller.processTransaction(customerInfo);
        });
    }

    private void setTextFields() {
        amount = new TextField();
        amount.setPromptText("Money Order Amount");

        customerID  = new TextField();
        customerID.setPromptText("Customer Identity String");

        gridPane.add(amount,1,1);
        gridPane.add(customerID, 1, 2);
    }


    private void setLabels() {
        amountLabel = new Label("Amount:");
        customerIDLabel = new Label("Customer ID");
        textArea = new TextArea();
        textArea.setMaxHeight(300);
        textArea.setMaxWidth(300);
        gridPane.add(textArea, 0,4,4,4);
        gridPane.add(amountLabel,0,1);
        gridPane.add(customerIDLabel, 0, 2);
    }

    public void addToTextArea(String text) {
        textArea.appendText(text);
    }

}
