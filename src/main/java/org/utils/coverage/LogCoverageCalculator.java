package org.utils.coverage;

import org.utils.files.xes.XESReader;
import org.utils.log.Trace;

import java.util.*;

public class LogCoverageCalculator {
    // test this class

    public double calculateCompleteness(String logFilePath) {
        List<Trace> traces = new XESReader().readXES(logFilePath);
        Set<Trace> distinctTraces = new HashSet<>(traces);
        int obs = distinctTraces.size();
        Map<Integer, List<Trace>> traceOccurrences = groupTraces(traces);
        int q1 = traceOccurrences.getOrDefault(1, new ArrayList<>()).size();
        double richness;
        if (traceOccurrences.containsKey(2)) {
            int q2 = traceOccurrences.get(2).size();
            richness = obs + (double) (q1 * q1) / (2 * q2);
        } else {
            richness = obs + (double) q1 * (q1 - 1) / 2;
        }

        double completeness = (double) distinctTraces.size() / richness;
        System.out.println(completeness);
        return completeness;
    }

    public double calculateCoverage(String logFilePath) {
        List<Trace> traces = new XESReader().readXES(logFilePath);
        Map<Integer, List<Trace>> traceOccurrences = groupTraces(traces);
        int q1 = traceOccurrences.getOrDefault(1, new ArrayList<>()).size();
        int q2 = traceOccurrences.getOrDefault(2, new ArrayList<>()).size();
        int n = traces.size();
        double part1 = (double) q1 * (n - 1) / ((n - 1) * q1 + 2 * q2);
        double part2 = (double) q1 / n;
        double coverage = 1 - part1 * part2;
        System.out.println(coverage);
        return coverage;
    }

    public void coverageV2(String logFilePath) {
        List<Trace> traces = new XESReader().readXES(logFilePath);
        Set<Trace> distinctTraces = new HashSet<>(traces);
        System.out.println((double) distinctTraces.size()/traces.size());

    }

    public static Map<Integer, List<String>> countDistinctSubstrings(List<String> strings) {
        // Create a HashMap to store substrings and their counts
        Map<String, Integer> substringCounts = new HashMap<>();

        // Iterate through the list of strings
        for (String str : strings) {
            // Generate and count distinct substrings of length 2
            for (int i = 0; i < str.length() - 1; i++) {
                String substring = str.substring(i, i + 2);
                substringCounts.put(substring, substringCounts.getOrDefault(substring, 0) + 1);
            }
        }

        // Create a HashMap to group strings by their counts
        Map<Integer, List<String>> result = new HashMap<>();

        // Iterate through the substrings and group them by counts
        for (Map.Entry<String, Integer> entry : substringCounts.entrySet()) {
            String substring = entry.getKey();
            int count = entry.getValue();

            // Initialize the list for this count if it doesn't exist
            result.computeIfAbsent(count, k -> new ArrayList<>()).add(substring);
        }

        return result;
    }

    private static HashMap<Integer, List<Trace>> groupTraces(List<Trace> inputList) {
        // Create a HashMap to count the occurrences of each distinct string
        HashMap<Trace, Integer> traceCount = new HashMap<>();

        for (Trace trace : inputList) {
            // Update the count for each string
            traceCount.put(trace, traceCount.getOrDefault(trace, 0) + 1);
        }

        // Create a HashMap to group strings by the count of occurrences
        HashMap<Integer, List<Trace>> groupedStrings = new HashMap<>();

        for (Trace trace : traceCount.keySet()) {
            int count = traceCount.get(trace);

            // If the count is not in the groupedStrings map, create a new list for it
            groupedStrings.putIfAbsent(count, new ArrayList<>());

            // Add the string to the list associated with its count
            groupedStrings.get(count).add(trace);
        }

        return groupedStrings;
    }
}
