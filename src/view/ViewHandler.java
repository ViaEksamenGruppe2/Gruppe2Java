package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.ExamCalendarController;

public class ViewHandler {
    private Scene currentScene;
    private Stage primaryStage;
    private ExamCalendarController model;
    private ViewControllerMain viewControllerMain;
    private ViewControllerAdd viewControllerAdd;
    private final int PREF_MIN_WIDTH = 820;
    private final int PREF_MIN_HEIGHT = 550;

    public ViewHandler(ExamCalendarController model){
        this.model = model;
        this.currentScene = new Scene(new Region());
    }
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        openView("ExamPlanner");
    }

    public void openView(String id) {
        Region root = null;
        switch (id){
            case "ExamPlanner":
                root = loadViewMain("ExamPlanner.fxml");
                break;
            case "AddObject":
                root = loadViewAdd("AddObject.fxml");
                break;
        }
        currentScene.setRoot(root);
        String title = "";
        if (root.getUserData() != null){
            title += root.getUserData();
        }
        primaryStage.setTitle(title);
        primaryStage.setScene(currentScene);
        primaryStage.setWidth(PREF_MIN_WIDTH);
        primaryStage.setHeight(PREF_MIN_HEIGHT);
        primaryStage.show();

    }

    private Region loadViewAdd(String fxmlFile) {
        if (viewControllerAdd == null){
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                viewControllerAdd = loader.getController();
                viewControllerAdd.init(this, model, root);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            viewControllerAdd.reset();
        }
        return viewControllerAdd.getRoot();
    }

    private Region loadViewMain(String fxmlFile) {
        if (viewControllerMain == null){
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                viewControllerMain = loader.getController();
                viewControllerMain.init(this, model, root);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            viewControllerMain.reset();
        }
        return viewControllerMain.getRoot();
    }

    public void closeView(){
        primaryStage.close();
    }


}
