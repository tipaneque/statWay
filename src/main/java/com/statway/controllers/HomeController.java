package com.statway.controllers;

import com.jfoenix.controls.JFXSnackbar;
import com.statway.utils.DataUtils;
import com.statway.utils.Validation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public StackPane stackPaneCenter;
    public TextArea textAreaX;
    public TextArea textAreaY;
    public Label email;
    public VBox panelTextoCopiado;
    public VBox paneSuggestion;
    public HBox rbPanel;

    public Button distributionButton;
    public Button correlatiobButton;
    public BorderPane borderPane;
    public TitledPane titledPane;
    public RadioButton rbAmostra;

    public RadioButton rbPopulacao;
    public TextArea textAreaDados;
    public StackPane stacktEntries;
    public VBox paneDistribution;
    public RadioButton rbAgrupados;
    public Button buttonEnviar;
    public VBox paneSugestion;
    public VBox errorPane1;
    public VBox errorPane2;
    public VBox errorPane3;
    public VBox errorPane4;
    public VBox paneCorrelation;
    public RadioButton rbSimples;
    private String selectedPane;

    private Object currentController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initPanes();
        initToogleGroup();
        selectedPane = "Distribuição";

        email.setOnMouseEntered(e ->{
            email.setStyle("-fx-text-fill: blue;");
        });

        email.setOnMouseExited(event -> {
            email.setStyle("-fx-text-fill: black;");
        });

        distributionButton.setOnAction(e -> handleDistributionButton());
        correlatiobButton.setOnAction(e -> handleCorrelationButton());
        buttonEnviar.setOnAction(e -> handleButtonEnviar());
        email.setOnMouseClicked(event -> handleEmailOnMouseClicked());
        rbSimples.setOnMouseClicked(event -> {
            handleRadioButtonOnMouseClicked();
            rbAgrupados.setSelected(true);
        });


    }

    public void handleButtonEnviar(){

        if(selectedPane.equals("Distribuição")){

            if(isValidField(textAreaDados.getText().replaceAll(" ", ""))){

                DataUtils.getInstance().setPopulation(rbPopulacao.isSelected());
                System.out.println("isPop enviado: " + rbPopulacao.isSelected());
                DataUtils.getInstance().setData(mapStringToFloat(textAreaDados.getText()));
                loadPage("/com/statway/views/distribution-view.fxml");

            }else { showErrorMessage(); }


        }else {

            if (isValidField(textAreaX.getText().replaceAll("[\\s]", "")) && isValidField(textAreaY.getText().replaceAll("[\\s]", ""))){

                DataUtils.getInstance().setDataX(mapStringToFloat(textAreaX.getText()));
                DataUtils.getInstance().setDataY(mapStringToFloat(textAreaY.getText()));
                loadPage("/com/statway/views/correlation-view.fxml");

            }else { showErrorMessage(); }

        }

    }

    private void handleDistributionButton(){

        selectedPane = "Distribuição";
        titledPane.setText(selectedPane);
        titledPane.setExpanded(false);
        paneDistribution.setVisible(true);
        paneCorrelation.setVisible(false);

    }

    private void handleCorrelationButton(){

        selectedPane = "Correlação";
        titledPane.setText(selectedPane);
        titledPane.setExpanded(false);
        paneDistribution.setVisible(false);
        paneCorrelation.setVisible(true);

    }

    private void handleEmailOnMouseClicked(){

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(email.getText());
        clipboard.setContent(content);
        JFXSnackbar snackbar = new JFXSnackbar(panelTextoCopiado);
        snackbar.enqueue(new JFXSnackbar.SnackbarEvent(new Label("Texto copiado para a área de transferência!")));

    }

    private void handleRadioButtonOnMouseClicked(){

        JFXSnackbar snackbar = new JFXSnackbar(errorPane2);
        Label label = new Label("ATENÇÃO\nEsta funcionalidade só estará disponível na próxima versão");
        label.setStyle("-fx-text-fill: red");
        snackbar.enqueue(new JFXSnackbar.SnackbarEvent(label, Duration.millis(4500)));

    }

    void initPanes(){
        paneCorrelation.setVisible(false);

    }

    private List<Float> mapStringToFloat(String text){

        List<Float> data = new ArrayList<>();
        for(String value: text.replaceAll("[\\s]", "").split(","))
            data.add(Float.parseFloat(value));

        return data;

    }


    private boolean isValidField(String text){

        return !Validation.containsLetter(text) && Validation.isCommaSeparated(text) && !Validation.isMoreThanOneComma(text) && Validation.isOnlyAlphaNumeric(text);

    }

    private void showErrorMessage(){

        Label labelError = new Label("Erro");
        Label label1 = new Label("Entrada inválida");
        Label label2 = new Label("Corrija o erro");

        labelError.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: red");
        label1.setStyle("-fx-font-size: 14; -fx-text-fill: red;");
        label2.setStyle("-fx-font-size: 14; -fx-text-fill: red;");

        JFXSnackbar snackbar = new JFXSnackbar(paneSuggestion);
        JFXSnackbar snackbar1 = new JFXSnackbar(errorPane1);
        JFXSnackbar snackbar2 = new JFXSnackbar(errorPane2);

        snackbar.enqueue(new JFXSnackbar.SnackbarEvent(labelError));
        snackbar1.enqueue(new JFXSnackbar.SnackbarEvent(label1, Duration.millis(2500)));
        snackbar2.enqueue(new JFXSnackbar.SnackbarEvent(label2, Duration.millis(3000)));

    }

    private void loadPage(String fxmlFile){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node page = loader.load();
            currentController = loader.getController();

            stackPaneCenter.getChildren().clear();
            stackPaneCenter.getChildren().add(page);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void initToogleGroup(){

        ToggleGroup toggle1 = new ToggleGroup();
        ToggleGroup toggle2 = new ToggleGroup();

        rbSimples.setToggleGroup(toggle1);
        rbAgrupados.setToggleGroup(toggle1);
        rbAgrupados.setSelected(true);

        rbAmostra.setToggleGroup(toggle2);
        rbPopulacao.setToggleGroup(toggle2);
        rbPopulacao.setSelected(true);

    }




}