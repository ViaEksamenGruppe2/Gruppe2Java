package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import model.Exam;
import model.ExamCalendarController;
import model.Person;
import model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ViewControllerAdd {
    @FXML private Tab addPerson;
    @FXML private Tab addExam;
    @FXML private Tab addRoom;
    @FXML private TabPane tabPane;

    // INPUTS FOR ADD PERSON
    @FXML private TableView assignedCoursesTable;
    @FXML private TextField personNameField;
    @FXML private TextField VIAIDField;
    @FXML private CheckBox isTeacherCheckbox;

    // INPUTS FOR ADD EXAM
    @FXML private TextField courseNameField;
    @FXML private TextField examDurationField;
    @FXML private ChoiceBox<model.Room> priorityRoomChoiceBox;
    @FXML private CheckBox isWrittenCheckBox;
    @FXML private CheckBox isGroupExamCheckBox;

    // INPUTS FOR ADD ROOM
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
                ObservableList priorityRoomData = FXCollections.observableList(model.getRooms());
                priorityRoomChoiceBox.setItems(priorityRoomData);
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
        String name, viaID;
        name = personNameField.getText();
        viaID = VIAIDField.getText();
        boolean isTeacher = isTeacherCheckbox.selectedProperty().get();
        ArrayList<String> assignedCourses = new ArrayList<>();

        ObservableList<Exam> assignedCoursesObservableList = assignedCoursesTable.getSelectionModel().getSelectedItems();
        for (int i = 0; i < assignedCoursesObservableList.size(); i++)
        {
            String courseName = assignedCoursesObservableList.get(i).getCourseName();
            assignedCourses.add(courseName);
        }
        Person person = new Person(name,viaID,assignedCourses,isTeacher);
        model.getPersons().add(person);
        Person.saveToBinary(model.getPersons());
        reset(0);
    }

    @FXML public void submitExamPressed(){
        String courseName = courseNameField.getText();
        boolean writtenExam, groupExam;
        writtenExam = isWrittenCheckBox.selectedProperty().get();
        groupExam = isGroupExamCheckBox.selectedProperty().get();
        Room priorityRoom = priorityRoomChoiceBox.getValue();
        int duration = 0;
        try
        {
            duration = (int) Double.parseDouble(examDurationField.getText());
            Exam exam = new Exam(courseName, duration, priorityRoom, groupExam, writtenExam);
            model.getExams().add(exam);
        }
        catch (NumberFormatException e)
        {
            System.out.println("WRONG INPUT DUUUDE");
        }
        Exam.saveToBinary(model.getExams());
        reset(1);
    }

    @FXML public void submitRoomPressed(){
        String name = roomNameField.getText();
        boolean hasHDMI, hasVGA, hasProjector;
        hasHDMI = hasHDMICheckBox.selectedProperty().get();
        hasVGA = hasVGACheckBox.selectedProperty().get();
        hasProjector = hasProjectorCheckBox.selectedProperty().get();
        int capacity = 0;
        try
        {
            capacity = Integer.parseInt(capacityField.getText());
            Room room = new Room(capacity,hasHDMI,hasVGA,hasProjector,name);
            model.getRooms().add(room);
        }
        catch (NumberFormatException e)
        {
            System.out.println("WRONG INPUT DUUUDE");
        }

        Room.saveToBinary(model.getRooms());
        reset(2);
    }

    public void loadPersonTab()
    {
        ObservableList courseData = FXCollections.observableList(model.getExams());
        assignedCoursesTable.setItems(courseData);
        TableColumn courseCol = new TableColumn("Course Name");
        courseCol.setPrefWidth(284);
        assignedCoursesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        courseCol.setCellValueFactory(new PropertyValueFactory<String, CheckBox>("courseName"));
        assignedCoursesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        assignedCoursesTable.getColumns().setAll(courseCol);


    }
}
