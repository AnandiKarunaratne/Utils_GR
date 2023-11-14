package org.utils.files.xes;

import org.deckfour.xes.in.XParser;
import org.deckfour.xes.in.XesXmlParser;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class XESReader {

    public List<String> readXES(String filePath) {
        List<String> traces = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            XParser parser = new XesXmlParser();
            List<XLog> logs = parser.parse(fileInputStream);

            if (!logs.isEmpty()) {
                XLog log = logs.get(0); // Assuming there's only one log in the XES file

                for (XTrace xTrace : log) {
                    StringBuilder trace = new StringBuilder();
                    for (org.deckfour.xes.model.XEvent xEvent : xTrace) {
                        String activityName = xEvent.getAttributes().get("concept:name").toString();
                        trace.append(activityName);
                    }
                    traces.add(trace.toString());
                }
            } else {
                System.out.println("No logs found in the XES file.");
            }
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return traces;
    }

}
