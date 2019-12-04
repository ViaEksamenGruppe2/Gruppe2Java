package view;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import model.ExamCalendarController;

public class ViewControllerAdd {
    private Region root;
    private ExamCalendarController model;
    private ViewHandler viewHandler;

    public void init(ViewHandler viewHandler, ExamCalendarController model, Region root){
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;
        reset();

    }

    public void reset(){
       // Reset all text fiels and button states
    }

    public Region getRoot()
    {
        return root;
    }

    @FXML public void closeButtonPressed(){
        viewHandler.closeView();
    }

    @FXML public void submitButtonPressed(){
        //Add swich to call depenging on the tab
        reset();
    }
}
