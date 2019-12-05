package view;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import model.ExamCalendarController;

public class ViewControllerAdd {
    @FXML private Tab addPerson;
    @FXML private Tab addExam;
    @FXML private Tab addRoom;
    @FXML private TabPane tabPane;
    private Region root;
    private ExamCalendarController model;
    private ViewHandler viewHandler;

    public void init(ViewHandler viewHandler, ExamCalendarController model, Region root){
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;
        reset();
    }

    public void setSpecificTab(int tabInt)
    {
        switch (tabInt)
        {
            case 0:
                addPerson.setDisable(false);
                addExam.setDisable(true);
                addRoom.setDisable(true);
                tabPane.getSelectionModel().select(addPerson);
                break;
            case 1:
                addPerson.setDisable(true);
                addExam.setDisable(false);
                addRoom.setDisable(true);
                tabPane.getSelectionModel().select(addExam);
                break;
            case 2:
                addPerson.setDisable(true);
                addExam.setDisable(true);
                addRoom.setDisable(false);
                tabPane.getSelectionModel().select(addRoom);
                break;
        }
    }

    public void reset(){
       // Reset all text fiels and button states
    }

    public Region getRoot()
    {
        return root;
    }

    @FXML public void closeButtonPressed(){
        viewHandler.openView("ExamPlanner",0);
    }

    @FXML public void submitButtonPressed(){
        //Add swich to call depenging on the tab
        reset();
    }
}
