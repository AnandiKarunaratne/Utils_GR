package org.utils;

import org.jbpt.graph.DirectedGraph;
import org.jbpt.petri.NetSystem;
import org.jbpt.petri.io.PNMLSerializer;
import org.jbpt.throwable.SerializationException;
import org.jbpt.utils.IOUtils;
import org.utils.discovery.ProcessDiscovery;
import org.utils.log.EventLog;
import org.utils.log.Trace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelGenerator {
    public void generate(String fName, String modelFileName) throws SerializationException {

        EventLog log = new EventLog().readXES(fName);

        EventLog filteredLog = filterLog(log, 1);
        // Get directed graph
        DirectedGraph G = new ProcessDiscovery().discoverDG(filteredLog);


        NetSystem M = new ProcessDiscovery().graphToNetSystem(G);

        String mString = PNMLSerializer.serializePetriNet(M);
        IOUtils.toFile(modelFileName, mString);

    }

    private EventLog filterLog(EventLog log, double filter) {
        Map<Trace, Integer> traceOccurrences = new HashMap<>();

        for (Trace trace : log) {
            traceOccurrences.put(trace, traceOccurrences.getOrDefault(trace, 0) + 1);
        }

        List<Map.Entry<Trace, Integer>> sortedList = new ArrayList<>(traceOccurrences.entrySet());
        sortedList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        int totalOccurrences = sortedList.stream().mapToInt(Map.Entry::getValue).sum();
        double threshold = filter * totalOccurrences;

        int accumulatedOccurrences = 0;
        List<Trace> topTraces = new ArrayList<>();
        for (Map.Entry<Trace, Integer> entry : sortedList) {
            if (accumulatedOccurrences >= threshold) {
                break;
            }
            topTraces.add(entry.getKey());
            accumulatedOccurrences += entry.getValue();
        }
        return new EventLog(topTraces);
    }
}
