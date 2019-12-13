package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class ViewControllerMain {
    // ADD BUTTONS
    @FXML private Button personAdd;
    @FXML private Button roomAdd;
    @FXML private Button examAdd;

    // EDIT BUTTONS
    @FXML private Button personEdit;
    @FXML private Button roomEdit;
    @FXML private Button examEdit;

    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private TableView examScheduleTable;
    @FXML private TableView roomsTable;
    @FXML private TableView examsTable;
    @FXML private TableView personsTable;
    private Region root;
    private ExamCalendarController model;
    private ViewHandler viewHandler;

    public void init(ViewHandler viewHandler, ExamCalendarController model, Region root){
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;
        loadRoomsTab();
        loadExamsTab();
        loadPersonTab();
    }

    public void reset(){
        // Reset all text fiels and button states
    }

    public Region getRoot()
    {
        return root;
    }

    public void closeButtonPressed(){
        viewHandler.closeView();
    }

    public void generateExamScheduleButtonPressed()
    {
        LocalDate startLocalDate = startDate.getValue();
        LocalDate endLocalDate = endDate.getValue();
        Date startDate, endDate;

        startDate = new Date(startLocalDate.getDayOfMonth(), startLocalDate.getMonthValue(), startLocalDate.getYear());
        endDate = new Date(endLocalDate.getDayOfMonth(), endLocalDate.getMonthValue(), endLocalDate.getYear());
        ExamCalendar examSchedule = new ExamCalendar(startDate, endDate, model.getPersons(), model.getRooms(), model.getExams());

        ArrayList<ArrayList<Object>> plannedExamSchedule;
        plannedExamSchedule = examSchedule.generateExamSchedule();
        ArrayList<Date> examDates = new ArrayList<>();
        ArrayList<Room> examRooms = new ArrayList<>();
        ArrayList<Exam> examExams = new ArrayList<>();
        ArrayList<Person> examTeachers = new ArrayList<>();
        for (int i = 0; i < plannedExamSchedule.size(); i++)
        {
            Date examDate = (Date) plannedExamSchedule.get(i).get(0);
            Room examRoom = (Room) plannedExamSchedule.get(i).get(1);
            Exam examExam = (Exam) plannedExamSchedule.get(i).get(2);
            examDates.add(examDate);
            examRooms.add(examRoom);
            examExams.add(examExam);
        }
        ObservableList examScheduleData = FXCollections.observableList(examDates);

        examScheduleTable.setItems(examScheduleData);
        TableColumn col1 = new TableColumn("Date");
        TableColumn col2 = new TableColumn("Course Name");
        TableColumn col3 = new TableColumn("Room");
        TableColumn col4 = new TableColumn("Assigned Teacher");
        TableColumn col5 = new TableColumn("Exam Type");
        examScheduleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        col1.setPrefWidth(83);
        col1.setCellValueFactory(new PropertyValueFactory<Date, Date>("Date"));
        col2.setPrefWidth(122);
        // col2.setCellValueFactory(new PropertyValueFactory<String, String>("courseName"));
        col3.setPrefWidth(124);
        //col3.setCellValueFactory(new PropertyValueFactory<ArrayList<String>, ArrayList<String>>("assignedCourses"));
        col4.setPrefWidth(180);
       // col4.setCellValueFactory(new PropertyValueFactory<String, String>("role"));
        col5.setPrefWidth(96);
        examScheduleTable.getColumns().setAll(col1, col2, col3, col4, col5);

            String alertMessage = examSchedule.getSuccess();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText(alertMessage);
            alert.showAndWait();

        ExamCalendar.saveToJS(plannedExamSchedule);
    }
    public void addButtonPressed(ActionEvent event){
        Button buttonPressed = (Button) event.getSource();
        String type = buttonPressed.getId();
        try{
            switch (type){
                case "personAdd":
                    viewHandler.openView("AddObject", 0);
                    break;
                case "examAdd":
                    viewHandler.openView("AddObject",1);
                    break;
                case "roomAdd":
                    viewHandler.openView("AddObject",2);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadPersonTab()
    {
        ObservableList personData = FXCollections.observableList(model.getPersons());
        personsTable.setItems(personData);
        TableColumn courseCol1 = new TableColumn("VIA ID");
        TableColumn courseCol2 = new TableColumn("Name");
        TableColumn courseCol3 = new TableColumn("Courses");
        TableColumn courseCol4 = new TableColumn("Role");
        personsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        courseCol1.setPrefWidth(78);
        courseCol1.setCellValueFactory(new PropertyValueFactory<String, String>("viaID"));
        courseCol2.setPrefWidth(221);
        courseCol2.setCellValueFactory(new PropertyValueFactory<String, String>("name"));
        courseCol2.setStyle("-fx-alignment: CENTER;");
        courseCol3.setPrefWidth(296);
        courseCol3.setCellValueFactory(new PropertyValueFactory<ArrayList<String>, ArrayList<String>>("assignedCourses"));
        courseCol4.setPrefWidth(74);
        courseCol4.setCellValueFactory(new PropertyValueFactory<String, String>("role"));
        courseCol4.setStyle("-fx-alignment: CENTER;");

        personsTable.getColumns().setAll(courseCol1, courseCol2, courseCol3, courseCol4);
        personsTable.refresh();
    }

    public void loadExamsTab()
    {
        ObservableList examData = FXCollections.observableList(model.getExams());
        examsTable.setItems(examData);
        TableColumn courseCol1 = new TableColumn("Course Name");
        TableColumn courseCol2 = new TableColumn("Priority Room");
        TableColumn courseCol3 = new TableColumn("Duration");
        TableColumn courseCol4 = new TableColumn("Exam Type");
        examsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        courseCol1.setPrefWidth(192);
        courseCol1.setCellValueFactory(new PropertyValueFactory<String, String>("courseName"));
        courseCol2.setPrefWidth(227);
        courseCol2.setCellValueFactory(new PropertyValueFactory<Room, Room>("priorityRoom"));
        courseCol3.setPrefWidth(137);
        courseCol3.setStyle("-fx-alignment: CENTER;");
       // courseCol3.setCellValueFactory(new PropertyValueFactory<Double, String>("duration"));
        courseCol4.setPrefWidth(112);

        examsTable.getColumns().setAll(courseCol1, courseCol2, courseCol3, courseCol4);
        examsTable.refresh();
    }
    public void loadRoomsTab()
    {
        ObservableList roomData = FXCollections.observableList(model.getRooms());
        roomsTable.setItems(roomData);
        TableColumn courseCol1 = new TableColumn("Room Name");
        TableColumn courseCol2 = new TableColumn("Equipment");
        TableColumn courseCol3 = new TableColumn("Capacity");
        roomsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        courseCol1.setPrefWidth(213);
        courseCol1.setCellValueFactory(new PropertyValueFactory<String, String>("roomName"));
        // courseCol2.setPrefWidth(359);
        // courseCol2.setCellValueFactory(new PropertyValueFactory<String, String>("hasHDMI"));
        // courseCol3.setPrefWidth(98);
        // courseCol3.setCellValueFactory(new PropertyValueFactory<Integer, String>("studentCapacity"));

        roomsTable.getColumns().setAll(courseCol1, courseCol2, courseCol3);
        roomsTable.refresh();
    }
    public void editPersonPressed() {
        Person selectedPerson = (Person) personsTable.getSelectionModel().getSelectedItem();
        viewHandler.openView("AddObject", 0);
        viewHandler.carryObject(selectedPerson,0);
    }
    public void editExamPressed() {
        Exam selectedExam = (Exam) examsTable.getSelectionModel().getSelectedItem();
        viewHandler.openView("AddObject", 1);
        viewHandler.carryObject(selectedExam,1);

    }
    public void editRoomPressed() {
        Room selectedRoom = (Room) roomsTable.getSelectionModel().getSelectedItem();
        viewHandler.openView("AddObject", 2);
        viewHandler.carryObject(selectedRoom,2);
    }
    public void removePersonPressed()
    {
        Person selectedPerson = (Person) personsTable.getSelectionModel().getSelectedItem();
        model.getPersons().remove(selectedPerson);
        Person.saveToBinary(model.getPersons());
        loadPersonTab();
    }
    public void removeExamPressed()
    {
        Exam selectedExam = (Exam) examsTable.getSelectionModel().getSelectedItem();
        model.getExams().remove(selectedExam);
        Exam.saveToBinary(model.getExams());
        loadExamsTab();
    }
    public void removeRoomPressed()
    {
        Room selectedRoom = (Room) roomsTable.getSelectionModel().getSelectedItem();
        model.getRooms().remove(selectedRoom);
        Room.saveToBinary(model.getRooms());
        loadRoomsTab();
    }

}
