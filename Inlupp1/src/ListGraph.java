//ihal3898 9503071996

import java.io.Serializable;
import java.util.*;

public class ListGraph<T> implements Graph<T>, Serializable {

    private Map<Object, LinkedList<Edge<T>>> edges = new Hashtable<>();

    @Override
    public void add(Object node) {
        if (node != null) {
            if (edges.containsKey(node)) {
                return;
            }
        }
        LinkedList<Edge<T>> edgeList = new LinkedList<>();
        edges.put(node, edgeList);
    }

    @Override
    public void connect(T node1, T node2, String name, int weight) {
        throwIfGraphDoesNotContainNodes(node1, node2);
        throwIfWeightIsNegative(weight);
        throwIfThereAreEdgesForTheNodes(node1, node2);
        connectNode(node1, node2, name, weight);
        connectNode(node2, node1, name, weight);
    }

    private void connectNode(T node1, T node2, String name, int weight) {
        LinkedList<Edge<T>> n1Edges = edges.get(node1);
        Edge<T> e1 = new Edge<>(node2, weight, name);
        n1Edges.add(e1);

    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {
        throwIfGraphDoesNotContainNodes(node1, node2);
        throwIfWeightIsNegative(weight);
        throwIfThereAreNoEdgesForTheNodes(node1, node2);
        iterateNodesEdgesToChangeWeight(node1, node2, weight);
        iterateNodesEdgesToChangeWeight(node2, node1, weight);
    }

    private void iterateNodesEdgesToChangeWeight(T node1, T node2, int weight) {
            for (Edge<T> e : edges.get(node1)) {
                if (e.getDestination() == node2)
                    e.setWeight(weight);
            }
    }

    @Override
    public Set getNodes() {
        HashSet<Object> nodes = new HashSet<>();
        for (Map.Entry<Object, LinkedList<Edge<T>>> n : edges.entrySet()) {
            nodes.add(n.getKey());
        }
        return nodes;
    }

    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) {
        throwIfNodeNotInGraph(node);
        return this.edges.get(node);
    }

    @Override
    public Edge<T> getEdgeBetween(T node1, T node2) {
        throwIfGraphDoesNotContainNodes(node1, node2);
        return getEdge(node1, node2);
    }

    private Edge<T> getEdge(T node1, T node2) {
        for (Edge<T> e : edges.get(node1)) {
            if (e.getDestination() == node2) {
                return e;
            }
        }
        return null;
    }

        @Override
        public void disconnect (T node1, T node2){
            throwIfGraphDoesNotContainNodes(node1, node2);
            throwIfThereIsNoEdgeBetweenNodes(node1, node2);
            removeEdgesFromNodes(node1, node2);
            removeEdgesFromNodes(node2, node1);

        }

        private void removeEdgesFromNodes (T node1, T node2){
            edges.get(node1).removeIf(e -> e.getDestination() == node2);
        }

    @Override
    public void remove (T node){
        throwIfNodeNotInGraph(node);
         for (LinkedList<Edge<T>> n : edges.values()) {
            n.removeIf(e -> e.getDestination() == node);
        }
        edges.remove(node);
   
    }

        @Override
        public boolean pathExists (T from, T to){
            if (!edges.containsKey(from) || !edges.containsKey(to))
                return false;

            Set<T> visited = new HashSet<>();
            depthFirstSearch(from, visited);
            return visited.contains(to);
        }

        private void depthFirstSearch (T where, Set < T > visited){
            visited.add(where);
            for (Edge<T> e : edges.get(where))
                if (!visited.contains(e.getDestination()))
                    depthFirstSearch(e.getDestination(), visited);
        }

        // OK
        private void depthFirstSearch (T where, T whereFrom, Set < T > visited, Map < T, T > via ){

            visited.add(where);
            via.put(where, whereFrom);
            for (Edge<T> e : edges.get(where)) {
                if (!visited.contains(e.getDestination())) {
                    depthFirstSearch(e.getDestination(), where, visited, via);
                }
            }
        }

   @Override
        public List<Edge<T>> getPath (T from, T to){
            throwIfGraphDoesNotContainNodes(from, to);
            List<Edge<T>> path = new ArrayList<>();
            Set<T> visited = new HashSet<>();
            Map<T, T> via = new HashMap<>();
            depthFirstSearch(from, null, visited, via);

            if (!visited.contains(to)) {
                return null;
            }
            T where = to;
            while (!where.equals(from)) {
                T node = via.get(where);
                Edge<T> e = getEdgeBetween(node, where);
                path.add(e);
                where = node;
            }
            Collections.reverse(path);
            return path;
        }

        @Override
        public String toString () {
            return "ListGraph{" + "edges=" + edges + '}';
        }

        private void throwIfNodeNotInGraph (Object node){
            if (!edges.containsKey(node))
                throw new NoSuchElementException("Node is not in the graph");
        }

        private void throwIfWeightIsNegative ( int weight){
            if (0 > weight)
                throw new IllegalArgumentException("Weight can't be negative because of \n" +
                        "the algorithm used in this program." + "Weight: " + weight);
        }

        private void throwIfThereIsNoEdgeBetweenNodes (T node1, T node2){
            if (getEdgeBetween(node1, node2) == null) {
                throw new IllegalStateException("No edge between nodes");
            }
        }

        private void throwIfThereAreEdgesForTheNodes (T node1, T node2){

            for (Edge<T> e : edges.get(node1)) {
                if (e.getDestination() == node2)
                    throw new IllegalStateException("The nodes are already connected.");
            }
            for (Edge<T> e : edges.get(node2)) {
                if (e.getDestination() == node1)
                    throw new IllegalStateException("The nodes are already connected.");
            }
        }

        private void throwIfThereAreNoEdgesForTheNodes (T node1, T node2){

            int b = 0;
            for (Edge<T> e : edges.get(node1)) {
                if (e.getDestination() == node2)
                    b++;
            }
            for (Edge<T> e : edges.get(node2)) {
                if (e.getDestination() == node1)
                    b++;
            }
            if (b <= 1) {
                throw new IllegalStateException("The nodes are already connected.");
            }
        }

        private void throwIfGraphDoesNotContainNodes (T node1, T node2){
            if (!edges.containsKey(node1) || !edges.containsKey(node2))
                throw new NoSuchElementException("The nodes are not in the graph.");
        }
    }