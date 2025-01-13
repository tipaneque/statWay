package com.statway.controllers;

import com.statway.models.DistributionDataProcessor;
import com.statway.models.FrequencyTableRow;
import com.statway.services.Distribution;
import com.statway.utils.DataUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DistributionController implements Initializable {

    public Pane paneChar;
    @FXML
    private Label assimetryClassification;

    @FXML
    private Label assimteryCoefValue;

    @FXML
    private Label curtosisClassification;

    @FXML
    private Label curtosisCoefValue;

    @FXML
    private VBox frequencyTablePane;

    @FXML
    private Label labelDecil;

    @FXML
    private Label labelPercentil;

    @FXML
    private Label labelQuartil;

    @FXML
    private Label meanValue;

    @FXML
    private Label medianValue;

    @FXML
    private Label modeValue;

    @FXML
    private Spinner<Integer> spinnerDecil;

    @FXML
    private Spinner<Integer> spinnerPercentil;

    @FXML
    private Spinner<Integer> spinnerQuartil;

    @FXML
    private Label standardDeviationValue;

    @FXML
    private Label varianceValue;

    @FXML
    private Label variationCoefValue;

    @FXML
    public BarChart<String, Number> chart;

    private TableView<FrequencyTableRow> frequencyTable;


    private SpinnerValueFactory<Integer> spinnerValueFactoryQuartil;
    private SpinnerValueFactory<Integer> spinnerValueFactoryDecil;
    private SpinnerValueFactory<Integer> spinnerValueFactoryPercentil;


    private Distribution distribution;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boolean isPop = DataUtils.getInstance().isPopulation();
        List<Float> data = DataUtils.getInstance().getData();
        distribution = new DistributionDataProcessor(data, isPop);
        initCompontents();
        System.out.println("IsPOP recebido: " + isPop);

    }

    void initCompontents(){
        initTable();
        initSpinner();
        initChart();
        updateLabelOfSpinner();
        configHistogram(distribution.getClasses(), distribution.getAbsoluteFrequency());
        setLabelQuantileValues();
        setNumericalValues();
        setClassification();

    }

    void initTable(){
        frequencyTable = new TableView<>();
        TableColumn<FrequencyTableRow, String> colClasses = new TableColumn<>("Classes");
        colClasses.setCellValueFactory(new PropertyValueFactory<>("classe"));

        TableColumn<FrequencyTableRow, String> colMedian = new TableColumn<>("xi");
        colMedian.setCellValueFactory(new PropertyValueFactory<>("median"));

        TableColumn<FrequencyTableRow, String> colAbsoluteFreq = new TableColumn<>("Freq abs");
        colAbsoluteFreq.setCellValueFactory(new PropertyValueFactory<>("absoluteFrequency"));

        TableColumn<FrequencyTableRow, String> colRelativeFreq = new TableColumn<>("Freq rel");
        colRelativeFreq.setCellValueFactory(new PropertyValueFactory<>("relativeFrequency"));

        TableColumn<FrequencyTableRow, String> colCumulativeFreq = new TableColumn<>("Freq acum");
        colCumulativeFreq.setCellValueFactory(new PropertyValueFactory<>("cumulativeFrequency"));

        frequencyTable.getColumns().addAll(colClasses, colMedian, colAbsoluteFreq, colRelativeFreq, colCumulativeFreq);

        setTable();
    }

    private void setTable(){

        ObservableList<FrequencyTableRow> rows = FXCollections.observableArrayList();

        for(int i = 0; i < distribution.getK(); i++){
            rows.add(new FrequencyTableRow(distribution.getClasses().get(i), distribution.getMedianValues().get(i), distribution.getAbsoluteFrequency().get(i), distribution.getRelativeFrequency().get(i), distribution.getCumulativeFrequency().get(i)));
        }

        frequencyTable.setItems(rows);
        frequencyTable.setVisible(true);
        frequencyTablePane.getChildren().add(frequencyTable);

    }

    private void setClassification(){

        assimetryClassification.setText(distribution.classifySimetry());
        curtosisClassification.setText(distribution.classifyCurtosis());
    }

    private void initChart(){

        CategoryAxis xAxys = new CategoryAxis();
        xAxys.setLabel("Classes");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Freq absoluta");
        chart = new BarChart<>(xAxys, yAxis);

    }

    private void configHistogram(List<String> classes, List<Integer> absoluteFrequencies){

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for(int i = 0; i < absoluteFrequencies.size(); i++){
            series.getData().add(new XYChart.Data<>(classes.get(i), absoluteFrequencies.get(i)));
        }
        chart.getData().add(series);
        chart.setLegendVisible(false);
        paneChar.getChildren().add(chart);

    }

    private void setLabelQuantileValues(){

        labelQuartil.setText(distribution.getQuartile(spinnerQuartil.getValue()) + "");
        labelDecil.setText(distribution.getDecile(spinnerDecil.getValue()) + "");
        labelPercentil.setText(distribution.getPercentile(spinnerPercentil.getValue()) + "");

    }

    private void setNumericalValues(){

        meanValue.setText(String.valueOf(distribution.getMean2()));
        medianValue.setText(String.valueOf(distribution.getMedian()));
        modeValue.setText(String.valueOf(distribution.getMode()));
        varianceValue.setText(String.valueOf(distribution.getVariance()));
        standardDeviationValue.setText(String.valueOf(distribution.getStandardDeviation()));
        variationCoefValue.setText(String.valueOf(distribution.getCV()));
        assimteryCoefValue.setText(String.valueOf(distribution.getAS()));
        curtosisCoefValue.setText(String.valueOf(distribution.getCustosisCoefficient()));

    }

    private void initSpinner(){

        spinnerValueFactoryQuartil = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3);
        spinnerValueFactoryQuartil.setValue(1);
        spinnerValueFactoryDecil = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9);
        spinnerValueFactoryDecil.setValue(1);
        spinnerValueFactoryPercentil = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99);
        spinnerValueFactoryPercentil.setValue(1);

        spinnerQuartil.setValueFactory(spinnerValueFactoryQuartil);
        spinnerDecil.setValueFactory(spinnerValueFactoryDecil);
        spinnerPercentil.setValueFactory(spinnerValueFactoryPercentil);

    }

    private void updateLabelOfSpinner(){

        spinnerQuartil.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                labelQuartil.setText(distribution.getQuartile(spinnerQuartil.getValue()) +"");
            }
        });
        spinnerDecil.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                labelDecil.setText(distribution.getDecile(spinnerDecil.getValue()) + "");
            }
        });
        spinnerPercentil.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                labelPercentil.setText(distribution.getPercentile(spinnerPercentil.getValue()) + "");
            }
        });

    }

}

