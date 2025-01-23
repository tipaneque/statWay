package com.statway.applications;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

public class HomeApplication extends Application {

    private static final String PREF_NODE = "com.statway.StatWayApp4";
    private static final String EXECUTION_COUNT_KEY = "ExecutionCount";
    private static final int MAX_EXECUTIONS = 5;
    private static final int SESSION_DURATION_MINUTES = 2;

    @Override
    public void start(Stage stage) throws IOException {

        Preferences prefs = Preferences.userRoot().node(PREF_NODE);

        int executionCount = prefs.getInt(EXECUTION_COUNT_KEY, 0);

        if (executionCount >= MAX_EXECUTIONS) {
            //showAlertAndExit("Limite de execuções atingido! A aplicação está bloqueada.");
            stage.setScene(new Scene(new FXMLLoader(HomeApplication.class.getResource("/com/statway/views/app-blocked-view.fxml")).load(), 600, 400));
            stage.show();
            return;
        }

        prefs.putInt(EXECUTION_COUNT_KEY, executionCount + 1);

        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource("/com/statway/views/home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), screen.getWidth()*0.8, screen.getHeight()*0.8);
        stage.setTitle("Statway!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

        scheduleShutdown();
        showExecutionMessage(executionCount + 1);
    }

    private void showAlertAndExit(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aplicação Bloqueada");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    private void scheduleShutdown() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            Platform.runLater( () -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Tempo Expirado");
                alert.setHeaderText("A aplicação será fechada.");
                alert.setContentText("O tempo máximo de execução foi atingido.");
                alert.showAndWait();
                System.exit(0);
            } );
        }, SESSION_DURATION_MINUTES, TimeUnit.MINUTES);
    }

    private void showExecutionMessage(int currentExecution) {
        System.out.println("Esta é a execução número " + currentExecution + " de " + MAX_EXECUTIONS + ".");
    }


    public static void main(String[] args) {
        System.setProperty("java.util.logging.config.file", "logging.properties");
        launch();
    }
}