package com.abcd.projetcnam;

import java.util.ArrayList;

/**
 * Created by julien on 24/07/2015.
 */

public class Node {

    int index;
    String roomName;
    float x,y;
    ArrayList <Integer> neighboursIndex= new ArrayList<Integer>();
    ArrayList <Edge> edges = new  ArrayList <Edge>();
    double distanceFromOrigin;
    int previousIndex;
    boolean visited, minFound = false;

    //public Node(int index, Edge[] edgesGraph) {
    public Node(int index, String roomName, float x, float y, Edge[] edgesGraph) {
        this.index= index;
        this.roomName=roomName;
        this.x = x;
        this.y = y ;

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

    public int getIndex() {
        return index;
    }

    public String getRoomName() {
        return roomName;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

