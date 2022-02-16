
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class DialogWindow extends Dialog {
    private final TextField nameField = new TextField();
    private final TextField timeField = new TextField();
    private final Text nameText = new Text("Name:");
    private final Text timeText = new Text("Time:");
    private final ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    private final ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    public DialogWindow(Boolean textEdit, Node n1, Node n2) {
        super();
        newDialog(n1, n2);
    }

    public void newDialog(Node n1, Node n2) {

        super.setTitle("Connection");
        super.setHeaderText("Connection from " + n1.getName() + " to " + n2.getName());

        GridPane grid = new GridPane();
        grid.add(nameText, 1, 1);
        grid.add(nameField, 2, 1);
        grid.add(timeText, 1, 2);
        grid.add(timeField, 2, 2);

        super.getDialogPane().setContent(grid);
        super.getDialogPane().getButtonTypes().add(buttonTypeOk);
        super.getDialogPane().getButtonTypes().add(buttonTypeCancel);
    }

    public boolean setTextFields(ListGraph lg, Node n1, Node n2) {
        if (lg.alreadyConnected(n1, n2)) {
            setNameField(lg, n1, n2);
            setTimeField(lg, n1, n2);
            return true;
        } else return false;
    }

    public boolean setTimeField(ListGraph lg, Node n1, Node n2) {
        if (lg.alreadyConnected(n1, n2)) {
            timeField.setText(Integer.toString(lg.getEdgeBetween(n1, n2).getWeight()));
            return true;
        } else return false;
    }

  public boolean setNameField(ListGraph lg, Node n1, Node n2) {
        if (lg.alreadyConnected(n1, n2)) {
            nameField.setText(lg.getEdgeBetween(n1, n2).getName());
            return true;
        } else return false;
    }

    public void makeUneditable() {
        makeTimeFieldUneditable();
        makeNameFieldUneditable();
    }

    public void makeTimeFieldUneditable() {
        timeField.setEditable(false);
        timeField.setMouseTransparent(true);
        timeField.setFocusTraversable(false);
    }

    public void makeNameFieldUneditable() {
        nameField.setEditable(false);
        nameField.setMouseTransparent(true);
        nameField.setFocusTraversable(false);
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getTimeField() {
        return timeField;
    }

    public ButtonType getButtonTypeOk() {
        return buttonTypeOk;
    }

    public ButtonType getButtonTypeCancel() {
        return buttonTypeCancel;
    }
}
