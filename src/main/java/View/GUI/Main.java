package View.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader programListLoader = new FXMLLoader();
        programListLoader.setLocation(getClass().getResource("ProgramMenuController.fxml"));
        Parent mainWindow = programListLoader.load();
        Scene programListScene = new Scene(mainWindow, 500, 550);
        ProgramMenuController programMenuController = programListLoader.getController();
        primaryStage.setTitle("Select program");
        primaryStage.setScene(programListScene);
        primaryStage.show();

        FXMLLoader programExecutorLoader = new FXMLLoader();
        programExecutorLoader.setLocation(getClass().getResource("ProgramExecutorController.fxml"));
        Parent programExecutorWindow = programExecutorLoader.load();
        Scene programExecutorScene = new Scene(programExecutorWindow, 500, 550);
        ProgramExecutorController programExecutorController = programExecutorLoader.getController();
        programMenuController.setProgramExecutorController(programExecutorController);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Program executor");
        secondaryStage.setScene(programExecutorScene);
        programMenuController.setSecondaryStage(secondaryStage);
    }
}
