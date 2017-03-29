import java.util.Scanner;

/**
 * Created by cutececil on 2017. 3. 27..
 */
public class E174_SqaureLaminae {
    private static final int TYPE_LIMIT = 10;
    public static void main(String[] args) throws Exception{ // class Solution
        Scanner in = new Scanner(System.in);
        
        int TC = in.nextInt();
        while (TC-- > 0) {
            int K = in.nextInt();// eg. 100 - K is number of a square to make hollow : 100개로 사각형 띠를 만들어라
            //int K = SIZE_LIMIT;
            
            //System.out.println(solve(K));
            System.out.println(run(K));
        }
    }
    
    static String run(int SIZE_LIMIT) {
        // Generate all possible laminae with at most SIZE_LIMIT tiles
        int[] type = new int[SIZE_LIMIT + 1];
        for (int n = 3; (n - 1) * 4 <= SIZE_LIMIT; n++) {  // Outer square size
            for (int m = n - 2; m >= 1; m -= 2) {  // Inner square hole size
                int tiles = n * n - m * m;  // Intermediate computation may overflow, but result is correct
                if (tiles > SIZE_LIMIT)
                    break;
                type[tiles]++;
            }
        }
        
        // Examine the type of each total tiling
        int count = 0;
        for (int t : type) {
            if (1 <= t && t <= TYPE_LIMIT)
                count++;
        }
        return Integer.toString(count);
    }
    
    static int solve(int K) {
//        LinkedHashSet<Long> set = new LinkedHashSet<>(); // to remove duplicate numbers
        int[] types = new int[K+1];
        
        int cnt = 0;
        for (int n = 1; n <= 10; n++) { // 1 <= n <= 10
//            LinkedHashSet<Long> set = new LinkedHashSet<>();
            cnt =0;
            for (int a = 2 * n + 1; cnt <= K ; a++) {
                cnt = a * a - (a - 2 * n) * (a - 2 * n);// a^2 - (a-2n)^2
                if (cnt <= K) // t <= k
                    types[cnt]++;
//                    set.add(cnt);
                else if (cnt > K)
                    break;
            }
           // System.out.println(n + "---------");
           // System.out.println(set.toString());
        }
    
        int result =0;
        for (int i = 1; i <= K ; i++) {
            int t = types[i];
            if (0 < t && t <= 10) 
                result++;
            else if(t>10)
                System.out.println("else");
            else if (0==t)
                System.out.println("t==0");
        }
        return result;
            //System.out.println(set.toString()); // [8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 52, 56, 60, 64, 68, 72, 76, 80, 84, 88, 92, 96, 100]
        //return 10;//set.size();
    }
    
    private static final int SIZE_LIMIT = 2000;
    
    
   
}
