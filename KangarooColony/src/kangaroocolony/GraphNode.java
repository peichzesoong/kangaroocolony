/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kangaroocolony;

public class GraphNode<V, E>{
    private V vertice;
    private GraphNode verticeLink;
    private E edgeLink;
    private boolean marked;
    
    public GraphNode(){
        vertice = null;
        verticeLink = null;
        edgeLink = null;
        marked = false;
    }
    
    public GraphNode(V a, GraphNode b){
        vertice = a;
        verticeLink = b;
        edgeLink = null;
        marked = false;
    }

    public V getVertice() {
        return vertice;
    }

    public void setVertice(V vertice) {
        this.vertice = vertice;
    }

    public GraphNode getVerticeLink() {
        return verticeLink;
    }

    public void setVerticeLink(GraphNode verticeLink) {
        this.verticeLink = verticeLink;
    }

    public E getEdgeLink() {
        return edgeLink;
    }

    public void setEdgeLink(E edgeLink) {
        this.edgeLink = edgeLink;
    }

    public boolean getMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }
    
    public String toString(){
        return vertice + " --> ";
    }
    
}
