import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 4..
 */
/*
오르막 수는 수의 자리가 오름차순을 이루는 수를 말한다. 이 때, 인접한 수가 같아도 오름차순으로 친다.
예를 들어, 2234와 3678, 11119는 오르막 수이지만, 2232, 3676, 91111은 오르막 수가 아니다.
수의 길이 N이 주어졌을 때, 오르막 수의 개수를 구하는 프로그램을 작성하시오. 수는 0으로 시작할 수 있다.
* */
public class BOJ11057_오르막수 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int L = 10; // 0 ~ 9
        int N = sc.nextInt();
        int[][] D = new int[N + 1][L];
        
        Arrays.fill(D[1], 1);
        //D[1][0] = 0; // 숫자는 0으로 시작할 수 있다
        for (int i = 2; i <= N; i++) {
            for (int j = 0; j < L; j++) {
                for (int k = 0; k <= j; k++) {
                    D[i][j] += D[i - 1][k] % 10007; // overflow 발생하므로 계속 %를 해주어야
                }
            }
        }
        
        //System.out.println(Arrays.toString(D[N]));
        long cnt = 0;
        for (int i = 0; i < L; i++) {
            cnt = (cnt + D[N][i]) % 10007; // long 인데도 그냥 다 더하면 overflow 발생하는 듯
        }
        System.out.println(cnt);
    }
}
