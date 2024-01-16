package de.pp.datastructures;

import de.pp.datastructures.graph.Edge;
import de.pp.datastructures.graph.Graph;
import de.pp.datastructures.graph.Vertex;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        var graph = new Graph();
        var a = new Vertex("a");
        var b = new Vertex("b");
        var c = new Vertex("c");
        var d = new Vertex("d");
        var e = new Vertex("e");
        var f = new Vertex("f");
        var g = new Vertex("g");
        var h = new Vertex("h");
        var i = new Vertex("i");


        new Edge(a, b, 5);
        new Edge(a, c, 2);
        new Edge(b, d, 3);
        new Edge(b, e, 1);
        new Edge(c, f, 4);
        new Edge(c, g, 3);
        new Edge(c, e, 2);
        new Edge(c, b, 2);
        new Edge(a, h, 3);
        new Edge(a, i, 1);
        new Edge(h, g, 1);
        new Edge(i, f, 1);
        graph.addVertex(a, b,c,d,e,f,g,h,i);


        var vorgänger = graph.dijkstra(a);
        //vorgänger.forEach((vertex, vertex2) -> System.out.println(vertex + " " + vertex2));
        var weg = graph.erstelleKürzestenPfad(d, vorgänger);
        weg.forEach(System.out::println);

    }
}
