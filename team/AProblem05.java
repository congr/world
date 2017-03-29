import java.util.Scanner;

/**
 * Created by cutececil on 2017. 3. 22..
 */

public class AProblem05 {
    public static void main(String[] args) { // class Solution
        Scanner in = new Scanner(System.in);
        
        int N = in.nextInt();
        int[] arr = new int[N];
        int cnt = 0;
        int sum = 0;
        for (int i = 0; i < N; i++) {
            arr[i] = in.nextInt();
            if (arr[i] == i + 1) cnt++;
            sum += arr[i];
        }
        
        if (cnt == N) {
            System.out.println(-1);
            return;
        }
        if (sum == 10000) {
            System.out.println(-1);
            return;
        }
        
        
        if (sum >= 10000) {
            System.out.println(101);
            return;
        }
    
        int val[] = arr;
        int wt[] = arr;
        int W = 10000;
        int n = arr.length;
        
        for (int i = 1; i <= W; i++) {
            if (i <= n && i == val[i - 1]) continue;
            
            int capa = knapSack(i, wt, val, n);
            if (capa != i) {
                System.out.println(i);
                return;
            }
            if (sum < i) {
                System.out.println(i);
                return;
            }
        }
        
        System.out.println(-1);
    }
    
    // A utility function that returns maximum of two integers
    static int max(int a, int b) {
        return (a > b) ? a : b;
    }
    
    // Returns the maximum value that can be put in a knapsack of capacity W
    static int knapSack(int W, int wt[], int val[], int n) {
        int i, w;
        int K[][] = new int[n + 1][W + 1];
        
        // Build table K[][] in bottom up manner
        for (i = 0; i <= n; i++) {
            for (w = 0; w <= W; w++) {
                if (i == 0 || w == 0)
                    K[i][w] = 0;
                else if (wt[i - 1] <= w)
                    K[i][w] = max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]);
                else
                    K[i][w] = K[i - 1][w];
                
                if (K[i][w] == W|| K[i][w] == val[n-1])  return W;
            }
        }
        
        return K[n][W];
    }
}
        
