import java.util.Scanner;

/**
 * Created by cutececil on 2017. 7. 5..
 */
/*
명우는 홍준이와 함께 팰린드롬 놀이를 해보려고 한다.

먼저, 홍준이는 자연수 N개를 칠판에 적는다. 그 다음, 명우에게 질문을 총 M번 한다.

각 질문은 두 정수 S와 E로 나타낼 수 있으며, S번째 수부터 E번째 까지 수가 팰린드롬을 이루는지를 물어보며, 명우는 각 질문에 대해 팰린드롬이다 또는 아니다를 말해야 한다.

예를 들어, 홍준이가 칠판에 적은 수가 1, 2, 1, 3, 1, 2, 1라고 하자.

S = 1, E = 3인 경우 1, 2, 1은 팰린드롬이다.
S = 2, E = 5인 경우 2, 1, 3, 1은 팰린드롬이 아니다.
S = 3, E = 3인 경우 1은 팰린드롬이다.
S = 5, E = 7인 경우 1, 2, 1은 팰린드롬이다.
자연수 N개와 질문 M개가 모두 주어졌을 때, 명우의 대답을 구하는 프로그램을 작성하시오.
 */
public class BOJ10942_팰린드롬 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        int[][] D = checkPalindrome(A, N);
        int M = sc.nextInt();
        for (int i = 0; i < M; i++) {
            int S = sc.nextInt(); // 1부터 시작
            int E = sc.nextInt();

            System.out.println(D[S - 1][E - 1]);
        }
    }

    // 길이 1일때 2일때, ... N일때 마다 다 팰린드롬인지 체크하여 D [][] 배열을 채움
    static int[][] checkPalindrome(int[] A, int N) {
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
