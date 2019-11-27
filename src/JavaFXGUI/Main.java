package JavaFXGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private final int PREF_MIN_WIDTH = 820;
    private final int PREF_MIN_HEIGHT = 550;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ExamPlanner.fxml"));
        primaryStage.setTitle("VIA Exam Manager");
        primaryStage.setScene(new Scene(root, PREF_MIN_WIDTH, PREF_MIN_HEIGHT));
        primaryStage.setMinWidth(PREF_MIN_WIDTH);
        primaryStage.setMinHeight(PREF_MIN_HEIGHT);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
