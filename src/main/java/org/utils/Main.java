package org.utils;

import org.jbpt.throwable.SerializationException;

public class Main {
    public static void main(String[] args) throws SerializationException {
        int[] logSizes = {100, 500, 1000, 5000, 10000};
        for (int logSize : logSizes) {
            for (double noise = 0; noise <= 1; noise = noise + 0.25) {
//            for (int noise = 0; noise <= 21; noise = noise + 5) {
                String logFilePath = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/Logx/OutOfOrder/Logs/log-" + logSize + "-" + noise + ".xes";
                String modelFilePath = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/Logx/OutOfOrder/Models/model-" + logSize + "-" + noise + ".pnml";

//                String logFilePath = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/Log0/InjectActivity/Logs/log-" + logSize + "-" + noise + ".xes";
//                new LogCoverageCalculator().calculateCompleteness(logFilePath);
                new ModelGenerator().generate(logFilePath, modelFilePath);

//                String rel = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/system.pnml";
//                String ret = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/Logx/OutOfOrder/Models/model-" + logSize + "-" + noise + ".pnml";
//                new EntropiaUtil().calculate(rel, ret);
            }
        }
    }
}