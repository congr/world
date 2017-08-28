import java.util.Scanner;

/**
 * Created by cutececil on 2017. 7. 26..
 */
public class WOC34_2_MaximumGCD2 {
    static int MAX_SIZE = 1000000;
    
    static int maximumGcdAndSum(int[] A, int[] B) {
        int[] AV = new int[MAX_SIZE + 1];
        int[] BV = new int[MAX_SIZE + 1];
        
        for (int a : A) AV[a] = a;
        for (int b : B) BV[b] = b;
        
        for (int i = MAX_SIZE; i > 0; i--) {
            int maxA = 0, maxB = 0;
            for (int j = i; j <= MAX_SIZE; j += i) {
                maxA = Math.max(maxA, AV[j]);
                maxB = Math.max(maxB, BV[j]);
            }
            
            if (maxA > 0 && maxB > 0) return maxA + maxB;
        }
        
        return 0;
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] A = new int[n];
        for (int A_i = 0; A_i < n; A_i++) {
            A[A_i] = in.nextInt();
        }
        int[] B = new int[n];
        for (int B_i = 0; B_i < n; B_i++) {
            B[B_i] = in.nextInt();
        }
        int res = maximumGcdAndSum(A, B);
        System.out.println(res);
    }
}
