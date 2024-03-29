package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import model.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
    private ModelControllerInterface model;
    private ViewHandler viewHandler;
    private ArrayList<ArrayList<Object>> plannedSchedule;

    public void init(ViewHandler viewHandler, ModelControllerInterface model, Region root){
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;
        loadRoomsTab();
        loadExamsTab();
        loadPersonTab();
    }

    public void reset(){
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
        //Clears non-personal date reservations
        int examSize = model.getExams().size(), roomSize = model.getRooms().size(), personSize = model.getPersons().size();
        for (int i = 0; i < examSize; i++) {
            model.getExams().get(i).getPrivateCalendar().removeAllNonPersonalDates();

            // THIS IS NEEDED TO CLEAR THE LAST OF THE PRIVATE CALENDAR.
            // APPARENTLY ATTENDEES HAVE A DIFFERENT CALENDAR FROM THE SINGLE PERSON
            for (int j = 0; j < model.getExams().get(i).getAttendees().size(); j++)
            {
                model.getExams().get(i).getAttendees().get(j).getPrivateCalendar().removeAllNonPersonalDates();
            }
        }
        for (int i = 0; i < roomSize; i++) {
            model.getRooms().get(i).getPrivateCalendar().removeAllNonPersonalDates();
        }
        for (int i = 0; i < personSize; i++) {
            model.getPersons().get(i).getPrivateCalendar().removeAllNonPersonalDates();
        }

        LocalDate startLocalDate = startDate.getValue();
        LocalDate endLocalDate = endDate.getValue();
        Date startDate, endDate;

        // Check to see if date isn't chosen
        if (startLocalDate == null || endLocalDate == null)
        {
            String alertMessage = "Please input a start and end date";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No start or end date");
            alert.setHeaderText(null);
            alert.setContentText(alertMessage);
            alert.showAndWait();
        }
        else
        {
            startDate = new Date(startLocalDate.getDayOfMonth(), startLocalDate.getMonthValue(), startLocalDate.getYear());
            endDate = new Date(endLocalDate.getDayOfMonth(), endLocalDate.getMonthValue(), endLocalDate.getYear());

            if (endDate.isBefore(startDate))
            {
                String alertMessage = "End date can't be before start date.\nPlease try again.";
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Date error");
                alert.setHeaderText(null);
                alert.setContentText(alertMessage);
                alert.showAndWait();
            }
            else {
            // Check to see if there's persons, exams and rooms
            if (model.getExams().isEmpty() || model.getRooms().isEmpty() || model.getPersons().isEmpty())
            {
                String alertMessage = "There needs to be persons, exams and rooms for the scheduler to run.\nPlease add some data before using the scheduler.";
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Missing data");
                alert.setHeaderText(null);
                alert.setContentText(alertMessage);
                alert.showAndWait();
            }
            else
            {
                // Logic to find out if exams has teachers or not.
                // Also adds all the missing teachers to an ArrayList to be printed out
                boolean teacher = false;
                String examsMissingTeacher = "";
                for (int i = 0; i < model.getExams().size(); i++)
                {
                    if (!model.getExams().get(i).hasTeacher())
                    {
                        for (int j = 0; j < model.getExams().size(); j++)
                        {
                            if (!model.getExams().get(j).hasTeacher())
                            {
                                examsMissingTeacher += model.getExams().get(j).getCourseName() + ", ";
                            }
                        }
                        break;
                    }
                    else
                    {
                        teacher = true;
                    }
                }

                // Check to see if there's teachers to each exam
                if (!teacher)
                {
                    String alertMessage = "There needs to at least one teacher for each exam.\nPlease add teachers before using the scheduler."
                        + "\n These courses are missing teachers:\n"
                        + examsMissingTeacher;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Missing data");
                    alert.setHeaderText(null);
                    alert.setContentText(alertMessage);
                    alert.showAndWait();
                }
                else
                {
                    model.setExamCalendar(startDate, endDate);
                    ExamCalendar examSchedule = model.getExamCalendar();

                    ArrayList<ArrayList<Object>> plannedExamSchedule;
                    plannedSchedule = examSchedule.generateExamSchedule();
                    ArrayList<Date> examDates = new ArrayList<>();
                    ArrayList<Room> examRooms = new ArrayList<>();
                    ArrayList<Exam> examExams = new ArrayList<>();
                    ArrayList<Person> examTeachers = new ArrayList<>();
                    for (int i = 0; i < plannedSchedule.size(); i++)
                    {
                        Date examDate = (Date) plannedSchedule.get(i).get(0);
                        Room examRoom = (Room) plannedSchedule.get(i).get(1);
                        Exam examExam = (Exam) plannedSchedule.get(i).get(2);
                        examDates.add(examDate);
                        examRooms.add(examRoom);
                        examExams.add(examExam);
                    }
                    ObservableList examScheduleDates = FXCollections.observableList(examDates);
                    ObservableList examScheduleRooms = FXCollections.observableList(examRooms);
                    ObservableList examScheduleExam = FXCollections.observableList(examExams);
                    ObservableList examScheduleData = FXCollections.observableList(plannedSchedule);

                    TableColumn col1 = new TableColumn("Date");
                    TableColumn col2 = new TableColumn("Course Name");
                    TableColumn col3 = new TableColumn("Room");
                    TableColumn col4 = new TableColumn("Assigned Teacher");
                    TableColumn col5 = new TableColumn("Exam Type");
                    examScheduleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    col1.setPrefWidth(83);
                    examScheduleTable.setItems(examScheduleExam);
                    col1.setCellValueFactory(new PropertyValueFactory<ArrayList<String>, ArrayList<String>>("privateCalendar"));
                    col2.setPrefWidth(122);
                    col2.setCellValueFactory(new PropertyValueFactory<String, String>("courseName"));
                    col3.setPrefWidth(124);
                    //col3.setCellValueFactory(new PropertyValueFactory<ArrayList<String>, ArrayList<String>>("assignedCourses"));
                    col4.setPrefWidth(180);
                    //col4.setCellValueFactory(new PropertyValueFactory<ArrayList<String>, ArrayList<String>>("getTeacher"));
                    col5.setPrefWidth(96);
                    col5.setCellValueFactory(new PropertyValueFactory<String, String>("type"));
                    examScheduleTable.getColumns().setAll(col1, col2, col3, col4, col5);




                    String alertMessage = examSchedule.getSuccess();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText(alertMessage);
                    alert.showAndWait();
                    model.getExamCalendar().saveToJS(plannedSchedule);
                }
                }
            }
        }
    }
    public void sendInfoPressed()
    {
        String alertMessage = "";
        if(plannedSchedule != null){
            String filename = "src/savefiles/dataToService.txt";
            File file = new File(filename);

            try {
                PrintWriter out = new PrintWriter(file); //Opens the file
                out.println("Dear service department");
                out.println("The following are rooms that need to be ready for written exams:");
                for (int i = 0; i < plannedSchedule.size(); i++) {
                    Exam exam = (Exam) plannedSchedule.get(i).get(2);
                    Room room = (Room) plannedSchedule.get(i).get(1);
                    if (exam.isWrittenExam()){
                        String output = "[" + plannedSchedule.get(i).get(0).toString() + "] ";
                        output += "Room: " + room.getRoomName() + " needs to be ready for " + exam.getAllStudents().size() + " students";
                        out.println(output);
                    }
                }
                out.println("Best regards");
                out.println("Department of exam planning");
                out.flush();
                out.close();
                alertMessage = "File was generated and is located in src/saveFiles/dataToService.txt";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            alertMessage = "Please generate an exam schedule before trying to send data to service department";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();

        // Generate a txt file here with date, room, written exams
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

    public void loadExamCalendarTab()
    {
        startDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty)
            {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        startDate.setEditable(false);

        endDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty)
            {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        endDate.setEditable(false);
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
        courseCol3.setCellValueFactory(new PropertyValueFactory<Double, String>("durationString"));
        courseCol4.setPrefWidth(112);
        courseCol4.setCellValueFactory(new PropertyValueFactory<Double, String>("type"));

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
        courseCol2.setPrefWidth(359);
        courseCol2.setCellValueFactory(new PropertyValueFactory<String, String>("equipment"));
        courseCol3.setPrefWidth(98);
        courseCol3.setCellValueFactory(new PropertyValueFactory<Integer, String>("capacityString"));

        roomsTable.getColumns().setAll(courseCol1, courseCol2, courseCol3);
        roomsTable.refresh();
    }
    public void editPersonPressed() {
        Person selectedPerson = (Person) personsTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null){
            viewHandler.openView("AddObject", 0);
            viewHandler.carryObject(selectedPerson,0);
        }
    }
    public void vacationPressed(){
        Person selectedPerson = (Person) personsTable.getSelectionModel().getSelectedItem();
        if (selectedPerson.isTeacher()){
            viewHandler.openView("AddObject", 3);
            viewHandler.carryObject(selectedPerson,3);
        }
        else {
            String alertMessage = "Only teachers can be chosen when adding vacation.";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong Select");
            alert.setHeaderText(null);
            alert.setContentText(alertMessage);
            alert.showAndWait();
        }
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
        model.removePerson(selectedPerson);
        model.removePersonExams(selectedPerson, selectedPerson.getExams());
        Person.saveToBinary(model.getPersons());
        Exam.saveToBinary(model.getExams());
        loadPersonTab();
    }
    public void removeExamPressed()
    {
        Exam selectedExam = (Exam) examsTable.getSelectionModel().getSelectedItem();
        model.removeExam(selectedExam);
        Exam.saveToBinary(model.getExams());
        loadExamsTab();
    }
    public void removeRoomPressed()
    {
        Room selectedRoom = (Room) roomsTable.getSelectionModel().getSelectedItem();
        model.removeRoom(selectedRoom);
        Room.saveToBinary(model.getRooms());
        loadRoomsTab();
    }

}
