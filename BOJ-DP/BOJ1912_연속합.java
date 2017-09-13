import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 24..
 */
/*
n개의 정수로 이루어진 임의의 수열이 주어진다. 우리는 이 중 연속된 몇 개의 숫자를 선택해서 구할 수 있는 합 중 가장 큰 합을 구하려고 한다. 단, 숫자는 한 개 이상 선택해야 한다.

예를 들어서 10, -4, 3, 1, 5, 6, -35, 12, 21, -1 이라는 수열이 주어졌다고 하자. 여기서 정답은 12+21인 33이 정답이 된다.
 */
public class BOJ1912_연속합 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();
        int[] A = new int[N];
        long[] D = new long[N];
        
        int amax = -1001;
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
            amax = Math.max(amax, A[i]); // 입력 받을 때 미리 최대값을 구함, 모드 음수라면 최대값을 답으로 출력
        }
        
        long max = 0;
        D[0] = A[0];
        for (int i = 1; i < N; i++) {
            D[i] = D[i - 1] + A[i];
            if (D[i] < 0) D[i] = 0; // 합한 결과가 0 보다 작다면 0으로 채우고 다음수열에서 합산을 다시 시작
            
            max = Math.max(max, D[i]);
        }
        
        System.out.println(Arrays.toString(D));
        
        if (max == 0) max = amax;   // !!! 이런 지저분한 코드 넣은 이유는,, 문제에서 숫자를 단 하나는 꼭 포함해야 한다고 했는데 그 숫자가 음수라면 최저 음수를 출력해야 함
        System.out.println(max);
    }
}
