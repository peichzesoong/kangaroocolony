/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kangaroocolony;

public class Kangaroo {
    private int currentPos;
    private char gender;
    private int pouchCapacity;
    
    public Kangaroo(int currentPos, char gender, int pouchCapacity){
        this.currentPos = currentPos;
        this.gender = gender;
        this.pouchCapacity = pouchCapacity;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getPouchCapacity() {
        return pouchCapacity;
    }

    public void setPouchCapacity(int pouchCapacity) {
        this.pouchCapacity = pouchCapacity;
    }
    
    public String toString(){
        return "Kangaroo pos "+currentPos+". Kangaroo gender "+gender+". Kangaroo pouch capacity "+pouchCapacity;
    }
    
}
