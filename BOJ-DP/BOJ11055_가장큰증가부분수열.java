import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 14..
 */
/*
수열 A가 주어졌을 때, 그 수열의 증가 부분 수열 중에서 합이 가장 큰 것을 구하는 프로그램을 작성하시오.

예를 들어, 수열 A = {1, 100, 2, 50, 60, 3, 5, 6, 7, 8} 인 경우에 합이 가장 큰 증가 부분 수열은 A = {1, 100, 2, 50, 60, 3, 5, 6, 7, 8} 이고, 합은 113이다.
 */
public class BOJ11055_가장큰증가부분수열 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] A = new int[N];
        long[] D = new long[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        long sum = 0;
        for (int i = 0; i < N; i++) {
            D[i] = A[i]; // !!! 이전에 작은 값이 없다면 아래에 D[i] 가 절대 채워지지 않는다
            for (int j = 0; j < i; j++) {
                if (A[i] > A[j]) {
                    D[i] = Math.max(D[i], D[j] + A[i]);
                }

            }
            sum = Math.max(sum, D[i]);
        }

        //System.out.println(Arrays.toString(D));
        System.out.println(sum);
    }
}
