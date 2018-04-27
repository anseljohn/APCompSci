public class Scoring
{  
    public static void main(String args[])
    {
        int[] a = {100, 90, 90, 90, 90, 90, 90, 10, 10};
        DanceComp d = new DanceComp(700, 700);
        d.printA(d.getA());
        System.out.print("" + d.first());
    }
    
    public static int countOccurences(int[] scores, int target) {
        int times = 0;
        for(int i : scores) {
            if(i == target) times++;
        }
        return times;
    }
    
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