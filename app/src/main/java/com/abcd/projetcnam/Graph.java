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


    public Graph() {
        // On construit toutes les arêtes du graphe avec les index des noeuds adjacents et la longueur
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

        // On construit tous les noeuds du graphe, avec leur index, nom, coordonnées relatives sur l'écran
        // le float x est le numérateur d'une fraction /48, le float y celui d'une fraction /26
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
    }

    public int findIndex(String room) {
        int index = 0;
        for (Node node : getNodes()) {
            if (node.getRoomName().equals(room)) {
                index = node.getIndex();
                break;
            }
        }
        return index;
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

    // Méthode basée sur l'algorithme de Dijkstra qui prend en argument un index de départ, un d'arrivée et retourne
    // la distance du trajet minimal tout en remplissant le tableau finalPath des index du parcours
    public double findMinimumDistance(int startIndex,int stopIndex){
        // On passe les booléens visited et minFound de l'index de départ à true
        nodes[startIndex].setDistanceFromOrigin(0);
        nodes[startIndex].setVisited(true);
        nodes[startIndex].setMinFound(true);
        currentIndex = startIndex;

        // boucle qui s'arrête uniquement lorsque le minimum de l'index d'arrivée est trouvé
        while (!nodes[stopIndex].isMinFound()){
            for (Edge e : nodes[currentIndex].getEdges()){
                neighbourIndex = e.getNeighbourIndex(currentIndex);
                // on ajuste les valeurs du tableau de Dijkstra uniquement si les noeuds n'ont pas été visités ou
                // s'ils ont été visités et qu'on trouve une valeur inférieure à celle déjà présente
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
                // la longueur minimale ainsi que l'index du noeud correspondants sont recherchés
                if (nodes[i].isVisited()&&!(nodes[i].isMinFound())){
                    if (nodes[i].getDistanceFromOrigin()<minLength){
                        minLength = nodes[i].getDistanceFromOrigin();
                        indexMin = i;
                    }
                    //nodes[indexMin].setMinFound(true);
                }
            }
            // l'indexmin a son booleén qui passe à true
            nodes[indexMin].setMinFound(true);
            // l'index courant devient l'indexmin
            currentIndex = indexMin;
        }
        int stepIndex = stopIndex;
        while (stepIndex != startIndex){
            finalPath.add(stepIndex);
            // Tout l'intérêt du previousIndex de la class Node est là : il sert à reconstituer
            // le chemin trouvé dans l'algorithme en ajoutant au finalPath les previousIndex successifs
            stepIndex = nodes[stepIndex].getPreviousIndex();
        }
        finalPath.add(startIndex);
        return nodes[stopIndex].getDistanceFromOrigin();
    }
}