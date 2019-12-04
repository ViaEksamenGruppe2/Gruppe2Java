package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Date;
import model.ExamCalendarController;
import model.PrivateCalendar;

import java.util.ArrayList;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        ExamCalendarController model = new Controller();
        ViewHandler view = new ViewHandler(model);
        view.start(primaryStage);
    }

    public static void main(String[] args) {
        Application.launch(Main.class);
    }
}
