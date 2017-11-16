package view;

import PlaceHolderForBetterName.Order;
import PlaceHolderForBetterName.SerialNumberFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Main { //extends Application{



    /*
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
*/

    public static void main(String[] args) {
        //launch(args);
        int amount = Integer.getInteger(args[1]);
        LinkedList<Order> orders = new LinkedList<>();

        for (int i = 0; i <  amount; i++) {
            orders.add(new Order(amount, SerialNumberFactory.getSerialNumber()));
        }

    }
}
