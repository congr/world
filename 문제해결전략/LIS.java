import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2016. 8. 18..
 */
public class LIS {
    static int N;
    static int[] cache = new int[501];
    static int[] S = new int[500];

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int tc = Integer.parseInt(sc.next());
        while (tc-- > 0) {
            Arrays.fill(cache, -1);
            Arrays.fill(S, -1);

            // input
            N = Integer.parseInt(sc.next()); // 1이상  ~ 100,000이하
//            int A[] = new int[N];
            for (int i = 0; i < N; i++) {
                S[i] = Integer.parseInt(sc.next());
            }

            int res = lis(0);
            System.out.println(res);
        }

        sc.close();
    }


    static int lis(int start) {
        int ret = cache[start + 1];
        if (ret != -1) return ret;

        cache[start + 1] = 1; // S[start] 는 항상 값이 있으므로 길이 최하는 1
        for (int next = start + 1; next < N; next++) {
            if (start == -1 || S[start] < S[next])
                cache[start + 1] = Math.max(cache[start + 1], lis(next) + 1);
        }
        return cache[start + 1];
    }
}
