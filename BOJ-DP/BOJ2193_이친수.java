/**
 * Created by cutececil on 2017. 6. 5..
 */

import java.util.Scanner;

/**
 * 0과 1로만 이루어진 수를 이진수라 한다. 이러한 이진수 중 특별한 성질을 갖는 것들이 있는데, 이들을 이친수(pinary number)라 한다. 이친수는 다음의 성질을 만족한다.
 * <p>
 * 이친수는 0으로 시작하지 않는다.
 * 이친수에서는 1이 두 번 연속으로 나타나지 않는다. 즉, 11을 부분 문자열로 갖지 않는다.
 * 예를 들면 1, 10, 100, 101, 1000, 1001 등이 이친수가 된다. 하지만 0010101이나 101101은 각각 1, 2번 규칙에 위배되므로 이친수가 아니다.
 * <p>
 * N(1≤N≤90)이 주어졌을 때, N자리 이친수의 개수를 구하는 프로그램을 작성하시오.
 */
public class BOJ2193_이친수 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        long[][] D = new long[N + 1][2]; // !!! int 는 overflow

        //D[1][0] = 0;  // 1자리수 일때 0은 이친수가 아님
        D[1][1] = 1;    // 1자리수 일때 1은 이친수
        for (int i = 2; i <= N; i++) {
            for (int j = 0; j <= 1; j++) {
                if (j == 0)
                    D[i][j] = D[i - 1][0] + D[i - 1][1];
                else if (j == 1)
                    D[i][j] = D[i - 1][0]; // 마지막수가 1이라면 앞에 1이 연속 올 수 없다
            }
        }

        System.out.println(D[N][0] + D[N][1]);
    }
}
