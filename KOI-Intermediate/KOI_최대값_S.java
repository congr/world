import java.util.Scanner;

/**
 * Created by cutececil on 2017. 10. 31..
 */
public class KOI_최대값_S {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = 9;
        int maxn = 0, maxi = -1;
        for (int i = 0; i < N; i++) {
            int n = sc.nextInt(); // 입력은 자연수
            if (maxn < n) {
                maxn = n;
                maxi = i;
            }
        }

        System.out.println(maxn);
        System.out.println(maxi + 1);
    }
}
