package de.pp.datastructures.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Vertex {
    private List<Edge> edges;

    private String name;

    private Vertex ancestor;

    private Distance distance;

    public Vertex(String name) {
        this.name = name;
        edges = new ArrayList<>();
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Vertex getAncestor() {
        return ancestor;
    }

    public void setAncestor(Vertex ancestor) {
        this.ancestor = ancestor;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Edge getLightestEdge() {
        var reslut = edges.get(0);
        for (Edge edge : edges) {
            if (edge.getWeight() < reslut.getWeight()) {
                reslut = edge;
            }
        }
        return reslut;
    }

    public Edge getEdgeTo(Vertex vertex) {
        for (Edge edge : edges) {
            if (edge.getVertexOne().equals(vertex) || edge.getVertexTwo().equals(vertex)) {
                return edge;
            }
        }
        throw new NoSuchElementException("Hat keine Verbindung zueinander");
    }
}
