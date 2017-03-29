import java.util.Scanner;

/**
 * Created by cutececil on 2017. 3. 20..
 */
public class AProblem04 {
    public static void main(String[] args) { // class Solution
        Scanner in = new Scanner(System.in);
        
        int N = in.nextInt();
        int M = in.nextInt();
        
        long cnt = 0;
        long result = 0;
        for (int i = N; i <= M; i++) {
            boolean yes = IsSquare(i);
//            if (cnt % 2 == 1) result++;
            if (yes) result++;
        }

        System.out.println(result);
    }
    
    static boolean IsSquare(long num) {
        long temp = (long)(Math.sqrt((double)num)+0.5);
        return temp*temp == num;
    }
    
    static long count(long num) {
        long cnt = 0;
        for (long a = 1; a <= num; a++) {
            if ((num % a) == 0) {
                cnt++;
            }
        }
        return cnt;
    }
    
   
}
