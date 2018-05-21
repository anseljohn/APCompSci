public class Scoring
{  
    //Main method
    public static void main(String args[])
    {
        DanceComp d = new DanceComp(700, 700); //Create a new DanceComp with 10 events and 10 couples
        d.printA(d.getA()); //Print out the array
        System.out.print("" + d.first()); //Print out the highest score and how many couples got the highest score 
    }
    
    //Counts the amount of times that target appears in array scores
    public static int countOccurences(int[] scores, int target) {
        int times = 0;
        for(int i : scores) {
            if(i == target) times++;
        }
        return times;
    }
    
    //Prints out an array in which array[0] is the maximum score in array scores and array[1] is the minimum score in array scores
    public static int[] findMaxAndMin(int[] scores) {
        int[] a = new int[2];
        int low = 100;
        int high = 0;
        for(int i : scores) {
            if(i > high) high = i;
            if(i < low) low = i;
        }
        a[0] = high;
        a[1] = low;
        return a;
    }
    
    /* Prints out the average score for array scores
        - If array scores is >= 6, and the maximum value appears once, then exclude the value, same thing with the minimum value
    */
    public static double averageScore(int[] scores) {
        int tot = 0;
        int div = 0;
        int[] t = findMaxAndMin(scores);
        int l = t[1];
        int h = t[0];
        for(int i : scores) {
            if(i != l && i != h) {
                tot += i;
                div++;
            }
        }
        if(scores.length >= 6) {
            if(countOccurences(scores, h) > 1) {
                for(int i : scores) {
                    if(i == h) {
                        tot += i;
                        div++;
                    }
                }
            }
            if(countOccurences(scores, l) > 1) {
                for(int i : scores) {
                    if(i == l) {
                        tot += i;
                        div++;
                    }
                }
            }
        }
        else {
            for(int i : scores) {
                if(i == l || i == h) {
                    tot += i;
                    div++;
                }
            }
        }
        return tot/div;
    }
}