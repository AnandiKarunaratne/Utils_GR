package org.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.math3.util.Pair;
import org.jbpt.pm.quality.EntropyPrecisionRecallMeasure;
import org.jbpt.pm.tools.QualityMeasuresCLI;

import java.io.FileWriter;
import java.util.Arrays;

public class Entropia {
    public void measure(String rel, String ret) {
        String file = "/Users/anandik/Downloads/bresults.csv";
        Object relevantTraces = QualityMeasuresCLI.parseModel(rel);
        Object retrievedTraces = QualityMeasuresCLI.parseModel(ret);
        EntropyPrecisionRecallMeasure epr = new EntropyPrecisionRecallMeasure(relevantTraces, retrievedTraces, 0, 0, true, true, false);
        try (FileWriter fileWriter = new FileWriter(file, true);
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
            Pair<Double, Double> result = epr.computeMeasure();

            String[] values = {Double.toString(result.getFirst()), Double.toString(result.getSecond())};
            csvPrinter.printRecord(Arrays.asList(values));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
