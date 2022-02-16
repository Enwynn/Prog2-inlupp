import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;

public class PathFinder extends Application {

    private ListGraph<Node> currentListGraph = new ListGraph<>();

    private NodeManager currentNodeManager;
    private String currentFile;
    private boolean saved = true;

    private final ImageView imageView = new ImageView();
    private final Pane pane = new Pane();

    private final BorderPane borderPane = new BorderPane();
    private final VBox verticalBox = new VBox();
    private final FlowPane menuButtons = new FlowPane();

    private final MenuItem newMap = new MenuItem("New Map");
    private final MenuItem open = new MenuItem("Open");
    private final MenuItem save = new MenuItem("Save");
    private final MenuItem saveImage = new MenuItem("Save Image");
    private final MenuItem exit = new MenuItem("Exit");

    private final Button findPathButton = new Button("Find Path");
    private final Button showConnectionButton = new Button("Show Connection");
    private final Button newPlaceButton = new Button("New Place");
    private final Button newConnectionButton = new Button("New Connection");
    private final Button changeConnectionButton = new Button("Change Connection");

    private final Menu topBarMenu = new Menu("File");

    public PathFinder() { }

    @Override
    public void start(Stage stage) {

        initTopBar();
        Scene scene = new Scene(initButtons());
        stage.setScene(scene);
        stage.setTitle("PathFinder");
        stage.show();

        new FileButtons(this,getTopBar(), stage);
        new MenuButtons(this, scene);
    }

    public BorderPane initButtons() {

        findPathButton.setId("btnFindPath");
        showConnectionButton.setId("btnShowConnection");
        newPlaceButton.setId("btnNewPlace");
        newConnectionButton.setId("btnNewConnection");
        changeConnectionButton.setId("btnChangeConnection");
        buttonsSetDisable(true);

        menuButtons.getChildren().addAll(Arrays.asList(findPathButton, showConnectionButton,
                newPlaceButton, newConnectionButton, changeConnectionButton));

        verticalBox.getChildren().add(menuButtons);
        borderPane.setTop(verticalBox);
        borderPane.setCenter(pane);
        return borderPane;
    }

    public void initTopBar() {
        pane.setId("outputArea");
        initMenuButtons();
        topBarMenu.getItems().add(newMap);
        topBarMenu.getItems().add(open);
        topBarMenu.getItems().add(save);
        topBarMenu.getItems().add(saveImage);
        topBarMenu.getItems().add(exit);
        topBarMenu.setId("menuFile");

        MenuBar menuBar = new MenuBar();
        menuBar.setId("menu");
        menuBar.getMenus().add(topBarMenu);
        verticalBox.getChildren().add(menuBar);
    }

    private void initMenuButtons() {
        newMap.setId("menuNewMap");
        open.setId("menuOpenFile");
        save.setId("menuSaveFile");
        saveImage.setId("menuSaveImage");
        exit.setId("menuExit");
    }

    public void resetImage(String newImage) {
        try {
            pane.getChildren().clear();
            imageView.setImage(new Image(newImage));
            pane.getChildren().add(imageView);
            borderPane.setCenter(pane);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void buttonsSetDisable(boolean bool) {
        save.setDisable(bool);
        saveImage.setDisable(bool);

        findPathButton.setDisable(bool);
        showConnectionButton.setDisable(bool);
        newPlaceButton.setDisable(bool);
        newConnectionButton.setDisable(bool);
        changeConnectionButton.setDisable(bool);
    }

    public void drawGraph() {
        resetImage(currentFile);
        Set<Node> nodesToDraw = currentListGraph.getNodes();
        for (Node n : nodesToDraw) {
            if (!pane.getChildren().contains(n)) {
                pane.getChildren().add(n);
                n.drawNode(currentListGraph);
                pane.getChildren().add(n.getLocationText());
                pane.getChildren().addAll(n.getLines());
            }
        }
        currentNodeManager = new NodeManager(currentListGraph);
    }

    public void reset() {
        currentFile = "europa.gif";
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public Menu getTopBar() {
        return topBarMenu;
    }

    public FlowPane getMenuButtons() {
        return menuButtons;
    }

    public String getCurrentFile() {
        return currentFile;
    }

    public Pane getPane() {
        return pane;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ListGraph getCurrentListGraph() {
        return currentListGraph;
    }

    public NodeManager getCurrentNodeManager() {
        return currentNodeManager;
    }

    public void setCurrentListGraph() {
        currentListGraph = new ListGraph<Node>();
    }

    public void setCurrentNodeManager() {
        currentNodeManager = new NodeManager(currentListGraph);
    }

    public void setCurrentFile(String currentFile) {
        this.currentFile = currentFile;
    }
}