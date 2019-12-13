package view;

import javafx.fxml.FXMLLoader;
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
    private int prefMinWidth = 820;
    private int prefMinHeight = 550;

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
                prefMinHeight = 550;
                prefMinWidth = 820;
                break;
            case "AddObject":
                root = loadViewAdd("dataInputWindow.fxml",tabInt);
                prefMinHeight = 450;
                prefMinWidth = 600;
                break;
        }
        currentScene.setRoot(root);
        String title = "";
        if (root.getUserData() != null){
            title += root.getUserData();
        }
        primaryStage.setTitle(title);
        primaryStage.setScene(currentScene);
        primaryStage.setWidth(prefMinWidth);
        primaryStage.setHeight(prefMinHeight);
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
                viewControllerMain.loadRoomsTab();
                viewControllerMain.loadPersonTab();
                viewControllerMain.loadExamsTab();
                viewControllerMain.loadExamCalendarTab();
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

    public void carryObject(Object obj, int tabInt)
    {
        viewControllerAdd.editObject(obj, tabInt);
    }
}
