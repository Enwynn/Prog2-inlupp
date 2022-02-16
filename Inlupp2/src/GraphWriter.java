

import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GraphWriter {
    private String currentFile;
    private ListGraph listGraph;
    private Map<Node, LinkedList<Edge>> graph = new HashMap<>();

    public GraphWriter(String currentFile, ListGraph listGraph) {
        this.currentFile = currentFile;
        this.listGraph = listGraph;
    }

    public void exportGraph() {
        graph = listGraph.getEdges();

        try {
            FileWriter fileWriter = new FileWriter("europa.graph");
            fileWriter.write("file:" + currentFile + "\n");
            String nodesWithCoordinates = "";
            for (Node n: graph.keySet()) {
                nodesWithCoordinates += n.toStringSave();

            }
            if (!graph.isEmpty()) {
                nodesWithCoordinates = nodesWithCoordinates.substring(0, nodesWithCoordinates.length() - 1);
            }
            fileWriter.write(nodesWithCoordinates);
            fileWriter.write( "\n");

            for (Map.Entry<Node, LinkedList<Edge>> entry : graph.entrySet()) {
                 String keyNode = entry.getKey().getName();
                for (Edge o: entry.getValue()) {
                    fileWriter.write(o.saveString(keyNode) + "\n");
                }
            }
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
