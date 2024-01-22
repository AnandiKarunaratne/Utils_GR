package org.utils;

import org.jbpt.graph.DirectedGraph;
import org.jbpt.petri.NetSystem;
import org.jbpt.petri.io.PNMLSerializer;
import org.jbpt.throwable.SerializationException;
import org.jbpt.utils.IOUtils;
import org.utils.discovery.ProcessDiscovery;
import org.utils.log.EventLog;

public class Test {
    public void xxx(String fName, String modelFileName) throws SerializationException {

         EventLog log = new EventLog().readXES(fName);

        // Get directed graph
        DirectedGraph G = new ProcessDiscovery().discoverDG(log);



        NetSystem M = new ProcessDiscovery().graphToNetSystem(G);

        String mString = PNMLSerializer.serializePetriNet(M);
        IOUtils.toFile(modelFileName, mString);


    }
}
