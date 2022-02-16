
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class DialogPath extends Dialog {
    private final TextArea area = new TextArea();
    private final ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

    public DialogPath( Node n1, Node n2) {
        super();
        newDialog(n1, n2);
    }

    public void newDialog(Node n1, Node n2) {

        super.setTitle("Connection");
        super.setHeaderText("The Path from " + n1.getName() + " to " + n2.getName() + ":");

        VBox verticalBox = new VBox();
        verticalBox.getChildren().add(area);

        super.getDialogPane().setContent(verticalBox);
        super.getDialogPane().getButtonTypes().add(buttonTypeOk);
    }

    public boolean setTextArea(ListGraph<Node> lg, Node n1, Node n2) {
        List<Edge<Node>> ep = lg.getPath(n1, n2);

        if (ep != null) {
            String path = "";
            int total = 0;
            for (Object e: lg.getPath(n1,n2)) {
               path += e.toString();
               total += ((Edge) e).getWeight();
            }
            path += "Total " + total;
            area.setText(path);
            return true;
        } else return false;
    }

    public ButtonType getButtonTypeOk() {
        return buttonTypeOk;
    }
}