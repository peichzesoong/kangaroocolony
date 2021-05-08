/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kangaroocolony;

public class Edge<E> {
    private GraphNode verticeLink;
    private Edge edgeLink;
    private E weight;
    
    public Edge(){
        verticeLink = null;
        edgeLink = null;
        weight = null;
    }
    
    public Edge(GraphNode a, E b, Edge c){
        verticeLink = a;
        edgeLink = c;
        weight = b;
    }

    public GraphNode getVerticeLink() {
        return verticeLink;
    }

    public void setVerticeLink(GraphNode verticeLink) {
        this.verticeLink = verticeLink;
    }

    public Edge getEdgeLink() {
        return edgeLink;
    }

    public void setEdgeLink(Edge edgeLink) {
        this.edgeLink = edgeLink;
    }

    public E getWeight() {
        return weight;
    }

    public void setWeight(E weight) {
        this.weight = weight;
    }
    
    public String toString(){
        return " --> " + verticeLink.getVertice() + " : " + weight;
    }
}
