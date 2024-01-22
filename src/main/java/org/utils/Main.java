package org.utils;

import org.utils.coverage.LogCoverageCalculator;

import org.jbpt.throwable.SerializationException;

public class Main {
    public static void main(String[] args) throws SerializationException {
        int[] logSizes = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
        String[] noiseTypes = {"oa", "ia", "ooo", "all"};
        double[] noiseLevels = {0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75, 2};
        for (int log = 0; log < 3; log++) {
            for (int logSize : logSizes) {
                String logFilePath = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV5/Logs/" + log + "-" + logSize + ".xes";
                String modelFilePath = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV5/Models/" + log + "-" + logSize + ".pnml";
                new ModelGenerator().generate(logFilePath, modelFilePath);

                for (String noiseType : noiseTypes) {
                    for (double noise : noiseLevels) {
                        logFilePath = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV5/Logs/" + log + "-" + logSize + "-" + noiseType + "-" + noise + ".xes";
                        modelFilePath = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV5/Models/" + log + "-" + logSize + "-" + noiseType + "-" + noise + ".pnml";

//                  String logFilePath = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/Log0/InjectActivity/Logs/log-" + logSize + "-" + noise + ".xes";
//                  new LogCoverageCalculator().calculateCompleteness(logFilePath);
                        new ModelGenerator().generate(logFilePath, modelFilePath);

//                  String rel = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/system.pnml";
//                  String ret = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/Logz5/" + noiseType + "/Models/model-" + logSize + "-" + noise + ".pnml";
//                  String rel = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/Logz6/" + noiseType + "/Logs/log-" + logSize + "-" + noise + ".xes";
//                  new EntropiaUtil().calculate(rel, ret);
                    }
                }
            }
        }

    }
}