
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.*;

public class NodeManager {

    private int counter;

    private final ArrayList<Node> nodesToConnect = new ArrayList<>();

    public NodeManager(ListGraph<Node> lg) {

        lg.getNodes().forEach(e -> {
            if (e.isMarked()) {
                nodesToConnect.add(e);
                counter++;
            }
        });
        lg.getNodes().forEach(e -> e.setOnMouseClicked((MouseEvent e1 ) -> checkIfMarked(e)));
    }

    private void checkIfMarked(Node e) {
        if (e.isMarked()) {
            e.setColor(Color.BLUE);
            nodesToConnect.remove(e);
            counter--;
        }

        else {
            if (!readyForConnection()) {
                e.setColor(Color.RED);
                nodesToConnect.add(e);
                counter++;
            }
        }
    }

    public boolean readyForConnection() {
        final int maxCounter = 2;
        return counter == maxCounter;
    }

    public List<Node> getNodesToConnect() {
        return Collections.unmodifiableList(nodesToConnect);
    }



}
