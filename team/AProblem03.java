import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 3. 20..
 */
public class AProblem03 {
    public static void main(String[] args) { // class Solution
        Scanner in = new Scanner(System.in);
        
        int N = in.nextInt();
        int T = in.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = in.nextInt();
        }
        
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            int t = T - arr[i];
            int res = Arrays.binarySearch(arr, t);
            if (res >= 0) {// found
                cnt++;
            }
        }
        
        System.out.println(cnt/2);
    }
    
    
}
