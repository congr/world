import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 18..
 */
public class BOJ_2747 {
    public static void main(String[] args) { // class Solution
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }
        
        System.out.println(solve());
    }
}
