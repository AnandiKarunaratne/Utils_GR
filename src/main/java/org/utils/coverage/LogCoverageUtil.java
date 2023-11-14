package org.utils.coverage;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;

public class LogCoverageUtil {

    public void calculate() {
        String csvFilePath = "/Users/anandik/Downloads/logs.csv";
        String file = "/Users/anandik/Downloads/coverage.csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFilePath));
            CSVWriter writer = new CSVWriter(new FileWriter(file));

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                for (String value : nextLine) {
                    String logFilePath = "/Users/anandik/OneDrive - The University of Melbourne/BootstrapGeneralization" + value.substring(1);
                    double coverage = new LogCoverageCalculator().calculateCoverage(logFilePath);
                    String[] data = {String.valueOf(coverage)};
                    writer.writeNext(data);
                }

            }
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
