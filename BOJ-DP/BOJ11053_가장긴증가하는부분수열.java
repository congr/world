import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 14..
 */
/*
수열 A가 주어졌을 때, 가장 긴 증가하는 부분 수열을 구하는 프로그램을 작성하시오.

예를 들어, 수열 A = {10, 20, 10, 30, 20, 50} 인 경우에 가장 긴 증가하는 부분 수열은 A = {10, 20, 10, 30, 20, 50} 이고, 길이는 4이다.
 */
public class BOJ11053_가장긴증가하는부분수열 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] A = new int[N];
        int[] D = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        int longest = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (A[i] > A[j])
                    D[i] = Math.max(D[i], D[j] + 1);
            }

            //System.out.println(Arrays.toString(D));
            longest = Math.max(longest, D[i]);
        }

        System.out.println(longest + 1);
    }
}
