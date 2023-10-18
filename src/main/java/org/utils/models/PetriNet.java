package org.utils.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PetriNet {

    Set<String> states;
    Set<String> transitions;
    Map<String, List<String>> flows = new HashMap<>();
    Set<String> finalActivities;

    public PetriNet(Set<String> states, Set<String> transitions, Map<String, List<String>> flows, Set<String> finalActivities) {
        this.states = states;
        this.transitions = transitions;
        this.flows = flows;
        this.finalActivities = finalActivities;
    }

    public Set<String> getStates() {
        return states;
    }

    public Set<String> getTransitions() {
        return transitions;
    }

    public Map<String, List<String>> getFlows() {
        return flows;
    }

    public Set<String> getFinalActivities() {
        return finalActivities;
    }
}
