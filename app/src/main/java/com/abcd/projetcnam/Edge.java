package com.abcd.projetcnam;

public class Edge {

    private int fromNodeIndex;
    private int toNodeIndex;
    private double length;
    private boolean black = false;
    private boolean green = false;

    public Edge(int fromNodeIndex, int toNodeIndex, double length) {
        this.fromNodeIndex = fromNodeIndex;
        this.toNodeIndex = toNodeIndex;
        this.length = length;
    }


    public boolean isGreen() {
        return green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }


    public int getFromNodeIndex() {
        return fromNodeIndex;
    }

    public int getToNodeIndex() {
        return toNodeIndex;
    }

    public int getNeighbourIndex(int index) {
        int result=0;
        if (index == fromNodeIndex ){
            result = toNodeIndex;
        }
        if (index == toNodeIndex){
            result = fromNodeIndex;
        }
        return result;
    }

    public double getLength() {
        return length;
    }

}
