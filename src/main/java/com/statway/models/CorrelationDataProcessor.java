package com.statway.models;

import com.statway.services.Correlation;

import java.util.List;

public class CorrelationDataProcessor implements Correlation {
    private final List<Float> listX;
    private final List<Float> listY;
    private final int N;

    public CorrelationDataProcessor(List<Float> listX, List<Float> listY){
        this.listX = listX;
        this.listY = listY;
        this.N = listX.size();
    }

    @Override
    public float getCorrelationCoefficient() {
        return (float) ((N*getSumXY() - getSumX()*getSumY())/(Math.sqrt((N*getSumXSquare() - Math.pow(getSumX(), 2))*(N*getSumYSaquare() - Math.pow(getSumY(), 2)))));
    }

    @Override
    public float getDeterminationCoefficient() {
        return (float) Math.pow(getCorrelationCoefficient(), 2);
    }

    @Override
    public float getBo() {
        return (float) (getSumY() - getB1()*getSumX())/N;
    }

    @Override
    public float getB1() {
        return (float) ((N*getSumXY() - getSumX()*getSumY())/(N*getSumXSquare() - Math.pow(getSumX(), 2)));
    }

    @Override
    public String getCorrelationClassification() {
        return "";
    }

    @Override
    public int getN() {
        return N;
    }

    private float getSumX(){
        float sum = 0;
        for (float value: listX)
            sum += value;
        return sum;
    }

    private float getSumY(){
        float sum = 0;
        for (float value: listY)
            sum += value;
        return sum;
    }

    private float getSumXSquare(){
        float sum = 0;
        for (float value: listX)
            sum += value*value;
        return sum;
    }

    private float getSumYSaquare(){
        float sum = 0;
        for (float value: listY)
            sum += value*value;
        return sum;
    }

    private float getSumXY(){
        float sum = 0;
        for (int i = 0; i < listX.size(); i++)
            sum += listX.get(i)*listY.get(i);
        return sum;
    }

}
