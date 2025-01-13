package com.statway.applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource("/com/statway/views/home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), screen.getWidth()*0.8, screen.getHeight()*0.8);
        stage.setTitle("Statway!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}