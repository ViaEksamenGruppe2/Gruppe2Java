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
    private Region root;
    private ExamCalendarController model;
    private ViewHandler viewHandler;

    public void init(ViewHandler viewHandler, ExamCalendarController model, Region root){
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;
        loadRoomsTab();
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
