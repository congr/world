import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 12..
 */
public class BOJ2156_포도주 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        int[][] D = new int[N][3];

        D[0][1] = A[0]; // !!! 이번에 처음으로 한잔 마실때, A[1]이 아니고 A[0]이어야 함
        for (int i = 1; i < N; i++) {
            D[i][0] = max(D[i - 1][0], D[i - 1][1], D[i - 1][2]);
            D[i][1] = D[i - 1][0] + A[i];
            D[i][2] = D[i - 1][1] + A[i];
        }

        //System.out.println(Arrays.toString(D[N - 1]));

        System.out.println(max(D[N - 1][0], D[N - 1][1], D[N - 1][2]));
    }

    static int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }
}
