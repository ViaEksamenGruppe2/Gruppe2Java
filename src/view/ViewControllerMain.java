package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import model.ExamCalendarController;
import model.Room;

import java.util.ArrayList;

public class ViewControllerMain {
    @FXML private Button personAdd;
    @FXML private Button roomAdd;
    @FXML private Button examAdd;
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

    public void submitButtonPressed(){
        //Add swich to call depenging on the tab
        reset();
    }
    public void addButtonPressed(ActionEvent event){
        //Add swich to call depenging on the tab
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
        courseCol3.setPrefWidth(296);
        courseCol3.setCellValueFactory(new PropertyValueFactory<ArrayList<String>, ArrayList<String>>("assignedCourses"));
        courseCol4.setPrefWidth(74);
        courseCol4.setCellValueFactory(new PropertyValueFactory<String, String>("role"));
        personsTable.getColumns().setAll(courseCol1, courseCol2, courseCol3, courseCol4);
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
       // courseCol3.setCellValueFactory(new PropertyValueFactory<Double, String>("duration"));
        courseCol4.setPrefWidth(112);

        examsTable.getColumns().setAll(courseCol1, courseCol2, courseCol3, courseCol4);
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

    }
}
