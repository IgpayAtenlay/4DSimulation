package Obsolete;

import Data.Dimention;

import java.util.ArrayList;
import java.util.HashMap;

public class Edges {
    private HashMap<Dimention, ArrayList<Dimention>> edges;
    public Edges() {
        edges = new HashMap<>();
    }
    public void addEdge(Dimention start, Dimention end) {
        if (!edges.containsKey(start)) {
            edges.put(start, new ArrayList<>());
        }
        if (!edges.containsKey(end)) {
            edges.put(end, new ArrayList<>());
        }
        edges.get(start).add(end);
        edges.get(end).add(start);
    }
    public void removeEdge(Dimention start, Dimention end) {
        if (edges.containsKey(start)) {
            edges.get(start).remove(end);
            if (edges.get(start).size() == 0) {
                edges.remove(start);
            }
        }
        if (edges.containsKey(end)) {
            edges.get(end).remove(start);
            if (edges.get(end).size() == 0) {
                edges.remove(end);
            }
        }
    }
    public HashMap<Dimention, ArrayList<Dimention>> getEdges() {
        return edges;
    }
    public int numPoints() {
        return edges.size();
    }
    public int numEdges() {
        int numEdges = 0;
        for (ArrayList<Dimention> edge : edges.values()) {
            numEdges += edge.size();
        }
        return numEdges / 2;
    }
    public void move(int distance, Dimention direction) {
        edges.forEach((k, v) -> {
            v.replaceAll(e -> e.move(distance, direction));
        });
        HashMap<Dimention, ArrayList<Dimention>> copyEdges = (HashMap<Dimention, ArrayList<Dimention>>) edges.clone();
        edges.clear();
        copyEdges.forEach((k,v) -> edges.put(k.move(distance, direction), v));
    }
}
