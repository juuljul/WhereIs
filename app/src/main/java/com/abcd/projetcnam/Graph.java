package com.abcd.projetcnam;

import java.util.ArrayList;

/**
 * Created by julien on 24/07/2015.
 */
public class Graph {
    Edge [] edges;
    int numberOfNodes=15;
    Node [] nodes = new Node[15];
    int currentIndex, neighbourIndex;
    ArrayList<Integer> finalPath = new ArrayList<>();

    //public Graph(Edge[] edges) {
    public Graph() {
        //this.edges = edges;

        Edge e0 = new Edge(13,11,18.0);
        Edge e1 = new Edge(3,13,14.0);
        Edge e2 = new Edge(11,3,29.0);
        Edge e3 = new Edge(5,11,50.0);
        Edge e4 = new Edge(5,3,29.0);
        Edge e5 = new Edge(11,8,41.0);
        Edge e6 = new Edge(5,8,24.0);
        Edge e7 = new Edge(8,9,33.0);
        Edge e8 = new Edge(7,9,25.0);
        Edge e9 = new Edge(5,7,38.0);
        Edge e10 = new Edge(5,1,53.0);
        Edge e11 = new Edge(1,7,45.0);
        Edge e12 = new Edge(2,1,21.0);
        Edge e13 = new Edge(2,12,19.0);
        Edge e14 = new Edge(4,2,22.0);
        Edge e15 = new Edge(12,14,17.0);
        Edge e16 = new Edge(14,10,15.0);
        Edge e17 = new Edge(4,10,12.0);
        Edge e18 = new Edge(6,10,8.0);
        Edge e19 = new Edge(14,0,30.0);


        edges = new Edge[]{e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15,
                e16, e17, e18, e19};

        Node n0 = new Node(0,"16", 39, 11,edges);
        Node n1 = new Node(1,"1",18, 23 ,edges);
        Node n2 = new Node(2,"2", 26, 23,edges);
        Node n3 = new Node(3,"3", 10, 9,edges);
        Node n4 = new Node(4,"4", 33, 23,edges);
        Node n5 = new Node(5,"5", 10, 16,edges);
        Node n6 = new Node(6,"6", 45, 19,edges);
        Node n7 = new Node(7,"7", 10, 23,edges);
        Node n8 = new Node(8,"9 bis", 3, 16,edges);
        Node n9 = new Node(9,"9", 3, 23,edges);
        Node n10 = new Node(10,"10", 39, 19,edges);
        Node n11 = new Node(11,"11", 3, 9,edges);
        Node n12 = new Node(12,"12", 26, 16,edges);
        Node n13 = new Node(13,"13", 6, 3,edges);
        Node n14 = new Node(14,"14", 33, 16,edges);

        nodes = new Node[]{n0, n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14};



        /*for (Edge e:edges){
            if (e.getFromNodeIndex()>numberOfNodes){
                numberOfNodes = e.getFromNodeIndex();
            }
            if (e.getToNodeIndex()>numberOfNodes){
                numberOfNodes = e.getToNodeIndex();
            }
            numberOfNodes ++;
        }
        nodes = new Node [numberOfNodes];
        for (int i=0;i<numberOfNodes;i++){
            nodes[i] = new Node(i,edges);
        }*/
    }


    public Node[] getNodes() {
        return nodes;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public ArrayList<Integer> getFinalPath() {
        return finalPath;
    }

    public double findMinimumDistance(int startIndex,int stopIndex){
        nodes[startIndex].setDistanceFromOrigin(0);
        nodes[startIndex].setVisited(true);
        nodes[startIndex].setMinFound(true);
        currentIndex = startIndex;

        while (!nodes[stopIndex].isMinFound()){
            for (Edge e : nodes[currentIndex].getEdges()){
                neighbourIndex = e.getNeighbourIndex(currentIndex);
                if (!(nodes[neighbourIndex].isVisited())||((nodes[neighbourIndex].isVisited())&&(
                        nodes[currentIndex].getDistanceFromOrigin()+e.getLength()<nodes[neighbourIndex].getDistanceFromOrigin()))){
                    nodes[neighbourIndex].setDistanceFromOrigin(nodes[currentIndex].getDistanceFromOrigin()+e.getLength());
                    nodes[neighbourIndex].setPreviousIndex(currentIndex);
                    nodes[neighbourIndex].setVisited(true);
                }
            }
            double minLength = Double.POSITIVE_INFINITY;
            int indexMin=0;
            for (int i=0;i<numberOfNodes;i++){
                if (nodes[i].isVisited()&&!(nodes[i].isMinFound())){
                    if (nodes[i].getDistanceFromOrigin()<minLength){
                        minLength = nodes[i].getDistanceFromOrigin();
                        indexMin = i;
                    }
                    //nodes[indexMin].setMinFound(true);
                }
            }
            nodes[indexMin].setMinFound(true);
            currentIndex = indexMin;
        }
        int stepIndex = stopIndex;
        while (stepIndex != startIndex){
            finalPath.add(stepIndex);
            stepIndex = nodes[stepIndex].getPreviousIndex();
        }
        finalPath.add(startIndex);
        return nodes[stopIndex].getDistanceFromOrigin();
    }


    /*
    public static void main (String [] args ){
        Edge e0 = new Edge(0,5,10.0);
        Edge e1 = new Edge(0,2,2.5);
        Edge e2 = new Edge(2,3,3.0);
        Edge e3 = new Edge(3,4,2.0);
        Edge e4 = new Edge(1,5,4.0);
        Edge e5 = new Edge(1,4,4.5);
        Edge e6 = new Edge(7,1,25.5);
        Edge e7 = new Edge(7,11,2.0);
        Edge e8 = new Edge(7,9,3.0);
        Edge e9 = new Edge(9,8,1.0);
        Edge e10 = new Edge(8,12,4.0);
        Edge e11 = new Edge(8,6,4.0);
        Edge e12 = new Edge(6,7,2.0);
        Edge e13 = new Edge(10,12,1.0);
        Edge e14 = new Edge(10,11,3.5);
        Edge e15 = new Edge(10,9,4.0);
        Edge e16 = new Edge(0,13,7.5);
        Edge e17 = new Edge(7,13,3.0);
        Edge e18 = new Edge(13,14,2.5);
        Edge e19 = new Edge(13,15,1.0);
        Edge e20 = new Edge(15,16,3.0);
        Edge e21 = new Edge(14,16,12.0);
        Edge e22 = new Edge(7,14,3.5);
        Edge e23 = new Edge(7,17,8.0);
        Edge e24 = new Edge(17,22,4.0);
        Edge e25 = new Edge(18,17,4.0);
        Edge e26 = new Edge(17,19,4.0);
        Edge e27 = new Edge(22,23,1.5);
        Edge e28 = new Edge(18,23,1.0);
        Edge e29 = new Edge(19,23,1.5);
        Edge e30 = new Edge(17,19,4.0);
        Edge e31 = new Edge(19,21,2.5);
        Edge e32 = new Edge(17,20,1.0);
        Edge e33 = new Edge(20,21,8.0);
        Edge [] edges = {e0,e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,e13,e14,e15,
                e16,e17,e18,e19,e20,e21,e22,e23,e24,e25,e26,e27,e28,e29,e30,e31,e32,e33};
        //Edge [] edges = {e0,e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,e13,e14,e15,e16};
        Graph graph = new Graph(edges);
        //graph.setStartIndex(10);
        //graph.setStopIndex(1);
        //graph.findMinimumDistance();
        System.out.println (graph.findMinimumDistance(16,23));
        //System.out.println (graph.getFinalMinDistance());
        ArrayList<Integer> cheminFinal = graph.getFinalPath();
        for (int i=cheminFinal.size()-1; i>=0 ;i--){
            System.out.print(cheminFinal.get(i)+" >> ");
        }
    }*/


}