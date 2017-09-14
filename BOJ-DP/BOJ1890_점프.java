import java.util.Scanner;

/**
 * Created by cutececil on 2017. 7. 3..
 */
/*
N×N 게임판에 수가 적혀져 있다. 이 게임의 목표는 가장 왼쪽 위 칸에서 가장 오른쪽 아래 칸으로 규칙에 맞게 점프를 해서 가는 것이다.

각 칸에 적혀있는 수는 현재 칸에서 갈 수 있는 거리를 의미한다. 반드시 오른쪽이나 아래쪽으로만 이동해야 한다. 0은 더 이상 진행을 막는 종착점이며, 항상 현재 칸에 적혀있는 수만큼 오른쪽이나 아래로 가야 한다.

가장 왼쪽 위 칸에서 가장 오른쪽 아래 칸으로 규칙에 맞게 이동할 수 있는 경로의 개수를 구하는 프로그램을 작성하시오.
 */
public class BOJ1890_점프 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[][] A = new int[N][N];
        long[][] D = new long[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = sc.nextInt();
            }
        }

        D[0][0] = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int k = A[i][j];
                if (k > 0) {
                    if (i + k < N) D[i + k][j] += D[i][j];
                    if (j + k < N) D[i][j + k] += D[i][j];
                }
            }
        }

        //for (int i = 0; i < N; i++) {
        //    System.out.println(Arrays.toString(D[i]));
        //}
        System.out.println(D[N - 1][N - 1]);
    }

    //public static void main(String[] args) {
    //    Scanner sc = new Scanner(System.in);
    //
    //    int N = sc.nextInt();
    //    int[][] A = new int[N][N];
    //    for (int i = 0; i < N; i++) {
    //        for (int j = 0; j < N; j++) {
    //            A[i][j] = sc.nextInt();
    //        }
    //    }
    //
    //    long[][] D = new long[N][N]; // !!! int로 하면 overflow, 경로의 개수는 2^63 -1 보다 작거나 같다라고 나와있음
    //    D[0][0] = 1;
    //    for (int i = 0; i < N; i++) {
    //        for (int j = 0; j < N; j++) {
    //            if (D[i][j] > 0 && A[i][j] > 0) { // !!! A[i][j] 인 경우 더 진행할 수 없다. 이 조건절이 빠지면 inside table일 경우 계속 더하게 됨
    //                int k = A[i][j];
    //                if (insideTable(i + k, j, N))
    //                    D[i + k][j] += D[i][j]; // !!! (i, j)까지 올 수 있는 방법을 다음 도착 지점에 계속 더해 줘야한다
    //                if (insideTable(i, j + k, N))
    //                    D[i][j + k] += D[i][j];
    //            }
    //        }
    //    }
    //
    //    //for (int i = 0; i < N; i++) {
    //    //    System.out.println(Arrays.toString(D[i]));
    //    //}
    //    System.out.println(D[N - 1][N - 1]);
    //}

    static boolean insideTable(int i, int j, int N) {
        if (i >= 0 && i < N && j >= 0 && j < N) return true;
        else return false;
    }
}
