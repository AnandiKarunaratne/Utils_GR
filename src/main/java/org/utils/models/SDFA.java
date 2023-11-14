package org.utils.models;

import java.util.*;

public class SDFA {
    Set<String> states;
    Set<String> alphabet;
    Map<String, List<String>> transitions;
    String initialState;
    Set<String> finalActivities;

    public SDFA(Set<String> states, Set<String> alphabet, Map<String, List<String>> transitions, String initialState, Set<String> finalActivities) {
        this.states = states;
        this.alphabet = alphabet;
        this.initialState = initialState;
        this.transitions = transitions;
        this.finalActivities = finalActivities;
    }

    public Set<String> getStates() {
        return states;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }
    public Map<String, List<String>> getTransitions(){
        return transitions;
    }
    public String getInitialState() {
        return initialState;
    }

    public Set<String> getFinalActivities() {
        return finalActivities;
    }
}
