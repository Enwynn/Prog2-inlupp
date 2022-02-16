
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Node extends Circle {
    private final String name;
    private final Text locationText = new Text();
    private final double x;
    private final double y;
    private ArrayList<Line> lines = new ArrayList<>();

    public Node(String name, double x, double y) {
        super(0,0,10);
        this.name = name;
        this.x = x;
        this.y = y;
        setFill(Color.BLUE);
        setStroke(Color.BLACK);
        locationText.setText(name);
    }

    public boolean isMarked() {
        return this.getFill().equals(Color.RED);
    }

    public void setColor(Paint p) {
        setFill(p);
    }

    public void drawNode (ListGraph<Node> listGraph) {
        for (Edge<Node> i: listGraph.getEdgesFrom(this)) {
            Line line = new Line(this.x, this.y, (i.getDestination()).getX(), (i.getDestination()).getY());
            line.setDisable(true);
            lines.add(line);
        }
        setLayoutX(x);
        setLayoutY(y);
        locationText.setX(x + 12);
        locationText.setY(y + 12);
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public Text getLocationText() {

        return locationText;
    }

    public String getName() {
        return name;
    }

    public String toStringSave() {
        return name + ";" + x  + ";" + y + ";";
    }

    @Override
    public String toString() {
        return "[ Name: " + name + " X: " + x + " Y: " + y + ";" + "\n";
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}