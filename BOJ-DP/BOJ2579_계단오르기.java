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

        int[][] D = new int[N][3];
        D[0][1] = A[0]; // !!! D[0][1] : N=0일 때, 한칸 밟음 + A[0] 을 해야함. + A[1]을 더하면 안됨
        for (int i = 1; i < N; i++) {
            D[i][0] = Math.max(D[i - 1][1], D[i - 1][2]);
            D[i][1] = D[i - 1][0] + A[i];
            D[i][2] = D[i - 1][1] + A[i];
        }

        System.out.println(Math.max(D[N - 1][1], D[N - 1][2]));
    }
}
