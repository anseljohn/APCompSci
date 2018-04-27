public class DanceComp{
    private int[][][] a;
    public DanceComp(int events, int couples) {
        a = new int[couples][events][8];
        for(int y = 0; y < a.length; y++) {
            for(int x = 0; x < a[y].length; x++) {
                for(int z = 0; z < a[y][x].length; z++) {
                    a[y][x][z] = (int)(Math.random() * 10) + 90;
                }
            }
        }
    }
    
    public double[][] averages() {
        double[][] scores = new double[a.length][a[0].length];
        for(int y = 0; y < scores.length; y++) {
            for(int x = 0; x < scores[y].length; x++) {
                scores[y][x] = Scoring.averageScore(a[y][x]);
            }
        }
        return scores;
    }
    
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
    
    public String first() {
        return ("Highest score: " + Scoring.findMaxAndMin(finalScores())[0] + "\n" + Scoring.countOccurences(finalScores(), Scoring.findMaxAndMin(finalScores())[0]) + " tied for first!");
    }

    public int[][][] getA() {
        return a;
    }

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