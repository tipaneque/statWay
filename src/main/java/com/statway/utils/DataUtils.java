package com.statway.utils;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    private static final DataUtils instance = new DataUtils();
    private List<Float> data;
    private List<Float> dataX;
    private List<Float> dataY;
    private boolean isPopulation;

    private DataUtils(){
        data = new ArrayList<>();
        dataX = new ArrayList<>();
        dataY = new ArrayList<>();
        isPopulation = true;
    }

    public static DataUtils getInstance(){
        return instance;
    }

    public void setData(List<Float> data){
        System.out.println("Recebi " + data.size() + " dados");
        this.data = data;
    }

    public List<Float> getData(){
        if (data == null){
            System.out.println("Brow, nao tou a retornar nada");
        }else {
            System.out.println("Retornando " + data.size() + " dados");

        }
        return data;

    }

    public List<Float> getDataX() {
        return dataX;
    }

    public void setDataX(List<Float> dataX) {
        this.dataX = dataX;
    }

    public List<Float> getDataY() {
        return dataY;
    }

    public void setDataY(List<Float> dataY) {
        this.dataY = dataY;
    }

    public boolean isPopulation() {
        return isPopulation;
    }

    public void setPopulation(boolean population) {
        isPopulation = population;
    }
}
