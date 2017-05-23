import java.util.Scanner;

/**
 * Created by cutececil on 2017. 5. 23..
 */

/*
정수 X에 사용할 수 있는 연산은 다음과 같이 세 가지 이다.
X가 3으로 나누어 떨어지면, 3으로 나눈다.
X가 2로 나누어 떨어지면, 2로 나눈다.
1을 뺀다.

정수 N이 주어졌을 때, 위와 같은 연산 세 개를 적절히 사용해서 1을 만들려고 한다. 연산을 사용하는 횟수의 최소값을 출력하시오.
입력 2  출력 1
입력 10 출력 3
 */
public class BOJ1463_Make1 {
public static void main(String[] args) { // class Solution
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    //int[] D = new int[1000001];
    //
    //D[1] = 0; // 이미 1인 경우 초기화. 근데 이미 0으로 초기화 됨
    //for (int i = 2; i <= N; i++) { // 2부터 체크 시작
    //    D[i] = D[i - 1] + 1;  // 이전 연산에 +1 횟수를 더한다. 모든 케이스에 해당한다. N보다 1작은 수는 항상 존재하므로.
    //    if (i % 3 == 0 && D[i] > D[i / 3] + 1) // D[i]에 -1 한 것 보다 더 작은 케이스가 있다면
    //        D[i] = D[i / 3] + 1;
    //    if (i % 2 == 0 && D[i] > D[i / 2] + 1)
    //        D[i] = D[i / 2] + 1;
    //}
    //
    ////System.out.println(Arrays.toString(D));
    //System.out.println(D[N]);

    //Arrays.fill(R, 987654321);

    System.out.println(solve(N));
}

static int R[] = new int[1000001];

static int solve(int n) {
    if (n == 1) return 0;

    if (R[n] > 0) return R[n];

    R[n] = solve(n - 1) + 1;
    if (n % 3 == 0)
        R[n] = Math.min(R[n], solve(n / 3) + 1);
    if (n % 2 == 0)
        R[n] = Math.min(R[n], solve(n / 2) + 1);
    return R[n];
}
}
