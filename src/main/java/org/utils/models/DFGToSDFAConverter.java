package org.utils.models;

import java.util.*;

public class DFGToSDFAConverter {

    private Map<String, List<String>> sdfaTransitions = new HashMap<>();
    private Set<String> alphabet = new HashSet<>();
    private Set<String> updatedAlphabet = new HashSet<>();
    private Set<String> states = new HashSet<>();
    private Set<String> usedStates = new HashSet<>();
    private Set<String> updatedStates = new HashSet<>();
    private Set<String> finalActivities = new HashSet<>();
    public SDFA convertToSDFA(DFG dfg) {
        // Define SDFA components
        this.states = new HashSet<>(dfg.actions);
        this.states.add("i");
        this.updatedStates.add("p0");
        this.alphabet = new HashSet<>(dfg.actions);
        this.updatedAlphabet = new HashSet<>(dfg.actions);
        Map<String, List<String>> transitions = new HashMap<>();

        // Create transitions based on the DFG's directlyFollows relation
        for (String relation : dfg.directlyFollows) {
            String[] parts = relation.split("->");
            if (parts.length == 2) {
                if (!transitions.containsKey(parts[0])) {
                    transitions.put(parts[0], new ArrayList<>());
                }
                transitions.get(parts[0]).add(parts[1]);
            }
        }

        setTransitions(transitions);

        // Create and return the SDFA
        return new SDFA(updatedStates, this.updatedAlphabet, this.sdfaTransitions, "p0", finalActivities);
    }

    private void setTransitions(Map<String, List<String>> transitions) {
        for (Map.Entry<String, List<String>> transition : transitions.entrySet()) {
            String source = transition.getKey();
            for (String target : transition.getValue()) {
                if (!target.equals("o")) {
                    String key = changeStateName(source, target);
                    String value = updateActivityName(target);
                    if (!this.sdfaTransitions.containsKey(key)) {
                        this.sdfaTransitions.put(key, new ArrayList<>());
                    }
                    this.sdfaTransitions.get(key).add(value);
                } else {
                    finalActivities.add(source);
                }
            }
        }

        for (String finalActivity : finalActivities) {
            Set<String> finalActivityStates = new HashSet<>();
            for (Map.Entry<String, List<String>> transition : transitions.entrySet()) {
                if (transition.getValue().contains(finalActivity)) {
                    finalActivityStates.add(transition.getKey());
                    //this.sdfaTransitions.put(changeStateName(transition.getKey(), "o"), updateActivityName(finalActivity));
                }
            }
            for (String finalActivityState : finalActivityStates) {
                String key = changeStateName(finalActivityState, "o");
                String value = updateActivityName(finalActivity);
                if (!this.sdfaTransitions.containsKey(key)) {
                    this.sdfaTransitions.put(key, new ArrayList<>());
                }
                this.sdfaTransitions.get(key).add(value);
            }
        }
    }

    private String changeStateName(String source, String target) {
        List<String> list = new ArrayList<>(this.alphabet);

        String sourceState = null;
        String targetState = null;

        if (source.equals("i")) sourceState = "p0";
        else {
            for (String letter : list) {
                if (source.equals(letter)) {
                    sourceState = "p" + String.valueOf(list.indexOf(letter) + 1);
                    updatedStates.add(sourceState);
                }
            }
        }

        if (target.equals("i")) targetState = "p0";
        else if (target.equals("o")) targetState = "sink";
        else {
            for (String letter : list) {
                if (target.equals(letter)) {
                    targetState = "p" + String.valueOf(list.indexOf(letter) + 1);
                    updatedStates.add(targetState);
                }
            }
        }

        return sourceState + "->" + targetState;
    }

    private String updateActivityName(String activity) {
        if (usedStates.contains(activity)) {
            // Generate a random number
            Random random = new Random();
            int randomNumber;
            String newString;

            do {
                randomNumber = random.nextInt(100); // Adjust the range as needed
                newString = activity + randomNumber;
            } while (usedStates.contains(newString));
            updatedAlphabet.add(newString);
            usedStates.add(newString);
            return newString;
        }
        usedStates.add(activity);
        return activity;
    }

//    public static void main(String[] args) {
//        // Create an example FDAG
//        Set<String> actions = new HashSet<>(Arrays.asList("A", "B", "C"));
//        Set<String> directlyFollows = new HashSet<>(Arrays.asList("A->B", "B->C"));
//        Map<String, Integer> actionFrequencies = new HashMap<>();
//        Map<String, Integer> arcFrequencies = new HashMap<>();
//        FDAG fdag = new FDAG(actions, directlyFollows, actionFrequencies, arcFrequencies);
//
//        // Convert the FDAG to SDFA
//        SDFA sdfa = convertToSDFA(fdag);
//
//        // Print or use the SDFA
//        System.out.println("Converted SDFA: " + sdfa);
//    }
}
