package view;

import PlaceHolderForBetterName.Order;
import PlaceHolderForBetterName.SerialNumberFactory;
import PlaceHolderForBetterName.WordSplitter;
import Utils.Tuple;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
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

        String personalInfo = "";

        WordSplitter wordSplitter = new WordSplitter(personalInfo);
        ArrayList<Tuple> tuples = WordSplitter.getPieces();

        for (Order order: orders) {
            order.commitPieces(tuples);
        }

    }
}
