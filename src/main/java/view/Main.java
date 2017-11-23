package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("Digital Cash Protocol");

        Controller controller = new Controller();

        primaryStage.setScene(controller.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        /*Controller controller = new Controller();

        CustomerInfo customerInfo = new CustomerInfo("localhost",33333,"aaaaaaaaaaaa",10);
        controller.processTransaction(customerInfo);*/
    }
}

