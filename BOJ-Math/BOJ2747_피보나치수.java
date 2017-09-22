import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 18..
 */
// 피보나치 수는 0과 1로 시작한다. 0번째 피보나치 수는 0이고, 1번째 피보나치 수는 1이다. 그 다음 2번째 부터는 바로 앞 두 피보나치 수의 합이 된다.
// n=17일때 까지 피보나치 수를 써보면 다음과 같다.
// 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597
// n은 45보다 작거나 같은 자연수이다. n번째 피보나치 수를 구해라.
// => 범위가 작아서 int 처리 가능
public class BOJ2747_피보나치수 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();
        int[] D = new int[N + 1];
        System.out.println(fibonacci(D, N));
    }
    
    static int fibonacci(int[] D, int n) {
        if (n == 0) return 0;
        else if (n == 1) return 1;
        
        if (D[n] > 0) return D[n];
        
        D[n] = fibonacci(D, n - 1) + fibonacci(D, n - 2);
        return D[n];
    }
}
