/*
Program 3
Robert Mopia cssc0856
Cesar Lopez cssc0830
CS 310 T/TH
 */
package edu.sdsu.cs.datastructures;

import java.util.*;

public class DirectedGraph<V extends Comparable<V>> implements IGraph<V> {

    ArrayList<V> verti = new ArrayList<>();
    ArrayList<LinkedList<V>> adj;
    private int size = 0;

    public DirectedGraph() {
        adj = new ArrayList<>();
    }

    public void add(V vertexName) {
        adj.add(new LinkedList<V>());
        adj.get(size).add(vertexName);
        size++;
    }

    public void connect(V start, V destination) {
        if (!contains(start) || !contains(destination)) {
            throw new NoSuchElementException();
        }
        for (int i = 0; i < adj.size(); i++) {
            if (start.compareTo(adj.get(i).get(0)) == 0) {
                adj.get(i).add(destination);
            }
        }
    }

    public void clear() {
        adj.clear();
        size = 0;
    }

    public boolean contains(V label) {
        for (int i = 0; i < adj.size(); i++) {
            if (label.compareTo(adj.get(i).get(0)) == 0) {
                return true;
            }
        }
        return false;
    }

    public void disconnect(V start, V destination) {
        if (!contains(start) || !contains(destination)) {
            throw new NoSuchElementException();
        }

        for (int i = 0; i < adj.size(); i++) {
            if (start.compareTo(adj.get(i).get(0)) == 0) {
                adj.get(i).remove(destination);
            }
        }

    }

    public boolean isConnected(V start, V destination) {
        if (!contains(start) || !contains(destination)) {
            throw new NoSuchElementException();
        }
        V ver = start;

        for (int i = 0; i < adj.size(); i++) {
            for (V v : neighbors(ver)) {
                if (v.equals(destination)) {
                    return true;
                }
                ver = v;
            }
        }
        return false;
    }

    public Iterable<V> neighbors(V vertexName) {
        if (!contains(vertexName)) {
            throw new NoSuchElementException();
        }
        LinkedList<V> neighborsList = new LinkedList<>();
        for (int i = 0; i < adj.size(); i++) {
            if (vertexName.equals(adj.get(i).get(0))) {
                neighborsList.addAll(adj.get(i));
                break;
            }
        }
        if (neighborsList.contains(vertexName)) {
            neighborsList.remove(vertexName);
        }
        return neighborsList;

    }

    public void remove(V vertexName) {
        if (!contains(vertexName)) {
            throw new NoSuchElementException();
        }
        for (int i = 0; i < adj.size(); i++) {
            if (vertexName.compareTo(adj.get(i).get(0)) == 0) {
                adj.remove(i);
                size--;
            }
        }

        for (int i = 0; i < adj.size(); i++) {
            if (adj.get(i).contains(vertexName)) {
                adj.get(i).remove(vertexName);
            }
        }

    }

    public List<V> shortestPath(V start, V destination) {
        int startIndex = 0;
        if (!contains(start) || !contains(destination)) {
            throw new NoSuchElementException();
        } else {
            List<V> path = new LinkedList<>();
            ArrayList<V> unVisited = new ArrayList<>();
            ArrayList<Integer> edgeWeight = new ArrayList<>();
            ArrayList<V> pastNode = new ArrayList<>();
            ArrayList<V> nodes = new ArrayList<>();
            ArrayList<V> reversedPath = new ArrayList<>();
            Queue<V> q = new LinkedList<>();

            for (int i = 0; i < adj.size(); i++) {
                unVisited.add(adj.get(i).get(0));
                nodes.add(adj.get(i).get(0));
                edgeWeight.add(999);

                pastNode.add((V) "");

                if (start.compareTo(adj.get(i).get(0)) == 0) {
                    startIndex = i;

                }

            }

            q.add(start);
            unVisited.remove(start);
            edgeWeight.add(startIndex, 0);
            V past = null;

            while (!(q.isEmpty())) {
                V temp = q.poll();

                if (unVisited.contains(temp)) {
                    edgeWeight.add(nodes.indexOf(temp),
                            edgeWeight.get(nodes.indexOf(pastNode.get
                                    (nodes.indexOf(temp)))) + 1);
                    edgeWeight.remove(nodes.indexOf(temp) + 1);
                }

                past = temp;

                unVisited.remove(temp);

                for (V p : neighbors(temp)) {
                    if (unVisited.contains(p)) {
                        q.add(p);
                        pastNode.add(nodes.indexOf(p), temp);
                        pastNode.remove(nodes.indexOf(p) + 1);
                    }
                }

            }

            reversedPath.add(destination);
            V pNode = pastNode.get(nodes.indexOf(destination));

            reversedPath.add(pNode);
            while (pNode != start && pNode != "") {

                pNode = pastNode.get(nodes.indexOf(pNode));
                reversedPath.add(pNode);

            }
            if (pNode == "") {
                reversedPath.clear();
            } else {
                for (int i = reversedPath.size() - 1; i >= 0; i--) {
                    path.add(reversedPath.get(i));

                }
            }

            return path;
        }

    }

    public int size() {
        return size;

    }

    public Iterable<V> vertices() {
        verti.clear();
        for (int i = 0; i < adj.size(); i++) {
            verti.add(adj.get(i).get(0));
        }
        return verti;
    }

    public IGraph<V> connectedGraph(V origin) {
        if (!contains(origin)) {
            throw new NoSuchElementException();
        }

        IGraph<V> cGraph = new DirectedGraph<>();
        V ver = origin;
        cGraph.add(ver);

        for (int i = 0; i < size - 1; i++) {
            for (V v : neighbors(ver)) {
                cGraph.add(v);
                cGraph.connect(ver, v);
                ver = v;
            }
        }

        return cGraph;
    }

    public String toString() {
        String str = "";

        if (size == 0) {
            return "Empty Graph\n";
        } else if (size == 1) {
            str += ("Amount of vertices: " + adj.size() + "\n");
            str += ("Vertex " + adj.get(0).get(0));
            return str;
        }
        str += ("Amount of vertices: " + adj.size() + "\n");
        str += ("Vertices in graph: " + vertices() + "\n");
        str += ("Connections: \n");
        for (int i = 0; i < adj.size(); i++) {
            for (V v : neighbors(adj.get(i).get(0))) {
                str += ("Vertex " + adj.get(i).get(0) + " is connected to ");
                str += (v + "\n");
            }
        }
        return str;
    }

}