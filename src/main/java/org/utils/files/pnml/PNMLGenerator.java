package org.utils.files.pnml;

import org.utils.models.PetriNet;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PNMLGenerator {

    public void generatePNML(PetriNet petriNet, String filePath) {
        File file = new File(filePath);
        OutputStream outputStream = null;
        String start = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<pnml xmlns=\"http://www.pnml.org/version-2009/grammar/pnml\">\n" +
                "<net id=\"2def6871-e972-4e80-8ede-0b821caf895f\" type=\"http://www.pnml.org/version-2009/grammar/ptnet\">\n" +
                "<name><text>model</text></name>\n" +
                "<page id=\"page0\">\n";

        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(start.getBytes());

            // states
            for (String state : petriNet.getStates()) {
                String stateLine;
                if (state.equals("p0")) {
                    stateLine = "<place id=\"p0\"><name><text>p0</text></name><initialMarking><text>1</text></initialMarking></place>\n";
                } else {
                    stateLine = "<place id=\"" + state + "\"><name><text>" + state + "</text></name></place>\n";
                }
                outputStream.write(stateLine.getBytes());
            }
            outputStream.write("<place id=\"sink\"><name><text>sink</text></name></place>\n".getBytes());

            // transitions
            for (String transition : petriNet.getTransitions()) {
                String transitionLine = "<transition id=\"" + transition + "\"><name><text>" + transition.substring(0,1) + "</text></name></transition>\n";
                outputStream.write(transitionLine.getBytes());
            }

            // flows
            int i = 1;
            for (Map.Entry<String, List<String>> flow : petriNet.getFlows().entrySet()) {
                String source = flow.getKey();
                for (String target : flow.getValue()) {
                    String arcLine = "<arc id=\"arc" + i++ +"\" source=\"" + source + "\" target=\"" + target + "\"/>\n";
                    outputStream.write(arcLine.getBytes());
                }
            }

            String end = "</page>\n</net>\n</pnml>";
            outputStream.write(end.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
