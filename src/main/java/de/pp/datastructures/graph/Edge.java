package de.pp.datastructures.graph;

/**
 * The Edges for the Graph
 */
public class Edge {

    private int weight = 0;

    private Vertex vertexOne;
    private Vertex vertexTwo;

    public Edge(Vertex vertexOne, Vertex vertexTwo) {
        vertexOne.getEdges().add(this);
        vertexTwo.getEdges().add(this);
        this.vertexOne = vertexOne;
        this.vertexTwo = vertexTwo;
    }

    public Edge(Vertex vertexOne, Vertex vertexTwo, int weight) {
        this(vertexOne, vertexTwo);
        this.weight = weight;
    }

    public Vertex getVertexOne() {
        return vertexOne;
    }

    public Vertex getVertexTwo() {
        return vertexTwo;
    }

    public int getWeight() {
        return weight;
    }

    public Vertex getOpposingVertex(Vertex vertex) {
        return vertex.equals(vertexOne) ? vertexTwo : vertexOne;
    }

}
