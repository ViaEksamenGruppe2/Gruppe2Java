package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.ExamCalendarController;

public class ViewControllerAdd {
    @FXML private Tab addPerson;
    @FXML private Tab addExam;
    @FXML private Tab addRoom;
    @FXML private TabPane tabPane;
    @FXML private TextField personNameField;
    @FXML private TextField VIAIDField;
    @FXML private CheckBox isTeacherCheckbox;
    @FXML private CheckBox assignedCoursesCheckbox;
    @FXML private TextField courseNameField;
    @FXML private TextField examDurationField;
    @FXML private ChoiceBox<model.Room> priorityRoomChoiceBox;
    @FXML private CheckBox isWrittenCheckBox;
    @FXML private CheckBox isGroupExamCheckBox;
    @FXML private TextField roomNameField;
    @FXML private TextField capacityField;
    @FXML private CheckBox hasHDMICheckBox;
    @FXML private CheckBox hasVGACheckBox;
    @FXML private CheckBox hasProjectorCheckBox;
    private Region root;
    private ExamCalendarController model;
    private ViewHandler viewHandler;

    public void init(ViewHandler viewHandler, ExamCalendarController model, Region root){
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;
    }

    public void setSpecificTab(int tabInt)
    {
        switch (tabInt)
        {
            case 0:
                addPerson.setDisable(false);
                addExam.setDisable(true);
                addRoom.setDisable(true);
                loadPersonTab();
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

    public void reset(int tab){
        switch (tab) {
            case 0:
                personNameField.clear();
                VIAIDField.clear();
                isTeacherCheckbox.setSelected(false);
                assignedCoursesCheckbox.setSelected(false);
                break;
            case 1:
                courseNameField.clear();
                examDurationField.clear();
                priorityRoomChoiceBox.getSelectionModel().clearSelection();
                isWrittenCheckBox.setSelected(false);
                isGroupExamCheckBox.setSelected(false);
                break;
            case 2:
                roomNameField.clear();
                capacityField.clear();
                hasHDMICheckBox.setSelected(false);
                hasVGACheckBox.setSelected(false);
                hasProjectorCheckBox.setSelected(false);
                break;
        }
    }

    public Region getRoot()
    {
        return root;
    }

    @FXML public void closeButtonPressed(){
        viewHandler.openView("ExamPlanner",0);
    }

    @FXML public void submitPersonPressed(){
        //Add swich to call depenging on the tab
        reset(0);
    }

    @FXML public void submitExamPressed(){
        //Add swich to call depenging on the tab
        reset(1);
    }

    @FXML public void submitRoomPressed(){
        //Add swich to call depenging on the tab
        reset(2);
    }

    public void loadPersonTab()
    {
        model.getExams();
    }
}
