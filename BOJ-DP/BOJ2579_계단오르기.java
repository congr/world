import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 26..
 */
/*
계단 오르는 데는 다음과 같은 규칙이 있다.

계단은 한 번에 한 계단씩 또는 두 계단씩 오를 수 있다. 즉, 한 계단을 밟으면서 이어서 다음 계단이나, 다음 다음 계단으로 오를 수 있다.
연속된 세 개의 계단을 모두 밟아서는 안된다. 단, 시작점은 계단에 포함되지 않는다.
마지막 도착 계단은 반드시 밟아야 한다.
따라서 첫 번째 계단을 밟고 이어 두 번째 계단이나, 세 번째 계단으로 오를 수 있다. 하지만, 첫 번째 계단을 밟고 이어 네 번째 계단으로 올라가거나, 첫 번째, 두 번째, 세번째 계단을 연속해서 모두 밟을 수는 없다.

각 계단에 쓰여 있는 점수가 주어질 때 이 게임에서 얻을 수 있는 총 점수의 최대값을 구하는 프로그램을 작성하시오.
*/
public class BOJ2579_계단오르기 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        //int[][] D = new int[N][3];
        //D[0][1] = A[0]; // !!! D[0][1] : N=0일 때, 한칸 밟음 + A[0] 을 해야함. + A[1]을 더하면 안됨
        //for (int i = 1; i < N; i++) {
        //    D[i][0] = Math.max(D[i - 1][1], D[i - 1][2]);
        //    D[i][1] = D[i - 1][0] + A[i];
        //    D[i][2] = D[i - 1][1] + A[i];
        //}
        //
        //System.out.println(Math.max(D[N - 1][1], D[N - 1][2]));


        // 연속 두개를 밟을 수 없다는 조건이 있다.
        // N 계단을 밟는 경우는 바로 N-1을 밟고, 그전에는 N-3을 밟았어야 한다. N-2를 밟으면 연속 세개다. 그래서 A[N-2]를 더해야한다. D[N-2]가 아님
        // N 계단을 밟는 다른 경우는, N-2를 밟고 오는 경우. 이전은 상관없다
        int[] D = new int[N];
        D[0] = A[0];
        D[1] = A[1] + A[0]; // 1까지 도착할때는 0, 1 1개씩 꼭꼭 다 밟고 올라오는 것이 점수가 크다
        D[2] = Math.max(A[1] + A[2], D[0] + A[2]); // i-3은 0으로 계산하면 됨.

        for (int i = 3; i < N; i++) {
            int a = D[i-3] + A[i-1] + A[i];
            int b = D[i-2] + A[i];

            D[i] = Math.max(a, b);
        }

        System.out.println(D[N - 1]);
    }
}
