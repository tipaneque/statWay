package com.statway.controllers;

import com.statway.models.CorrelationDataProcessor;
import com.statway.services.Correlation;
import com.statway.utils.DataUtils;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CorrelationController implements Initializable {

    public Label pearson;
    public Label determination;
    public Label angular;
    public Label linear;
    public VBox chartCorrPane;
    ScatterChart<Number, Number> scatterChart;
    private Correlation correlation;
    private List<Float> dataX;
    private List<Float> dataY;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dataX = DataUtils.getInstance().getDataX();
        dataY = DataUtils.getInstance().getDataY();
        correlation = new CorrelationDataProcessor(dataX, dataY);

        setLabelValues();
        initChart();

    }

    void setLabelValues(){

        pearson.setText(correlation.getCorrelationCoefficient() + "");
        determination.setText(correlation.getDeterminationCoefficient() + "");
        angular.setText(correlation.getBo() + "");
        linear.setText(correlation.getB1() + "");

    }

    void initChart(){

        NumberAxis xAxys = new NumberAxis();
        xAxys.setLabel("Valores de x");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Valores de y");
        scatterChart = new ScatterChart<>(xAxys, yAxis);

        setChart();

    }

    private void setChart(){

        XYChart.Series series = new XYChart.Series();

        for(int i = 0; i < correlation.getN(); i++){
            series.getData().add(new XYChart.Data(dataX.get(i), dataY.get(i)));
        }
        scatterChart.getData().add(series);
        chartCorrPane.getChildren().add(scatterChart);

    }




}
