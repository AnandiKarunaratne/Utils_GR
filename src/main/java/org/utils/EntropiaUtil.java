package org.utils;

import org.processmining.plugins.filter.noise.NoiseLogFilter;

public class EntropiaUtil {

    public void calculate(String rel, String ret) {
//        int[] logSizes = {100, 500, 1000, 5000, 10000};
//        String[] noiseTypes = {"OmitActivity", "InjectActivity", "OutOfOrder"};
//        for (String noiseType: noiseTypes) {
//            for (int logSize : logSizes) {
//                for (int noise = 0; noise < 21; noise = noise + 5) {
//                String ret = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/Log0/OutOfOrder/Models/model-" + logSize + "-" + noise + ".pnml";
//                String ret = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/Log0/" + noiseType + "/Models/model-" + logSize + "-" + noise + ".pnml";
//                    String rel = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/Log0/"+ noiseType +"/Logs/log-" + logSize + "-" + noise + ".xes";
//                    String rel = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/system.pnml";
                    new Entropia().measure(rel, ret);
                }
//            }
//        }
//    }

}
