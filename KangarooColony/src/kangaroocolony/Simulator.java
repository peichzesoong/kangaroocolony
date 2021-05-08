package kangaroocolony;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JTextArea;

public class Simulator {

    private Graph<Point, Integer> graph;
    Random r = new Random();
    private Point[] pnts;

    public Simulator(){
        graph = new Graph<>();
        int maxPoints = 5;
        int kangarooConstraint = 5;
        int foodConstraint = 30;
        int numberOfPts = r.nextInt(maxPoints-1)+2;    //setting number of points randomly between 2 and 5
        pnts = new Point[numberOfPts];
        for(int i=0 ; i<pnts.length; i++){
            int maxKangaroos = r.nextInt(kangarooConstraint)+1;  // setting maximum number of kangaroos in the point randomly between 1 and 5
            int food = r.nextInt(foodConstraint)+1;         // setting number of food resources in point randomly between 1 and 30
            pnts[i] = new Point(maxKangaroos, food, i+1);  // creating a new point with maxKangaroo, food and point id 
            int numOfKangaroo = r.nextInt(maxKangaroos)+1;   //setting number of kangaroos in the point range depends on the max kangaroo 
            Kangaroo[] kangarooInPnt = new Kangaroo[numOfKangaroo];
            for(int j=0; j<kangarooInPnt.length; j++){
                kangarooInPnt[j] = new Kangaroo(pnts[i].getPointId(), r.nextInt(2) == 0? 'M' : 'F', r.nextInt(10)+1);
                pnts[i].addKangaroo(kangarooInPnt[j]);
            }
            graph.addVertice(pnts[i]);
        }

        for(int i= 0; i<pnts.length;){
            int maxEdge = 2;
            int numberEdges = r.nextInt(maxEdge)+1;  //setting constraint to have maximum number of edge from one point
            int point1 = r.nextInt(pnts.length);
            if(i != point1){
                for(int j=1; j<=numberEdges; j++){
                    graph.addEdge(pnts[i], pnts[point1], r.nextInt(10)); // adding edges for each point to another random point and adding a random obstacle height.
                    point1 = r.nextInt(pnts.length);
                    while(point1 == i)
                        point1 = r.nextInt(pnts.length);
                }
                i++;
            }
        }

    }
    
    public String showMap(){
        return graph.showGraph();
    }

    public String kangarooPos(){
        String s = "";
        for(int i=0; i<pnts.length; i++){
            s += "\nThis point has "+pnts[i].getFood()+" food resources.\n";
            s += pnts[i].showKangaroos()+"\n";
        }
        return s;
        
    }

    public void simulate(JTextArea ta1){
        LinkedList<GraphNode> vertices = graph.getVertices();
        int threshold = 3;        //threshold size determined by user
        for (int i = 0; i < vertices.size(); i++) {
            Point p = (Point) vertices.get(i).getVertice();
            LinkedList<Kangaroo> kangaroos = p.getKangaroosInPoint();
            for (int j = 0; j < kangaroos.size(); j++) {
                //System.out.println("Gender: " + kangaroos.get(j).getGender());
                if (kangaroos.get(j).getGender() == 'M') {             // check if kangaroo is male or not
                    Point ptgotMale = (Point) vertices.get(i).getVertice();  // store the point got male kangaroo
                    ta1.append("-----------------------------------------------------------------------------------------\n");
                    ta1.append("Male kangaroo detected: " + ptgotMale+"\n");
                    ArrayList<Point> c = graph.getAdjacent(ptgotMale);     //ArrayList that stores adjacent points of the point which contains male kangaroo
                    LinkedList<Integer> food = new LinkedList<>();         // LinkedList storing amount of food for each adjacent points

                    int max = 0, femaleInPoint = 0;
//                    int foodNeeded = 2;    //to be changed to calculations later
                    Point chosenPoint = null;
                    Double foodNeeded = 0.0;
                    //initially (when kangaroos are born)
                    int hour=0;
                    //set the time elapsed to be 48 hours, 2 days
                    //for every hour ,food for every locations will be regenerated, NO upper limit for food to be stored in a location
                    while (hour<=48) {
                        hour++;
                        for (GraphNode<Point, Integer> pnt: vertices) {
                            pnt.getVertice().regenerateFood();
                        }
                    }
                    ta1.append("Currently evaluting adjacent point...\n");
                    for (int k = 0; k < c.size(); k++) {
                        Point pointadj = c.get(k);
                        foodNeeded = graph.getWeight(p , pointadj)+(0.5*kangaroos.get(j).getPouchCapacity());    //Food needed calculations
                        food.add(pointadj.getFood());
                        //System.out.println("Current evaluating adjacent point: " + c.get(k));
                        //System.out.println(food);        //show food linked list
                        if (c.size() >= 0) {                // to check if there are adjacent points or not stored in array list c
                            for (int l = 0; l < food.size(); l++) {
                                int first = food.get(l);
                                int female = c.get(l).numberOfFemale();
                                if (first >= max) {
                                    if (first == max) {     //if food are equal, check for female kangaroos
                                        if (female > femaleInPoint) {      //to check which points have more female kangaroos
                                            femaleInPoint = female;
                                            chosenPoint = c.get(l);
                                        }
                                    } else {                        //to check which points have more food
                                        max = first;
                                        femaleInPoint = female;
                                        chosenPoint = c.get(l);
                                    }     //chosenPoint is the point kangaroo will move to
                                }
                            }
                        }
                    }

                    if (!c.isEmpty()) {
                        //System.out.println("Max food is: " + max);
                        //System.out.println("Female in choosen point: " + femaleInPoint);
                        ta1.append("Choosen point is: " + chosenPoint+"\n");

                        if (chosenPoint.getKangarooNumber() >= threshold) {    //if true, threshold reached, colony exist
                            // check if kangaroo food is enough to give the kangaroos in the colony and the kangaroos in the point is less than maximum number of kangaroos
                            if(!chosenPoint.maxKangaroos()){
                                if ((kangaroos.get(j).getPouchCapacity() >= chosenPoint.getKangarooNumber()) && (chosenPoint.getKangarooNumber() < chosenPoint.getMaxKangaroos())) {
                                    chosenPoint.addKangaroo(kangaroos.get(j));
                                    kangaroos.get(j).setCurrentPos(chosenPoint.getPointId());
                                    ptgotMale.moveKangaroo(kangaroos.get(j));

                                } else if (c.size() > 1) {   //if the previous condition not fulfill, the kangaroo will search on the other adjacent point again to see if its possible to join or not
                                    for (int m = 1; m < c.size(); m++) {
                                        chosenPoint = c.get(m);        //to get other available adjacent points in array list c
                                        ta1.append("Changing choosen point: " + chosenPoint+"\n");
                                        if (chosenPoint.getKangarooNumber() >= threshold) {    //if the new chosen point got
                                            if(!chosenPoint.maxKangaroos()){
                                                if ((kangaroos.get(j).getPouchCapacity() >= chosenPoint.getKangarooNumber()) && (chosenPoint.getKangarooNumber() < chosenPoint.getMaxKangaroos())) {
                                                    chosenPoint.addKangaroo(kangaroos.get(j));
                                                    kangaroos.get(j).setCurrentPos(chosenPoint.getPointId());
                                                    ptgotMale.moveKangaroo(kangaroos.get(j));
                                                }
                                            }
                                        } else {   //if threshold not reached, check food enough or not
                                            if(!chosenPoint.maxKangaroos()){
                                                if (chosenPoint.getFood() >= foodNeeded) {     //if enough food, move?
                                                    chosenPoint.addKangaroo(kangaroos.get(j));
                                                    kangaroos.get(j).setCurrentPos(chosenPoint.getPointId());
                                                    ptgotMale.moveKangaroo(kangaroos.get(j));
                                                }
                                            }
                                        }
                                    }

                                } else if (c.size() == 1) {
                                    ta1.append("No movement as no adjacent points.\n");
                                }
                            }
                        } else {   //if threshold not reached, check food enough or not
                            if(!chosenPoint.maxKangaroos()){
                                if (chosenPoint.getFood() >= foodNeeded) {     //if enough food
                                    chosenPoint.addKangaroo(kangaroos.get(j));
                                    kangaroos.get(j).setCurrentPos(chosenPoint.getPointId());
                                    ptgotMale.moveKangaroo(kangaroos.get(j));
                                }
                            }

                        }

                        ta1.append("\nNew position of kangaroos: ");
                        ta1.append(kangarooPos());
                        ta1.append("\n");

                    } else {
                        ta1.append("No movement as no adjacent points.\n");
                    }
                }
            }
        }
    }
}
