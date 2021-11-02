package com.example.project3fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * The Main class sets up the stage and runs it
 *
 * @author Isaac Brukhman
 */
public class Main extends Application {
    /**
     * start makes our program with the fxml file
     * @param stage that's used as the default stage
     *
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("project3.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 720, 720);
            stage.setTitle("Tuition Screen!");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * main launches our program
     * @param args that is the command line
     */
    public static void main(String[] args) {
        launch();
    }
}