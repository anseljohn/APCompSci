/*
    DanceComp class represents Couples who participate in events in which 8 judges give their scores from 90-99.
*/

public class DanceComp{
    // a var for holding all couples in all events and all their scores from the 8 judges
    private int[][][] a;

    //Constructor fills array with random scores from 90-99
    public DanceComp(int events, int couples) { //takes in input for amount of events and couples
        a = new int[couples][events][8];
        for(int y = 0; y < a.length; y++) {
            for(int x = 0; x < a[y].length; x++) {
                for(int z = 0; z < a[y][x].length; z++) {
                    a[y][x][z] = (int)(Math.random() * 10) + 90;
                }
            }
        }
    }
    
    //Returns a now 2d array holding each couple's average score per event
    public double[][] averages() {
        double[][] scores = new double[a.length][a[0].length];
        for(int y = 0; y < scores.length; y++) {
            for(int x = 0; x < scores[y].length; x++) {
                scores[y][x] = Scoring.averageScore(a[y][x]);
            }
        }
        return scores;
    }
    
    //Final scores are the total scores of each couple's events
    public int[] finalScores() {
        double[][] toSum = averages();
        int[] finalScores = new int[toSum.length];
        for(int y = 0; y < toSum.length; y++) {
            int sum = 0;
            for(int x = 0; x < toSum[y].length; x++) {
                sum += toSum[y][x];
            }
            finalScores[y] = Math.round(sum);
        }
        return finalScores;
    }
    
    //Prints out the highest score, and how many couples got the highest score
    public String first() {
        return ("Highest score: " + Scoring.findMaxAndMin(finalScores())[0] + "\n" + Scoring.countOccurences(finalScores(), Scoring.findMaxAndMin(finalScores())[0]) + " tied for first!");
    }

    //Getter for the a array
    public int[][][] getA() {
        return a;
    }

    //Prints out all the values of the a array
    public void printA(int[][][] mat) {
        for(int[][] x: mat) {
            System.out.print(" { ");
            for(int[] y: x) {
                System.out.print(" { ");
                for(int z: y) {
                    System.out.print(z + " " );
                }
                System.out.print(" } ");
            }
            System.out.print(" } ");
            System.out.println("");
        }
        System.out.println();
    }
}