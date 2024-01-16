package de.pp.datastructures.graph;

import java.util.*;


public class Graph {

    Set<Vertex> vertices = new HashSet<>();


    /**
     * Returns all Vertices that are connected with the given Vertex.
     * It returns then to if the Vertices are connected though other Vertices,
     * but one of them need to be connected to the given Vertex.
     *
     * @param v The Vertex you want all connected Vertices.
     * @return All Connected Vertices.
     */
    public Set<Vertex> getConnectedVertices(Vertex v) {
        return getConnectedVertices(v, new HashSet<>());
    }
    public void addVertex(Vertex ... a){
        vertices.addAll(List.of(a));
    }

    /**
     * Funktioniert wiue folgt
     * alle Vertexe Nehmen und den kürzsesten Pfad entlange
     *
     * @param start
     * @param end
     * @return
     */
    public List<Vertex> dijkstraAlgorithmus(Vertex start, Vertex end) {
        start.setDistance(new Distance(0));
        var currentVertex = start.getLightestEdge().getVertexTwo();
        var otherEdges = new ArrayList<>(start.getEdges().stream().filter(edge -> !edge.equals(start.getLightestEdge())).toList());
        currentVertex.setAncestor(start);
        currentVertex.setDistance(new Distance(start.getLightestEdge().getWeight()));
        do {
            try {
                currentVertex = updateDijkstra(currentVertex, otherEdges);
            } catch (NoSuchElementException e) {
                break;
            }
        } while (!currentVertex.equals(end));
        var result = new ArrayList<Vertex>();
        while (!currentVertex.equals(start)) {
            result.add(currentVertex);
            currentVertex = currentVertex.getAncestor();
        }
        result.add(start);
        return result;
    }

    public void startToEnd(Vertex start, Vertex end) {
        var result = dijkstraAlgorithmus2(start);
        var pointer = end;
//        result.getPredecessor().entrySet().forEach(System.out::println);
//        result.getDistance().entrySet().forEach(System.out::println);
        while (pointer != null) {
            System.out.print(pointer);
            pointer = result.getPredecessor().get(pointer);
        }
    }

    public Result dijkstraAlgorithmus2(Vertex start) {
        var distance = new HashMap<Vertex, Integer>();
        distance.put(start, 0);
        var predecessor = new HashMap<Vertex, Vertex>();
        predecessor.put(start, null);
        ArrayList<Vertex> result = new ArrayList<>();
        ArrayList<Edge> otherEdges = new ArrayList<>();
        var currentVertex = start;

        while (vertices.containsAll(result)) {
            currentVertex = updateDijkstra2(currentVertex, otherEdges, distance, predecessor);
            if (currentVertex == null) {
                break;
            }
            result.add(currentVertex);
        }
        Result r1 = new Result();
        r1.setDistance(distance);
        r1.setPredecessor(predecessor);
        return r1;
    }

    private Vertex updateDijkstra2(Vertex vertex, ArrayList<Edge> otherEdges, HashMap<Vertex, Integer> distance, HashMap<Vertex, Vertex> predecessor) {
        for (Edge edge : vertex.getEdges()) {
            var otherSide = edge.getVertexTwo();
            if (otherSide.equals(vertex)) {
                otherSide = edge.getVertexOne();
            }
            if (otherSide.equals(predecessor.get(vertex))) {
                continue;//If its the predecessor skip
            }
            if (distance.containsKey(otherSide)) {
                if (distance.get(vertex) + edge.getWeight() >= distance.get(otherSide)) {
                    continue;//If it has already a connection and its shorter than the future skip
                } else {
                    distance.remove(otherSide); //If it has already a connection and its longer than the future delete the old path
                    predecessor.remove(otherSide);
                }
            }
            otherEdges.add(edge);
        }
        Edge shortestEdge = null;
        if (otherEdges.size() == 0) {
            return null;
        }
        for (Edge otherEdge : otherEdges) {
            Vertex currentVertex;
            if (distance.containsKey(otherEdge.getVertexOne())) {
                currentVertex = otherEdge.getVertexOne();
            } else {
                currentVertex = otherEdge.getVertexTwo();
            }
            if (shortestEdge == null) {
                shortestEdge = otherEdge;
                continue;
            }
            Vertex shortestEdgeVertex;
            if (distance.containsKey(shortestEdge.getVertexOne())) {
                shortestEdgeVertex = shortestEdge.getVertexOne();
            } else {
                shortestEdgeVertex = shortestEdge.getVertexTwo();
            }
            if (distance.get(currentVertex) + otherEdge.getWeight() < distance.get(shortestEdgeVertex) + shortestEdge.getWeight()) {
                shortestEdge = otherEdge;
            }
        }
        otherEdges.remove(shortestEdge);
        if (!distance.containsKey(shortestEdge.getVertexOne())) {
            distance.put(shortestEdge.getVertexOne(), distance.get(shortestEdge.getVertexTwo()) + shortestEdge.getWeight());
            predecessor.put(shortestEdge.getVertexOne(), shortestEdge.getVertexTwo());
            return updateDijkstra2(shortestEdge.getVertexOne(), otherEdges, distance, predecessor);
        } else {
            distance.put(shortestEdge.getVertexTwo(), distance.get(shortestEdge.getVertexOne()) + shortestEdge.getWeight());
            predecessor.put(shortestEdge.getVertexTwo(), shortestEdge.getVertexOne());
            return updateDijkstra2(shortestEdge.getVertexTwo(), otherEdges, distance, predecessor);
        }
    }

    /**
     * The Update of the Dijkstra Algorithmus. It checks every edge that the given Vertex has except for the one that
     * is directed at him. And checks also all edges given in other edges and takes the lightest.
     *
     * @param vertex
     * @param otherEdges
     * @return
     */
    private Vertex updateDijkstra(Vertex vertex, ArrayList<Edge> otherEdges) {
        Edge shortestEdge = null;
        if (otherEdges.size() == 0 && vertex.getEdges().size() == 0) {
            throw new NoSuchElementException("Nothing to Update");
        }
        for (Edge edge : vertex.getEdges()) {
            if (!edge.getVertexOne().equals(vertex.getAncestor())) {
                shortestEdge = edge;
            }
        }
        if (shortestEdge == null) {
            shortestEdge = otherEdges.get(0);
        }
        if (otherEdges.size() != 0) {
            for (Edge edge : otherEdges) {
                if (edge.getWeight() + edge.getVertexOne().getDistance().getValue() < shortestEdge.getWeight() + shortestEdge.getVertexOne().getDistance().getValue()) {
                    if (edge.getVertexTwo().getDistance() == null || edge.getVertexTwo().getDistance().getValue() > edge.getWeight() + edge.getVertexOne().getDistance().getValue()) {
                        shortestEdge = edge;
                    }
                }
            }
        }
        for (Edge edge : vertex.getEdges()) {
            if (shortestEdge.getWeight() > edge.getWeight() && edge.getVertexTwo() != vertex && (edge.getVertexTwo().getDistance() == null || edge.getVertexTwo().getDistance().getValue() > edge.getWeight() + vertex.getDistance().getValue())) {
                shortestEdge = edge;
            }
            if (edge.getVertexOne() == vertex) {
                otherEdges.add(edge);
            }
        }
        shortestEdge.getVertexTwo().setAncestor(shortestEdge.getVertexOne());
        shortestEdge.getVertexTwo().setDistance(new Distance(shortestEdge.getWeight() + shortestEdge.getVertexOne().getDistance().getValue()));
        otherEdges.remove(shortestEdge);
        return shortestEdge.getVertexTwo();
    }

    public HashMap<Vertex, Vertex> dijkstra(Vertex start) {
        var abstand = new HashMap<Vertex, Integer>();
        var vorgänger = new HashMap<Vertex, Vertex>();
        var Q = initialisiere(start, abstand, vorgänger);
        while (Q.size() > 0) {
            var u = getSmallestDistance(abstand, Q);
            Q.remove(u);
            for (Edge edge : u.getEdges()) {
                var v = edge.getOpposingVertex(u);
                if (Q.contains(v)) {
                    distanz_update(u, v, abstand, vorgänger);
                }
            }
        }

        return vorgänger;
    }

    public ArrayList<Vertex> initialisiere(Vertex start, HashMap<Vertex, Integer> abstand, HashMap<Vertex, Vertex> vorgänger) {
        for (Vertex v : vertices) {
            abstand.put(v, Integer.MAX_VALUE);
            vorgänger.put(v, null);
        }
        abstand.replace(start, 0);
        var array = vertices.stream().toList();
        return new ArrayList<>(array);
    }

    public void distanz_update(Vertex u, Vertex v, HashMap<Vertex, Integer> abstand, HashMap<Vertex, Vertex> vorgänger) {
        int alternativ = abstand.get(u) + u.getEdgeTo(v).getWeight();
        if (alternativ < abstand.get(v)) {
            abstand.replace(v, alternativ);
            vorgänger.replace(v, u);
        }
    }

    public ArrayList<Vertex> erstelleKürzestenPfad(Vertex zielknoten, HashMap<Vertex, Vertex> vorgänger) {
        var weg = new ArrayList<Vertex>();
        weg.add(zielknoten);
        var u = zielknoten;
        while (vorgänger.get(u) != null) {
            u = vorgänger.get(u);
            weg.add(0, u);
        }
        return weg;
    }

    public Vertex getSmallestDistance(HashMap<Vertex, Integer> distance, ArrayList<Vertex> Q) {
        int smallestDistance = Integer.MAX_VALUE;
        Vertex currentVertex = null;
        for (Vertex vertex : Q) {
            if (distance.get(vertex) < smallestDistance) {
                smallestDistance = distance.get(vertex);
                currentVertex = vertex;
            }
        }
        return currentVertex;
    }

    public void printPath(ArrayList<Vertex> result) {
        for (int i = result.size() - 1; i >= 0; i--) {
            System.out.print(result.get(i));
            for (Edge edge : result.get(i).getEdges()) {
                if (i == 0) {
                    break;
                }
                if (edge.getVertexTwo().equals(result.get(i - 1))) {
                    System.out.print(" " + edge.getWeight() + " ");
                }
            }
        }
    }

    /**
     * Help Method for the getConnectedVertices(Vertex v)
     *
     * @param v      The Vertex you want all connected Vertices.
     * @param result The current result.
     * @return All Connected Vertices.
     */
    private Set<Vertex> getConnectedVertices(Vertex v, Set<Vertex> result) {
        if (result.contains(v)) {
            return result;
        }
        result.add(v);
        v.getEdges().forEach(edge -> getConnectedVertices(edge.getVertexTwo(), result));
        return result;
    }

    /**
     * Uses Depth search to go through the complete Graph
     *
     * @param vertex A Vertex of the Graph.
     * @return All connected Vertices in the Order when they are found.
     */
    public ArrayList<Vertex> depthSearch(Vertex vertex) {
        var path = new ArrayList<Vertex>();
        var result = new ArrayList<Vertex>();
        if (vertex.getEdges() == null) {
            result.add(vertex);
            return result;
        }
        var edge = vertex.getEdges().get(0);
        Vertex currentVertex;
        path.add(vertex);
        result.add(vertex);
        if (!edge.getVertexOne().equals(vertex)) {
            currentVertex = edge.getVertexOne();
        } else {
            currentVertex = edge.getVertexTwo();
        }

        // Find the end of that thread of vertices.
        currentVertex = findAnEnd(currentVertex, path, result);
        result.add(currentVertex);
        path.add(currentVertex);
        // Start Backtracking
        for (int i = path.size() - 1; i >= 0; i--) {
            currentVertex = path.get(i);
            for (Edge vertexEdge : currentVertex.getEdges()) {
                if (!result.contains(vertexEdge.getVertexOne()) || !result.contains(vertexEdge.getVertexTwo())) {
                    currentVertex = !vertexEdge.getVertexOne().equals(currentVertex) ? vertexEdge.getVertexOne() : vertexEdge.getVertexTwo();
                    //STATUS EINEN NEUEN WEG GEFUNDEN
                    result.add(currentVertex);
                    path.removeAll(path.subList(i + 1, path.size() - 1));
                    currentVertex = findAnEnd(currentVertex, path, result);
                }
            }
        }
        return result;
    }

    /**
     * Goes to an End of a Thread of vertices.
     *
     * @param vertex The Vertex where you want to start.
     * @param path   The Path that was taken
     * @param result All Vertices in the Order how they were found.
     * @return The Last Vertex that was found.
     */
    public Vertex findAnEnd(Vertex vertex, ArrayList<Vertex> path, ArrayList<Vertex> result) {
        while (vertex.getEdges().size() > 1) {
            for (Edge vertexEdge : vertex.getEdges()) {
                if (result.contains(vertexEdge.getVertexOne()) || result.contains(vertexEdge.getVertexTwo())) continue;
                path.add(vertex);
                result.add(vertex);
                vertex = !vertexEdge.getVertexOne().equals(vertex) ? vertexEdge.getVertexOne() : vertexEdge.getVertexTwo();
                break;
            }
        }
        return vertex;
    }

    /**
     * Uses breadthSearch to go through the complete Graph.
     *
     * @param vertex A Vertex of the Graph.
     * @return All connected Vertices in the Order when they are found.
     */
    public ArrayList<Vertex> breadthSearchRec(Vertex vertex) {
        var result = new ArrayList<Vertex>();
        result.add(vertex);
        breadthSearchRec(vertex, result);
        return result;
    }

    /**
     * Uses breadthSearch to go through the complete Graph.
     *
     * @param vertex A Vertex of the Graph.
     * @param result The current result. At the beginning empty.
     * @return All connected Vertices in the Order when they are found.
     */
    private ArrayList<Vertex> breadthSearchRec(Vertex vertex, ArrayList<Vertex> result) {
        var tempResult = new ArrayList<Vertex>();
        for (Edge edge : vertex.getEdges()) {
            if (!result.contains(edge.getVertexTwo())) {
                result.add(edge.getVertexTwo());
                tempResult.add(edge.getVertexTwo());
            } else if (!result.contains(edge.getVertexOne())) {
                result.add(edge.getVertexOne());
                tempResult.add(edge.getVertexOne());
            }
        }
        tempResult.forEach(vertex1 -> breadthSearchRec(vertex1, result));
        return result;
    }

    //TODO

    /**
     * Builds a new Spanning tree based on the given Vertex.
     * Steps:
     * 1. Copys the given Vertex
     * 2.
     *
     * @param vertex The Vertex you want to be the root.
     * @return the root of the new Spanning tree. Attention: Given Vertex ≠ Returned Vertex.
     */
    private Vertex makeMinimumSpanningTree(Vertex vertex) {

        return null;
    }

    /**
     * Returns the lightest Edge of the given Vertex.
     *
     * @param vertex the Vertex you want the Edge from.
     * @return The Lightest Edge from the given Vertex.
     * @throws NoSuchElementException If the Vertex has no edges.
     */
    private Edge getLightestEdge(Vertex vertex) throws NoSuchElementException {
        if (vertex.getEdges().size() == 0) {
            throw new NoSuchElementException();
        }
        var result = vertex.getEdges().get(0);
        for (Edge edge : vertex.getEdges()) {
            if (edge.getWeight() < result.getWeight()) {
                result = edge;
            }
        }
        return result;
    }

    /*
    NOTE: Wenn man den schnellsten weghaben will, kann man nicht alle wege durchlaufen,
    weil wenn der Baum sehr groß ist ein Strang vlt überhaupt nicht zum Ziel kommt und
    er deswegen in die unendlichkeit gehen würde.
     */


}
