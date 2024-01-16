package de.pp.datastructures.graph;

import java.util.HashMap;

public class Result {
    HashMap<Vertex, Integer> distance = new HashMap<Vertex, Integer>();
    HashMap<Vertex, Vertex> predecessor = new HashMap<Vertex, Vertex>();


    public HashMap<Vertex, Integer> getDistance() {
        return distance;
    }

    public void setDistance(HashMap<Vertex, Integer> distance) {
        this.distance = distance;
    }

    public HashMap<Vertex, Vertex> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(HashMap<Vertex, Vertex> predecessor) {
        this.predecessor = predecessor;
    }

}
