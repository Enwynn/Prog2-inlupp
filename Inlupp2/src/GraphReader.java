
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class GraphReader {

    private String filename;

    public HashMap<String, Node> readGraph (String fileName, ListGraph<Node> listGraph) throws IOException {

        final int IS_NODE = 4;
        final int IS_COORDINATE = 3;

        Node currentTempNode = null;
        Node currentDestNode = null;

        HashMap<String, Node> nodes = new HashMap();

        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String tempLine;
            currentFileName(in);
            while ((tempLine = in.readLine()) != null) {
                String[] currentLine = tempLine.split(";");
                if (tempLine.contains(";") && currentLine.length == IS_NODE) {
                    for (int i = 0; i < 4 ; i += 2) {
                        if (i >= 2) {
                            if (!listGraph.alreadyConnected(currentTempNode, currentDestNode)) {
                                listGraph.connect(currentTempNode, currentDestNode, currentLine[i], Integer.parseInt(currentLine[i + 1]));
                            }
                        }
                        else if (!listGraph.getNodes().contains(currentLine[i])) {
                            currentTempNode = nodes.get(currentLine[i]);
                            currentDestNode = nodes.get(currentLine[i + 1]);
                        }
                    }
                }
                else if (tempLine.contains(";") && currentLine.length != IS_NODE) {
                    for (int i = 0; i < currentLine.length; i += IS_COORDINATE) {
                        Node currentNode = new Node(currentLine[i], Double.parseDouble(currentLine[i + 1]), Double.parseDouble(currentLine[i + 2]));
                        listGraph.add(currentNode);
                        nodes.put(currentNode.getName(),currentNode);
                    }
            }
        }
    }
        return nodes;
}

      private void currentFileName(BufferedReader in) throws IOException {
        String filename = in.readLine();
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
