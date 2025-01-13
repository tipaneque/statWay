package com.statway.models;

import com.statway.services.Distribution;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DistributionDataProcessor implements Distribution {
    private final List<Float> DATA;
    private final int N;
    private final boolean isPopulation;

    public DistributionDataProcessor(List<Float> DATA, boolean isPopulation){

        this.DATA = DATA.stream().sorted().toList();
        this.N = DATA.size();
        this.isPopulation = isPopulation;

    }


    @Override
    public int getN() { return N; }

    @Override
    public float getMean() { return getSum()/ N; }

    @Override
    public float getMedian() { return getSep(1, 2); }

    @Override
    public float getMode() {

        List<Integer> absoluteFreq = getAbsoluteFrequency();
        int pos = -1;
        int max = 0;
        for(int i = 0; i < absoluteFreq.size(); i++){
            if(absoluteFreq.get(i) > max) {
                max = absoluteFreq.get(i);
                pos = i;
            }
        }

        int freqAnt;
        int freqPos;

        if(pos > 0){
            freqAnt = absoluteFreq.get(pos - 1);
            if(pos == absoluteFreq.size() - 1){
                freqPos = 0;
            }else {
                freqPos = absoluteFreq.get(pos + 1);
            }
        }else {
            freqAnt = 0;
            freqPos = absoluteFreq.get(pos + 1);

        }

        return (getMinimumValues().get(pos) + ((float)freqPos /(freqAnt + freqPos))*getH());

    }

    @Override
    public float getStandardDeviation() {return (float) Math.sqrt(getVariance());}

    @Override
    public float getVariance() {
        int n = isPopulation ? N : N - 1;
        List<Float> medianValues = getMedianValues();
        List<Integer> absoluteFreq = getAbsoluteFrequency();
        float mean = getMean();
        float sum = 0;

        for(int i = 0; i < medianValues.size(); i++){
            sum += (float) Math.pow((medianValues.get(i) - mean), 2)*absoluteFreq.get(i);
        }
        System.out.println("Soma: " + sum);

        return sum/ n;

    }

    @Override
    public float getCV() {return getStandardDeviation()/getMean();}

    @Override
    public float getQuartile(int index) {return getSep(index, 4);}

    @Override
    public float getDecile(int index) {return getSep(index, 10);}

    @Override
    public float getPercentile(int index) {return getSep(index, 100);}

    @Override
    public float getK() {

        if(N < 30)
            return 5;
        else if (N < 100)
            return (float) Math.sqrt(N);
        return (float) (1 + 3.322*Math.log10(N));

    }

    @Override
    public float getAS() {
        return (getMean() - getMode())/getStandardDeviation();
    }

    @Override
    public float getCustosisCoefficient() {
        return (getQuartile(3) - getQuartile(1))/(2*(getPercentile(90) - getPercentile(10)));
    }

    @Override
    public String classifySimetry() {

        return (getAS() > 0) ? "Positiva" : (getAS() < 0) ? "Negativa" : "Simétrica";
    }

    @Override
    public String classifyCurtosis() {
        return (getCustosisCoefficient() > 0.263) ? "Platicúrtica" : (getCustosisCoefficient() < 0.263) ? "Leptocúrtica" : "Mesocúrtica";
    }

    @Override
    public List<Integer> getAbsoluteFrequency() {
        List<Integer> absoluteFrequencyValues = new ArrayList<>();
        List<Float> minValues = getMinimumValues();
        List<Float> maxValues = getMaximumValues();
        int absoluteFrequency;

        for(int i = 0; i < minValues.size(); i++){
            absoluteFrequency = 0;
            for(float d: DATA){
                if(d >= minValues.get(i) && d < maxValues.get(i)){
                    absoluteFrequency++;
                }
            }
            absoluteFrequencyValues.add(absoluteFrequency);
        }
        return absoluteFrequencyValues;
    }

    @Override
    public List<Float> getRelativeFrequency() {
        return getAbsoluteFrequency().stream().map(d ->  d/ (float) N).toList();
    }

    @Override
    public List<Integer> getCumulativeFrequency() {
        List<Integer> absoluteFrequencyValues = getAbsoluteFrequency();
        List<Integer> cumulativeValues = new ArrayList<>();

        for(int i = 0; i < absoluteFrequencyValues.size(); i++){
            cumulativeValues.add( (i==0) ? absoluteFrequencyValues.get(i) : cumulativeValues.get(i -1) + absoluteFrequencyValues.get(i) );
        }

        return cumulativeValues;
    }

    @Override
    public List<Float> getMedianValues() {
        List<Float> medianValues = new ArrayList<>();
        List<Float> minValues = getMinimumValues();
        List<Float> maxValues = getMaximumValues();

        for(int i = 0; i < minValues.size(); i++){
            medianValues.add((minValues.get(i) + maxValues.get(i))/2);
        }

        return medianValues;
    }

    @Override
    public List<String> getClasses() {
        DecimalFormat formater = new DecimalFormat("##.###");
        List<String> classes = new ArrayList<>();
        List<Float> minValues = getMinimumValues();
        List<Float> maxValues = getMaximumValues();

        for(int i = 0; i < minValues.size(); i++){
            classes.add(formater.format(minValues.get(i)) + " - " + formater.format(maxValues.get(i)));
        }

        return classes;
    }

    @Override
    public float getH() {
        return (DATA.getLast() - DATA.getFirst())/getK();
    }

    private float getSum(){
        float sum = 0;
        for(float d: DATA)
            sum += d;
        return sum;
    }

    private List<Float> getMinimumValues(){
        List<Float> list = new ArrayList<>();
        float min = DATA.getFirst();

        int k = (int) Math.ceil(getK());
        float h = getH();
        for(int i = 0; i < k; i++){
            list.add(min);
            min += h;
        }

        return list;

    }

    private List<Float> getMaximumValues(){
        float h = getH();
        return getMinimumValues().stream().map(d -> d+ h).toList();
    }

    private float getSep(int index, int sepValue){
        float decisor = (index * (float) N)/sepValue;
        int pos = -1;
        List<Integer> cumulativeFreq = getCumulativeFrequency();
        for(int i = 0; i < cumulativeFreq.size(); i++){
            if(decisor < cumulativeFreq.get(i)){
                pos = i;
                break;
            }
        }

        return (getMinimumValues().get(pos) + ((decisor - ((pos == 0) ? 0 : cumulativeFreq.get(pos - 1)))/getAbsoluteFrequency().get(pos))*getH());
    }

    public float getMean2(){
        List<Float> medianValues = getMedianValues();;
        List<Integer> absoluteFrequency = getAbsoluteFrequency();
        float sum = 0;

        for(int i = 0; i < medianValues.size(); i++){
            sum += medianValues.get(i)*absoluteFrequency.get(i);
        }

        return sum/N;
    }
}
