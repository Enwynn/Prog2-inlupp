
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.util.Optional;

public class MenuButtons {
    private final Scene scene;
    private final PathFinder pathFinder;

    private Node nodeOne;
    private Node nodeTwo;

    public MenuButtons(PathFinder pathFinder, Scene scene) {
        this.pathFinder = pathFinder;
        final FlowPane fp = pathFinder.getMenuButtons();
        this.scene = scene;

        fp.getChildren().get(2).setOnMousePressed(mouseEvent -> newPlace((Button) fp.getChildren().get(2), mouseEvent));
        fp.getChildren().get(3).setOnMousePressed(mouseEvent -> newConnection());
        fp.getChildren().get(1).setOnMousePressed(mouseEvent -> showConnection());
        fp.getChildren().get(4).setOnMousePressed(mouseEvent -> changeConnection());
        fp.getChildren().get(0).setOnMousePressed(mouseEvent -> findPath());
    }

    private void findPath() {
        if (checkIfTwoNodesSelected()) return;
        getCurrentNodes();
        DialogPath dialog = new DialogPath(nodeOne, nodeTwo);

        if (!dialog.setTextArea(pathFinder.getCurrentListGraph(), nodeOne, nodeTwo))
            alertForNewConnection("No path between the selected nodes");
        else
            dialog.showAndWait();
    }

    private void getCurrentNodes() {
        nodeOne = pathFinder.getCurrentNodeManager().getNodesToConnect().get(0);
        nodeTwo = pathFinder.getCurrentNodeManager().getNodesToConnect().get(1);
    }

    private void changeConnection() {
        DialogWindow dialogWindow = getDialogWindow();
        if (dialogWindow == null) return;
        dialogWindow.makeNameFieldUneditable();
        if (!dialogWindow.setNameField(pathFinder.getCurrentListGraph(), nodeOne, nodeTwo))
            alertForNewConnection("No connection between the selected nodes");
        else
            dialogWindow.showAndWait().ifPresent(response -> {
                if (response.equals(dialogWindow.getButtonTypeOk())) {
                    Optional<String> result2 = Optional.ofNullable(dialogWindow.getTimeField().getText());
                    if (result2.get().matches("^[a-zA-Z]*$")) {
                        alertForNewConnection("Time can only contain numerals");
                    } else
                        pathFinder.getCurrentListGraph().setConnectionWeight(nodeOne, nodeTwo, Integer.parseInt(result2.get()));
                        pathFinder.setSaved(false);
                }
            });
    }

    private DialogWindow getDialogWindow() {
        if (checkIfTwoNodesSelected()) return null;
        getCurrentNodes();
        return new DialogWindow(true, nodeOne, nodeTwo);
    }

    private void showConnection() {
        DialogWindow dialogWindow = getDialogWindow();

        if (dialogWindow == null) return;
        dialogWindow.makeUneditable();
        if (!dialogWindow.setTextFields(pathFinder.getCurrentListGraph(), nodeOne, nodeTwo))
            alertForNewConnection("No connection between the selected nodes");
        else
            dialogWindow.showAndWait();
    }

    private void newConnection() {
        if (checkIfTwoNodesSelected()) return;
        getCurrentNodes();
        if (!pathFinder.getCurrentListGraph().alreadyConnected(nodeOne, nodeTwo)) {
            dialogNewConnection(nodeOne, nodeTwo);
        } else alertForNewConnection("Already connected");
    }

    private boolean checkIfTwoNodesSelected() {
        if (!pathFinder.getCurrentNodeManager().readyForConnection()) {
            alertForNewConnection("Two places must be selected");
            return true;
        }
        return false;
    }

    private void alertForNewConnection(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
        alert.showAndWait();
    }

    private void dialogNewConnection(Node n1, Node n2) {
        DialogWindow dialogWindow = new DialogWindow(true, n1, n2);
        dialogWindow.showAndWait().ifPresent(response -> {
            if (response.equals(dialogWindow.getButtonTypeOk())) {
                Optional<String> result1 = Optional.ofNullable(dialogWindow.getNameField().getText());
                Optional<String> result2 = Optional.ofNullable(dialogWindow.getTimeField().getText());
                if (checkNewConnection(result1, result2)) {
                    pathFinder.getCurrentListGraph().connect(n1, n2, result1.get(), Integer.parseInt(result2.get()));
                    pathFinder.drawGraph();
                    pathFinder.setSaved(false);
                }
            }
        });
    }

    private boolean checkNewConnection(Optional<String> result1, Optional<String> result2) {
        if (result1.isPresent() && !result1.get().isEmpty() && result2.isPresent() && !result2.get().isEmpty()) {
            if (result2.get().matches("^[a-zA-Z]*$")) {
                alertForNewConnection("Time can only contain numerals");
                return false;
            } else return true;
        } else
            alertForNewConnection("Please check your inputs in the Name and Time fields");
        return false;
    }


    private void newPlace(Button button, MouseEvent event) {
        scene.setCursor(Cursor.CROSSHAIR);
        button.setDisable(true);
        ImageView image = pathFinder.getImageView();
        image.setPickOnBounds(false);
        image.setDisable(false);
        image.setOnMouseClicked((MouseEvent e) -> {
            double x = e.getX();
            double y = e.getY();
            scene.setCursor(Cursor.DEFAULT);
            button.setDisable(false);
            dialogNewPlace(x, y);
            image.setDisable(true);
        });
    }

    private void dialogNewPlace(double x, double y) {

        Dialog<String> dialog = new TextInputDialog();
        dialog.setTitle("Name");
        dialog.setContentText("Name of place:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent() && !result.get().isEmpty()){
            pathFinder.getCurrentListGraph().add(new Node(result.get(), x, y));
            pathFinder.drawGraph();
            pathFinder.setSaved(false);
        }
        else if (result.isPresent() && result.get().isEmpty()) {
            alertForNewConnection("Can't create node without a name.");
        }
    }
}
