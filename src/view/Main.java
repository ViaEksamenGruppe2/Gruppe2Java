package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Exam;
import model.ExamCalendarController;
import model.Room;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        ExamCalendarController model = new Controller();
        ViewHandler view = new ViewHandler(model);
        model.setAllRooms(Room.loadFromBinary());
        view.start(primaryStage);
    }



    public static void main(String[] args) {
        Application.launch(Main.class);
    }
}
