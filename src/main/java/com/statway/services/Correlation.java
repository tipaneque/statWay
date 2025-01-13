package com.statway.services;

public interface Correlation {
    float getCorrelationCoefficient();
    float getDeterminationCoefficient();
    float getBo();
    float getB1();
    String getCorrelationClassification();
    int getN();
}
