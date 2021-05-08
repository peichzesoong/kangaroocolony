/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kangaroocolony;

import java.util.LinkedList;

public class Point implements Comparable<Point> {
    
    private int maxKangaroos;
    private int food;
    private int pointId;
    private LinkedList<Kangaroo> kangaroosInPoint;
    
    public Point(){
        maxKangaroos = 0;
        food = 0;
        pointId = 0;
        kangaroosInPoint = new LinkedList<>();
    }
    
    public Point(int maxKangaroos, int food, int pointId){
        this.maxKangaroos = maxKangaroos;
        this.food = food;
        this.pointId = pointId;
        kangaroosInPoint = new LinkedList<>();
    }

    public int getMaxKangaroos() {
        return maxKangaroos;
    }
    
    public boolean maxKangaroos(){
        return kangaroosInPoint.size() == maxKangaroos;
    }

    public void setMaxKangaroos(int maxKangaroos) {
        this.maxKangaroos = maxKangaroos;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }
    
    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }
    
    public void addKangaroo(Kangaroo a){
        if(kangaroosInPoint.size() < maxKangaroos)
            kangaroosInPoint.add(a);
        else System.out.println("Maximum kangaroos in this point");
    }

    public String showKangaroos(){
        String s = "";
        s += "Kangaroos in point " + pointId+ " are : \n";
        for(int i=0; i<kangaroosInPoint.size(); i++)
            s += kangaroosInPoint.get(i)+"\n";
            
        return s;
    }
    public int compareTo(Point point2){
        if(pointId > point2.pointId) 
            return 1;
        else if(pointId < point2.pointId)
            return -1;
        else 
            return 0;
    }
    
    public LinkedList getKangaroosInPoint(){
        return kangaroosInPoint;
    }
    
    public void setKangaroosInPoint (LinkedList<Kangaroo> kangaroosInPoint){
        this.kangaroosInPoint=kangaroosInPoint;
    }
    
    public String toString(){
        return "Point id is "+pointId + ". It can contain " + maxKangaroos + " and currently has " + kangaroosInPoint.size() + " kangaroo(s)";
    }
    
    public void moveKangaroo (Kangaroo a){      //remove kangaroo from a point
        if (!kangaroosInPoint.isEmpty()){
            kangaroosInPoint.remove(a);
        }
        else {
            System.out.println("Kangaroo not found");
        }
    }
    
    public int getKangarooNumber() {
        return kangaroosInPoint.size();
    }
    
    public int numberOfFemale(){        //get number of female kangaroos in a point
        int female=0;
        for (int i=0;i<kangaroosInPoint.size();i++){
            if(kangaroosInPoint.get(i).getGender()=='F'){
                female++;
            } 
        }
        return female;
    }

    public void regenerateFood(){
        food += 1;
    }
    
}
