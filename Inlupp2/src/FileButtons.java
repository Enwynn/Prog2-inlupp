
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class FileButtons {
   private final Stage stage;
   private final PathFinder pathFinder;

    public FileButtons(PathFinder pathFinder, Menu menu, Stage primarystage) {
        this.stage = primarystage;
        this.pathFinder = pathFinder;

        menu.getItems().get(0).setOnAction(actionEvent -> newMap());
        menu.getItems().get(1).setOnAction(actionEvent -> open());
        menu.getItems().get(2).setOnAction(actionEvent -> save());
        menu.getItems().get(3).setOnAction(actionEvent -> saveImage());
        menu.getItems().get(4).setOnAction(actionEvent -> exitHandler());
        primarystage.setOnCloseRequest(this::exitHandlerWindow);

    }

    private void newMap() {
        pathFinder.buttonsSetDisable(false);
        if (!openHandler()) return;
        pathFinder.setCurrentListGraph();
        pathFinder.setCurrentNodeManager();
        pathFinder.reset();
        pathFinder.resetImage(pathFinder.getCurrentFile());
        pathFinder.setSaved(true);
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    private void open() {
        pathFinder.buttonsSetDisable(false);
        if (!openHandler()) return;
        pathFinder.setCurrentListGraph();
        try {
            GraphReader gr = new GraphReader();
            gr.readGraph("europa.graph", pathFinder.getCurrentListGraph());
            pathFinder.setCurrentFile(gr.getFilename());
            pathFinder.resetImage(gr.getFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        pathFinder.drawGraph();
        pathFinder.setSaved(true);
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    private void save() {
        GraphWriter gw = new GraphWriter(pathFinder.getCurrentFile(), pathFinder.getCurrentListGraph());
        gw.exportGraph();
        pathFinder.setSaved(true);
    }

    private void saveImage() {
        try{
            WritableImage image = pathFinder.getPane().snapshot(null, null);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(bufferedImage, "png", new File("capture.png"));
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    private boolean openHandler() {
        if (!pathFinder.isSaved()) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Unsaved changes, continue anyway?");
            dialog.setTitle("Warning!");
            dialog.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonBar.ButtonData.OK_DONE));
            dialog.getDialogPane().getButtonTypes().add(new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE));
            Optional<ButtonType> result = dialog.showAndWait();
            return result.isPresent() && result.get().getText().equals("OK");
        }
        else return true;
    }

    private void exitHandler() {
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private void exitHandlerWindow(WindowEvent event) {
        if (!pathFinder.isSaved()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Unsaved changes, exit anyway?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.CANCEL)
                event.consume();
        }
    }
}
