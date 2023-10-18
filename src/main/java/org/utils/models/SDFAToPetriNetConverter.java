package org.utils.models;

import org.jbpt.petri.NetSystem;
import org.jbpt.petri.Place;
import org.jbpt.petri.Transition;
import org.jbpt.petri.io.PNMLSerializer;
import org.jbpt.throwable.SerializationException;
import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.*;

public class SDFAToPetriNetConverter {

    String initialState;
    Set<String> states;
    Set<String> transitions;
    Set<String> finalActivities;
    Map<String, List<String>> flows = new HashMap<>();

    public SDFAToPetriNetConverter(SDFA sdfa) {
        this.initialState = sdfa.getInitialState();
        this.states = sdfa.getStates();
        this.transitions = sdfa.getAlphabet();
        this.finalActivities = sdfa.getFinalActivities();
        updateFlows(sdfa.getTransitions());
        print(this.flows);
    }

    private void updateFlows(Map<String, List<String>> sdfaFlows) {
        for (Map.Entry<String, List<String>> sdfaFlow : sdfaFlows.entrySet()) {
            String source = null;
            String target = null;
            String[] parts = sdfaFlow.getKey().split("->");
            if (parts.length == 2) {
                source = parts[0];
                target = parts[1];
            }
            for (String activity : sdfaFlow.getValue()) {
                if (!flows.containsKey(source)) {
                    flows.put(source, new ArrayList<>());
                }
                flows.get(source).add(activity);
                if (!flows.containsKey(activity)) {
                    flows.put(activity, new ArrayList<>());
                }
                flows.get(activity).add(target);
            }
        }

    }

    public PetriNet convertToPetriNet() {
        return new PetriNet(states, transitions, flows, finalActivities);
    }

    public void generatePNML() {
        NetSystem net = new NetSystem();

        // set states
        Map<String, Place> netStates = new HashMap<>();
        for (String state : states) {
            netStates.put(state, new Place(state));
        }

        // set transitions
        Map<String, Transition> netTransitions = new HashMap<>();
        for (String transition : transitions) {
            netTransitions.put(transition, new Transition(transition, transition.substring(0,1)));
        }

        // set flows
        for (Map.Entry<String, List<String>> flow : flows.entrySet()) {
            String key = flow.getKey();
            if (netStates.containsKey(key)) {
                Place source = netStates.get(key);
                for (String targetKey : flow.getValue()) {
                    Transition target = netTransitions.get(targetKey);
                    net.addFlow(source, target);
                }
            } else if (netTransitions.containsKey(key)) {
                Transition souce = netTransitions.get(key);
                for (String targetKey : flow.getValue()) {
                    Place target = netStates.get(targetKey);
                    net.addFlow(souce, target);
                }
            }
        }

        Document doc = null;
        try {
            doc = PNMLSerializer.serialize(net);
        } catch (SerializationException e1) {
            e1.printStackTrace();
        }

        DOMSource domSource = new DOMSource(doc);

        StreamResult streamResult = new StreamResult(System.out);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer;
        try {
            serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.INDENT,"yes");
            serializer.transform(domSource, streamResult);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private void print(Map<String, List<String>> flows) {
        for (Map.Entry<String, List<String>> entry : flows.entrySet()) {
            String key = entry.getKey();
            // String value = entry.getValue();
            System.out.print("Key: " + key + ", Value: ");
            for (String x : entry.getValue()) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }


}
