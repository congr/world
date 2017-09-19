import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 19..
 */
// 자연수 N과 정수 K가 주어졌을 때 이항 계수 (N K)를 10,007로 나눈 나머지를 구하는 프로그램을 작성하시오. 10,007로 나눈 나머지를 출력한다.
// 파스칼의 삼각형으로 구현 - 솔루션 참고
public class BOJ11051_이항계수2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int K = sc.nextInt();

        int[][] D = new int[N + 1][N + 1]; // [N + 1][N + 1] 로 할당한다

        for (int i = 0; i <= N; i++) {
            D[i][0] = D[i][i] = 1; // 라인마다 첫수와 끝수(i와 같다)는 1로 세팅, 0라인의 끝수 인덱스는 0, 1라인의 끝수 인덱스는 1, 2라인의 끝수 인덱수는 2

            for (int j = 1; j < i; j++) { // j 0은 이미 1로 채워서 1부터 시작, 끝수 인덱스는 i이고 이미 1로 채워서 j<i, 즉 i가 2가 되야 수행됨
                D[i][j] = (D[i - 1][j] + D[i - 1][j - 1]) % 10007; // 안고른 경우 + 고른 경우
            }
        }

        //for (int i = 0; i <= N; i++) {
        //    System.out.println(Arrays.toString(D[i]));
        //}

        System.out.println(D[N][K] % 10007);
    }
}
