package org.utils;

import org.utils.stats.ConfidenceInterval;

public class Main {
    public static void main(String[] args) {
        double[] data = { 55.97,
                56.53,
                56.57,
                55.23,
                55.24,
                56.63,
                55.72,
                56.84,
                56.24,
                57.03 };

        new ConfidenceInterval().calculateConfidenceInterval(data);
    }
}