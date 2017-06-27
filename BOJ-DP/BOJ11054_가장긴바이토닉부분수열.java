import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 15..
 */
/*
수열 S가 어떤 수 Sk를 기준으로 S1 < S2 < ... Sk-1 < Sk > Sk+1 > ... SN-1 > SN을 만족한다면, 그 수열을 바이토닉 수열이라고 한다.

예를 들어, {10, 20, 30, 25, 20}과 {10, 20, 30, 40}, {50, 40, 25, 10} 은 바이토닉 수열이지만,  {1, 2, 3, 2, 1, 2, 3, 2, 1}과 {10, 20, 30, 40, 20, 30} 은 바이토닉 수열이 아니다.

수열 A가 주어졌을 때, 그 수열의 부분 수열 중 바이토닉 수열이면서 가장 긴 수열의 길이를 구하는 프로그램을 작성하시오.
 */
public class BOJ11054_가장긴바이토닉부분수열 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] A = new int[N];
        int[] D = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (A[i] > A[j])
                    D[i] = Math.max(D[i], D[j] + 1);
            }
        }

        int[] D2 = new int[N];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = N - 1; j > i; j--) {
                if (A[i] > A[j])
                    D2[i] = Math.max(D2[i], D2[j] + 1);
            }
        }

        System.out.println(Arrays.toString(A));
        System.out.println(Arrays.toString(D));
        System.out.println(Arrays.toString(D2));

        int max = 0;
        for (int i = 1; i < N - 1; i++) {
            max = Math.max(max, D[i] + D2[i]);
        }

        System.out.println(max + 1);
    }
}
