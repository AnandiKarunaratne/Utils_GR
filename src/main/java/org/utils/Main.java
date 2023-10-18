package org.utils;

import org.jbpt.throwable.SerializationException;
import org.utils.files.pnml.PNMLGenerator;
import org.utils.models.DFG;
import org.utils.models.DFGToSDFAConverter;
import org.utils.models.SDFA;
import org.utils.models.SDFAToPetriNetConverter;

public class Main {
    public static void main(String[] args) throws SerializationException {
//        for (int logSize = 100; logSize <= 100000; logSize = logSize * 10) {
//            for (int noise = 0; noise < 21; noise = noise + 5) {
//                String logFilePath = "/Users/anandik/Documents/LogQuality/Logs/OOO-2/log-" + logSize + "/log-" + logSize + "-" + noise + ".xes";
//                String modelFilePath = "/Users/anandik/Documents/LogQuality/Models/OOO-2/model-" + logSize + "/model-" + logSize + "-" + noise + ".pnml";
//
//                DFG dfg = new DFG(logFilePath, 1.0);
//                SDFA sdfa = new DFGToSDFAConverter().convertToSDFA(dfg);
//                new PNMLGenerator().generatePNML(new SDFAToPetriNetConverter(sdfa).convertToPetriNet(), modelFilePath);
//            }
//        }

        int[] logSizes = {100, 500, 1000, 5000, 10000};

        for (int i : logSizes) {
            for (int j = 0; j < 21; j = j + 5) {
                for (int k = 0; k < 10; k++) {
                    String logFilePath = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/Log" + k + "/OmitActivity/Logs/log-" + i + "-" + j + ".xes";
                    String modelFilePath = "/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/Log" + k + "/OmitActivity/Models/model-" + i + "-" + j + ".pnml";
                    new Test().xxx(logFilePath, modelFilePath);
                }
            }
        }
//        new Test().xxx("","/Users/anandik/OneDrive - The University of Melbourne/GR/Experiments/LogQualityV3/model.pnml");



    }
}