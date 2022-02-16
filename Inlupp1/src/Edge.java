
//ihal3898 9503071996
import java.io.Serializable;
import java.util.NoSuchElementException;
public class Edge<T> implements Serializable {

    private T destination;
    private int weight;
    private String name;


    public Edge(T destination, int weight, String name) {

        this.destination = destination;
        this.weight = weight;
        this.name = name;
    }

   public T getDestination() {
        if (destination == null) {
            throw new NoSuchElementException("There is no destination");
        }
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        if (0 > weight) {
            throw new IllegalArgumentException("Weight can't be negative.");
        }
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

   @Override
    public String toString() {
        return "till " + destination + " med " + name + " tar " + weight;
    }
}