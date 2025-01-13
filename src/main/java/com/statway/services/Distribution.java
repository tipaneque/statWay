package com.statway.services;

import java.util.List;

public interface Distribution {
    int getN();
    float getMean();
    float getMean2();
    float getMedian();
    float getMode();
    float getStandardDeviation();
    float getVariance();
    float getCV();
    float getQuartile(int index);
    float getDecile(int index);
    float getPercentile(int index);
    float getK();
    float getAS();
    float getCustosisCoefficient();
    String classifySimetry();
    String classifyCurtosis();
    List<Integer> getAbsoluteFrequency();
    List<Float> getRelativeFrequency();
    List<Integer> getCumulativeFrequency();
    List<Float> getMedianValues();
    List<String> getClasses();
    float getH();
}
