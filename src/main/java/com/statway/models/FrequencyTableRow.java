package com.statway.models;

public class FrequencyTableRow {
    private String classe;
    private float median;
    private int absoluteFrequency;
    private float relativeFrequency;
    private int cumulativeFrequency;

    public FrequencyTableRow(String classe, float median, int absoluteFrequency, float relativeFrequency, int cumulativeFrequency) {
        this.classe = classe;
        this.median = median;
        this.absoluteFrequency = absoluteFrequency;
        this.relativeFrequency = relativeFrequency;
        this.cumulativeFrequency = cumulativeFrequency;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public float getMedian() {
        return median;
    }

    public void setMedian(float median) {
        this.median = median;
    }

    public int getAbsoluteFrequency() {
        return absoluteFrequency;
    }

    public void setAbsoluteFrequency(int absoluteFrequency) {
        this.absoluteFrequency = absoluteFrequency;
    }

    public float getRelativeFrequency() {
        return relativeFrequency;
    }

    public void setRelativeFrequency(float relativeFrequency) {
        this.relativeFrequency = relativeFrequency;
    }

    public int getCumulativeFrequency() {
        return cumulativeFrequency;
    }

    public void setCumulativeFrequency(int cumulativeFrequency) {
        this.cumulativeFrequency = cumulativeFrequency;
    }
}
