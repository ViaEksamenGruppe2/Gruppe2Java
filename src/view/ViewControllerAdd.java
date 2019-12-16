package view;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.util.Duration;
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
    @FXML private Label confirmationPerson;
    @FXML private CheckBox isSeventhSemesterCheckBox;

    // INPUTS FOR ADD EXAM
    @FXML private TextField courseNameField;
    @FXML private TextField examDurationField;
    @FXML private ChoiceBox<model.Room> priorityRoomChoiceBox;
    @FXML private CheckBox isWrittenCheckBox;
    @FXML private CheckBox isGroupExamCheckBox;
    @FXML private Label confirmationExam;

    // INPUTS FOR ADD ROOM
    @FXML private TextField roomNameField;
    @FXML private TextField capacityField;
    @FXML private CheckBox hasHDMICheckBox;
    @FXML private CheckBox hasVGACheckBox;
    @FXML private CheckBox hasProjectorCheckBox;
    @FXML private Label confirmationRoom;

    private boolean enteredFromEdit;
    private Object objectToRemove;
    private Region root;
    private ExamCalendarController model;
    private ViewHandler viewHandler;

    public void init(ViewHandler viewHandler, ExamCalendarController model, Region root){
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;
        enteredFromEdit = false;
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
                isWrittenCheckBox.setVisible(true);
                isGroupExamCheckBox.setVisible(true);
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
        enteredFromEdit = false;
    }

    public Region getRoot()
    {
        return root;
    }

    @FXML public void closeButtonPressed(){
        viewHandler.openView("ExamPlanner",0);
        enteredFromEdit = false;
    }

    @FXML public void submitPersonPressed(){
        String name, viaID;
        ArrayList<String> assignedCourses = new ArrayList<>();
        ObservableList<Exam> assignedCoursesObservableList = assignedCoursesTable.getSelectionModel().getSelectedItems();
        if (!personNameField.getText().isEmpty() && !VIAIDField.getText().isEmpty() && !assignedCoursesObservableList.isEmpty()
            && personNameField.getText().length() <= 256 && VIAIDField.getText().length() <= 16)
        {
            name = personNameField.getText();
            viaID = VIAIDField.getText();
            boolean isTeacher = isTeacherCheckbox.selectedProperty().get();
            for (int i = 0; i < assignedCoursesObservableList.size(); i++)
            {
                String courseName = assignedCoursesObservableList.get(i).getCourseName();
                assignedCourses.add(courseName);
            }
            Person person = new Person(name,viaID,assignedCourses,isTeacher);
            for (int i = 0; i < assignedCoursesObservableList.size(); i++)
            {
                person.addExam(assignedCoursesObservableList.get(i));
            }
            for (int i = 0; i < assignedCoursesObservableList.size(); i++)
            {
                assignedCoursesObservableList.get(i).addPerson(person);
            }
            model.getPersons().add(person);
            submitLabel(0);
            if (enteredFromEdit)
            {
                Person personToRemove = (Person) objectToRemove;
                model.getPersons().remove(personToRemove);
            }
        }
        else {
            String alertMessage = "You haven't entered all the required input to add a person. \nPlease try again.";
            if (personNameField.getText().length() <= 256 && VIAIDField.getText().length() <= 16)
                alertMessage = "You have entered too long a name or VIA ID. \nPlease try again.";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong input");
            alert.setHeaderText(null);
            alert.setContentText(alertMessage);
            alert.showAndWait();
        }
        Person.saveToBinary(model.getPersons());
        reset(0);
    }

    @FXML public void submitExamPressed(){
        String courseName;
        boolean writtenExam, groupExam, seventhSemester;
        int duration;
        if (!courseNameField.getText().isEmpty() && !examDurationField.getText().isEmpty())
        {
            courseName = courseNameField.getText();
            try
            {
                duration = (int) Double.parseDouble(examDurationField.getText());
                writtenExam = isWrittenCheckBox.selectedProperty().get();
                groupExam = isGroupExamCheckBox.selectedProperty().get();
                seventhSemester = isSeventhSemesterCheckBox.selectedProperty().get();
                Room priorityRoom = priorityRoomChoiceBox.getValue();
                Exam exam = new Exam(courseName, duration, priorityRoom, groupExam, writtenExam,seventhSemester);
                model.getExams().add(exam);
                submitLabel(1);
                if (enteredFromEdit)
                {
                    Exam examToRemove = (Exam) objectToRemove;
                    model.getExams().remove(examToRemove);
                    // set all for object instead of removing it.
                    // add else model.getExams().add(exam)
                    // Then it will be edited instead of added anew
                }
            }
            catch (NumberFormatException e)
            {
                String alertMessage = "You have entered text instead of an integer for the exam length. \nPlease try again.";
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Wrong input");
                alert.setHeaderText(null);
                alert.setContentText(alertMessage);
                alert.showAndWait();
            }
        }
        else
        {
            String alertMessage = "You haven't entered all the required input to add an exam. \nPlease try again.";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong input");
            alert.setHeaderText(null);
            alert.setContentText(alertMessage);
            alert.showAndWait();
        }
        Exam.saveToBinary(model.getExams());
        reset(1);
    }

    @FXML public void writtenExamPressed()
    {
        if (isWrittenCheckBox.selectedProperty().get())
        {
            isGroupExamCheckBox.setVisible(false);
        }
        else {
            isGroupExamCheckBox.setVisible(true);
        }
    }
    @FXML public void groupExamPressed()
    {
        if (isGroupExamCheckBox.selectedProperty().get())
        {
            isWrittenCheckBox.setVisible(false);
        }
        else {
            isWrittenCheckBox.setVisible(true);
        }
    }

    @FXML public void submitRoomPressed(){
        String name;
        int capacity;
        boolean hasHDMI, hasVGA, hasProjector;
        if (!roomNameField.getText().isEmpty() && !capacityField.getText().isEmpty())
        {
            name = roomNameField.getText();
            try
            {
                capacity = Integer.parseInt(capacityField.getText());
                hasHDMI = hasHDMICheckBox.selectedProperty().get();
                hasVGA = hasVGACheckBox.selectedProperty().get();
                hasProjector = hasProjectorCheckBox.selectedProperty().get();
                Room room = new Room(capacity,hasHDMI,hasVGA,hasProjector,name);
                submitLabel(2);
                if (enteredFromEdit)
                {
                    Room roomToEdit = (Room) objectToRemove;
                    for (int i = 0; i < model.getRooms().size(); i++)
                    {
                        if (model.getRooms().get(i).equals(roomToEdit))
                        {
                            model.getRooms().get(i).setAll(capacity, hasHDMI, hasVGA,hasProjector, name);
                        }
                    }
                }
                else {
                    model.getRooms().add(room);
                }
            }
            catch (NumberFormatException e)
            {
                String alertMessage = "You have entered text instead of an integer for the room capacity. \nPlease try again.";
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Wrong input");
                alert.setHeaderText(null);
                alert.setContentText(alertMessage);
                alert.showAndWait();
            }
        }
        else {
            String alertMessage = "You haven't entered all the required input to add an exam. \nPlease try again.";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong input");
            alert.setHeaderText(null);
            alert.setContentText(alertMessage);
            alert.showAndWait();
        }
        Room.saveToBinary(model.getRooms());
        reset(2);
    }

    public void editObject(Object obj, int tabInt)
    {
        enteredFromEdit = true;
        objectToRemove = obj;
        switch (tabInt)
        {
            case 0:
                Person person = (Person) obj;
                editPerson(person);
                break;
            case 1:
                Exam exam = (Exam) obj;
                editExam(exam);
                break;
            case 2:
                Room room = (Room) obj;
                editRoom(room);
                break;
        }
    }

    public void editPerson(Person person)
    {
        personNameField.setText(person.getName());
        VIAIDField.setText(person.getViaID());
        isTeacherCheckbox.setSelected(person.isTeacher());
        // NEEDS TO SELECT WHAT WAS CHOSEN IN TABLE
    }
    public void editExam(Exam exam)
    {
        String duration = Double.toString(exam.getDuration());
        courseNameField.setText(exam.getCourseName());
        examDurationField.setText(duration);
        // NEEDS TO SELECT WHAT WAS CHOSEN IN CHOICE BOX
        isWrittenCheckBox.setSelected(exam.isWrittenExam());
        isGroupExamCheckBox.setSelected(exam.isGroupExam());
    }
    public void editRoom(Room room)
    {
        String capacity = Integer.toString(room.getCapacity());
        roomNameField.setText(room.getRoomName());
        capacityField.setText(capacity);
        hasHDMICheckBox.setSelected(room.hasHDMI());
        hasVGACheckBox.setSelected(room.hasVGA());
        hasProjectorCheckBox.setSelected(room.hasProjector());
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

        public void submitLabel(int label){
        switch (label)
        {
            case 0:
                confirmationPerson.setVisible(true);
                PauseTransition personPause = new PauseTransition(
                    Duration.seconds(2)
                );
                personPause.setOnFinished(
                    event -> confirmationPerson.setVisible(false)
                );
                personPause.play();
                break;
            case 1:
                confirmationExam.setVisible(true);
                PauseTransition examPause = new PauseTransition(
                    Duration.seconds(2)
                );
                examPause.setOnFinished(
                    event -> confirmationExam.setVisible(false)
                );
                examPause.play();
                break;
            case 2:
                confirmationRoom.setVisible(true);
                PauseTransition roomPause = new PauseTransition(
                    Duration.seconds(2)
                );
                roomPause.setOnFinished(
                    event -> confirmationRoom.setVisible(false)
                );
                roomPause.play();
                break;
        }
    }
}
