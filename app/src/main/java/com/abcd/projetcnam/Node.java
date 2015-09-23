package com.abcd.projetcnam;

import java.util.ArrayList;

/**
 * Created by julien on 24/07/2015.
 */

public class Node {

    int index;
    String roomName;
    float x,y;
    // boolean à true lorsque le noeud fait partie du trajet minimal qu'on recherche
    boolean green = false;
    // tableau des noeuds adjacents
    ArrayList <Integer> neighboursIndex= new ArrayList<Integer>();
    // tableau des arêtes adjacentes
    ArrayList <Edge> edges = new  ArrayList <Edge>();

    // variables utilisées pour l'algorithme de Dijkstra dans la classe Graph
    double distanceFromOrigin;
    int previousIndex;
    boolean visited, minFound = false;


    public Node(int index, String roomName, float x, float y, Edge[] edgesGraph) {
        this.index= index;
        this.roomName=roomName;
        this.x = x;
        this.y = y ;
        // trouve les arêtes adjacentes et les ajoute à l'Arraylist edges
        for (Edge e : edgesGraph){
            if (e.getFromNodeIndex() == index){
                edges.add(e);
                neighboursIndex.add(e.getToNodeIndex());
            }
            if (e.getToNodeIndex() == index){
                edges.add(e);
                neighboursIndex.add(e.getFromNodeIndex());
            }
        }
    }


    public boolean isGreen() {
        return green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }

    public int getIndex() {
        return index;
    }

    public String getRoomName() {
        return roomName;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public double getDistanceFromOrigin() {
        return distanceFromOrigin;
    }

    public void setDistanceFromOrigin(double distanceFromOrigin) {
        this.distanceFromOrigin = distanceFromOrigin;
    }

    public int getPreviousIndex() {
        return previousIndex;
    }

    public void setPreviousIndex(int previousIndex) {
        this.previousIndex = previousIndex;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isMinFound() {
        return minFound;
    }

    public void setMinFound(boolean minFound) {
        this.minFound = minFound;
    }

}

