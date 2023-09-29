package org.utils.stats;

import org.apache.commons.math3.distribution.NormalDistribution;

public class ConfidenceInterval {

    public void calculateConfidenceInterval(double[] data) {
        double mean = calculateMean(data);
        double stdDev = calculateStandardDeviation(data);

        double confidenceLevel = 0.95;

        NormalDistribution standardNormal = new NormalDistribution();
        double z = standardNormal.inverseCumulativeProbability(1 - (1 - confidenceLevel) / 2);

        double marginOfError = z * (stdDev / Math.sqrt(data.length));
        System.out.println("(" + mean + ", " + marginOfError + ")");
    }

    private double calculateMean(double[] data) {
        double sum = 0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.length;
    }

    private double calculateStandardDeviation(double[] data) {
        double mean = calculateMean(data);
        double sumOfSquaredDifferences = 0;
        for (double value : data) {
            sumOfSquaredDifferences += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sumOfSquaredDifferences / (data.length - 1));
    }
}
