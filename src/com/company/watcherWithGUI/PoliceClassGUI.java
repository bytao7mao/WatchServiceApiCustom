package com.company.watcherWithGUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.nio.file.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import static com.company.ColorsClass.ANSI_RED;
import static com.company.ColorsClass.ANSI_YELLOW_BACKGROUND;
import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Created by taozen on 10/13/2018.
 */
public class PoliceClassGUI extends Application {


    Button button;
    Button getPath;
    TextField pathName;
    Text syncText;
    Text showListenText;
    public static String pathForClient = "";
    Scene scene;



    @FXML
    @Override
    public void start(Stage primaryStage) throws Exception {

        Text scene_title = new Text("Watching O_o");


        Label pathText = new Label("path to listen: ");
        pathName = new TextField();
        syncText = new Text("here");
        showListenText = new Text("there");
        button = new Button("Done");
        getPath = new Button("getpath");
        HBox hbtn = new HBox(10);
        hbtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbtn.getChildren().add(button);
        hbtn.getChildren().add(getPath);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(scene_title, 0, 0, 2, 1);
        grid.add(showListenText, 0, 0, 2, 2);
        grid.add(pathText, 0, 2);
        grid.add(pathName, 1, 2);
        grid.add(syncText, 1,3);
        grid.add(hbtn, 1, 4);
        grid.setGridLinesVisible(false);

        scene = new Scene(grid, 300, 275);

        primaryStage.setTitle("POLICE IS HERE!");
        primaryStage.setScene(scene);
        primaryStage.show();

        getPathBtn();

//        Runnable runnable = () -> {
//            doneClick(primaryStage);
//        };
//        Thread thread = new Thread(runnable);
//        thread.setName("Tao's Thread");
//        thread.start(); // YES !

        new Thread(() -> doneClick(primaryStage)).start();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

    }

    @Override
    public void stop() {
        System.out.println("Stage is closing");
        // Save file

    }

    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }


    void getPathBtn() {
        getPath.setOnAction(event -> {
            pathForClient = pathName.getText();
            showListenText.setText(pathForClient);
            System.out.println(pathForClient);
        });
    }

    void doneClick(Stage primaryStage) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                try {
//                    runWatcher();

//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                closeWindow(primaryStage);
            }
        });
    }

    void closeWindow(Stage stage) {
        stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}

