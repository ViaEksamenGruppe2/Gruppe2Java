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
    private int PREF_MIN_WIDTH = 820;
    private int PREF_MIN_HEIGHT = 550;

    public ViewHandler(ExamCalendarController model){
        this.model = model;
        this.currentScene = new Scene(new Region());
    }
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        openView("ExamPlanner",0);
    }

    public void openView(String id, int tabInt) {
        Region root = null;
        switch (id){
            case "ExamPlanner":
                root = loadViewMain("ExamPlanner.fxml");
                PREF_MIN_HEIGHT = 550;
                PREF_MIN_WIDTH = 820;
                break;
            case "AddObject":
                root = loadViewAdd("AddObject.fxml",tabInt);
                PREF_MIN_HEIGHT = 450;
                PREF_MIN_WIDTH = 600;
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
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Region loadViewAdd(String fxmlFile, int tabInt) {
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
            viewControllerAdd.reset(tabInt);
        }
        viewControllerAdd.setSpecificTab(tabInt);
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
