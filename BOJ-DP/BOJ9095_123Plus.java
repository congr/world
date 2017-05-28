import java.util.Scanner;

/**
 * Created by cutececil on 2017. 5. 28..
 */
public class BOJ9095_123Plus {
    static int[] D = new int[12];
    
    public static void main(String[] args) { // class Solution
        Scanner sc = new Scanner(System.in);
        
        int T = sc.nextInt();
        
        D[1] = 1;
        D[2] = 2;
        D[3] = 4;
        
        // bottom-up
        for (int i = 4; i < 12; i++) {
            D[i] += D[i - 1] + D[i - 2] + D[i - 3];
        }
        
        // top-down
        //solve(11);
        
        for (int i = 0; i < T; i++) {
            int N = sc.nextInt();
            System.out.println(D[N]);
        }
    }
    
    static int solve(int n) {
        if (n > 0 && D[n] != 0) return D[n];
        
        D[n] = solve(n - 1) + solve(n - 2) + solve(n - 3);
        return D[n];
    }
}
