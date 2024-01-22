package org.utils.models;

import org.utils.files.xes.XESReader;
import org.utils.log.Trace;

import java.util.*;

/**
 * this is not used.
 * use ModelGenerator instead.
 */
public class DFG {
    Set<String> actions;
    Set<String> directlyFollows;

    public DFG(String logFilePath, double filterThreshold) {
        createDFG(logFilePath, filterThreshold);
    }

    private void createDFG(String logFilePath, double filterThreshold) {
        List<Trace> traces = new XESReader().readXES(logFilePath);
        List<String> filteredTraces = filterTraces(traces, filterThreshold);

        Map<String, HashSet<String>> model = new HashMap<>();
        for (String maxTrace : filteredTraces) {
            for (int i = 0; i < maxTrace.length() - 1; i++) {
                String currentActivity = String.valueOf(maxTrace.charAt(i));
                String nextActivity = String.valueOf(maxTrace.charAt(i+1));
                if (!model.containsKey(currentActivity)) {
                    model.put(currentActivity, new HashSet<>());
                }
                model.get(currentActivity).add(nextActivity);
            }
        }
        Set<String> derivedActions = new HashSet<>();
        derivedActions.addAll(model.keySet());
        for (Map.Entry<String, HashSet<String>> activities : model.entrySet()) {
            derivedActions.addAll(activities.getValue());
        }
        for (String action : derivedActions) System.out.println(action);
        Set<String> derivedDF = new HashSet<>();
        for (Map.Entry<String, HashSet<String>> activities : model.entrySet()) {
            for (String activity : activities.getValue()) {
                String df = activities.getKey() + "->" + activity;
                derivedDF.add(df);
            }
        }

        derivedActions.remove("i");
        derivedActions.remove("o");

        this.actions = derivedActions;
        this.directlyFollows = derivedDF;
    }

    private List<String> filterTraces(List<Trace> traces, double filterThreshold) {
        Map<String, Integer> traceOccurrences = new HashMap<>();
        for (Trace trace : traces) {
            // fix this
            traceOccurrences.put("i" + trace + "o", traceOccurrences.getOrDefault("i" + trace + "o", 0) + 1);
        }

        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(traceOccurrences.entrySet());
        sortedList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Calculate the total occurrences
        int totalOccurrences = sortedList.stream().mapToInt(Map.Entry::getValue).sum();

        // Calculate the threshold for the top 80%
        double threshold = filterThreshold * totalOccurrences;

        // Iterate through the sorted list and accumulate strings until reaching the top 80%
        int accumulatedOccurrences = 0;
        List<String> topStrings = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedList) {
            if (accumulatedOccurrences >= threshold) {
                break;
            }
            topStrings.add(entry.getKey());
            accumulatedOccurrences += entry.getValue();
        }
        return topStrings;
    }
}
