import java.util.Scanner;

/**
 * Created by cutececil on 2017. 3. 22..
 */
public class E81_PathSum_TwoWays {
    public static void main(String[] args) { // class Solution
        Scanner in = new Scanner(System.in);
        
        int N = in.nextInt();
        long[][] arr = new long[N + 1][N + 1];
        
        /* make arr array like below with inputs which has 0 on i == 0 || j == 0
        *  0 0 0 0
        *  0 1 2 3
        *  0 4 5 6
        *  0 7 8 9
        * */
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) arr[i + 1][j + 1] = in.nextInt();
        }
        
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (arr[i - 1][j] == 0) arr[i - 1][j] = Long.MAX_VALUE; // MAX를 구하는 것은 아래 한줄로 되지만 Min을 구하는 것은 초기에 큰값 초기화가 필요하다
                else if (arr[i][j - 1] == 0) arr[i][j - 1] = Long.MAX_VALUE;
                
                arr[i][j] += Math.min(arr[i - 1][j], arr[i][j - 1]);
            }
        }
        
        System.out.println(arr[N][N]);
    }
    
    // 처음 생각한 방법 - 헷갈리고 배열을 두개써서 비효율적이다
    static long solve1(int N, int arr[][]) {
        long[][] re = new long[N + 1][N + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= N; j++) {
                if (i == 0 || j == 0)
                    re[i][j] = 0;
                else if (i <= 1)
                    re[i][j] = arr[i - 1][j - 1] + re[i][j - 1];
                else if (j <= 1)
                    re[i][j] = arr[i - 1][j - 1] + re[i - 1][j];
                else
                    re[i][j] = arr[i - 1][j - 1] + Math.min(re[i - 1][j], re[i][j - 1]);
            }
        }
        
        return re[N][N];
    }
}
