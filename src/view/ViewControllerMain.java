package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import model.ExamCalendarController;


public class ViewControllerMain {
    @FXML private Button personAdd;
    @FXML private Button roomAdd;
    @FXML private Button examAdd;
    private Region root;
    private ExamCalendarController model;
    private ViewHandler viewHandler;

    public void init(ViewHandler viewHandler, ExamCalendarController model, Region root){
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;
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
                    viewHandler.openView("AddObject");
                    break;
                case "examAdd":
                    viewHandler.openView("AddObject");
                    break;
                case "romAdd":
                    viewHandler.openView("AddObject");
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
