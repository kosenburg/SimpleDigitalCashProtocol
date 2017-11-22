package view;

import model.Committer;
import model.Order;
import model.SerialNumberFactory;
import model.WordSplitter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

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
        String ID = "myIDmyIDmyID";
        long amount = 4;


        String serialNumber = SerialNumberFactory.getSerialNumber();
        WordSplitter wordSplitter = new WordSplitter(ID);
        ArrayList<String> splittedID = wordSplitter.getPieces();


        BigInteger randomKey  = new BigInteger(128,new Random());
        Committer committer = new Committer(new String(randomKey.toByteArray()));


        ArrayList<byte[]> commits = new ArrayList<>();
        for(int i = 0; i < splittedID.size(); i+=2) {
            commits.add(i, committer.commit(splittedID.get(i)));
            commits.add(i +1, committer.commit(splittedID.get(i+1)));
        }

        for(byte[] array: commits) {
            System.out.println(Arrays.toString(array));
        }


        LinkedList<Order> orders = new LinkedList<>();
        for (int i = 0; i < amount; i++) {
            Order tempOrder = new Order(amount, serialNumber);
            for(int j = 0; j < commits.size(); j+=2) {
                tempOrder.addCommitment(commits.get(j), commits.get(j+1));
            }
            orders.add(tempOrder);
        }

        System.out.println(orders.size());

    }
}
