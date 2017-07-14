import java.util.Scanner;

/**
 * Created by cutececil on 2017. 7. 10..
 */
/*
세준이는 어떤 문자열을 팰린드롬으로 분할하려고 한다.

예를 들어, ABACABA를 팰린드롬 분할하면, {A, B, A, C, A, B, A}, {A, BACAB, A}, {ABA, C, ABA}, {ABACABA}가 된다.

분할의 개수의 최소값을 출력하는 프로그램을 작성하시오.

첫째 줄에 문자열이 주어진다. 이 문자열의 최대길이는 2,500이다.
 */
public class BOJ1509_팰린드롬분할 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String S = sc.next().trim();
        int N = S.length();

        char[] A = S.toCharArray();
        int[][] C = checkPalindrome(A, N);

        int[] D = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            D[i] = 987654321;

            for (int j = 1; j <= i; j++) {
                if (C[j-1][i-1] == 1) // j ~ i가 팰랜드롬일 때
                    D[i] = Math.min(D[i], D[j - 1] + 1);
            }
        }

        System.out.println(D[N]);
    }

    // 길이 1일때 2일때, ... N일때 마다 다 팰린드롬인지 체크하여 D [][] 배열을 채움
    static int[][] checkPalindrome(char[] A, int N) {
        int[][] D = new int[N][N];

        // 길이가 1인 경우는 무조건 팰린드롬이므로 1 초기화, D[s][e] => D[1]][1] = 1
        for (int i = 0; i < N; i++) {
            D[i][i] = 1;
        }

        // 길이가 2인 경우는 A[i] == A[i+1] 이면 팰린드롬
        for (int i = 0; i < N - 1; i++) {
            if (A[i] == A[i + 1]) D[i][i + 1] = 1;
        }

        // 길이가 3이상이라면, A[i] == A[i+1] 이 아니고, s와 e를 제외한 가운데 사이에 있는 수들이 팰린드롬인지 체크 필요
        for (int l = 3; l <= N; l++) { // l은 길이
            for (int s = 0; s < N - l + 1; s++) {
                int e = s + l - 1;
                if (A[s] == A[e] && D[s + 1][e - 1] == 1)
                    D[s][e] = 1;
            }
        }

        return D;
    }

}
