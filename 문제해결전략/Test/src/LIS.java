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
            for (int i = 0; i < N; i++) {
                S[i] = Integer.parseInt(sc.next());
            }

            // lis2 각 시작자리부터 끝까지 모든 숫자를 확인.
            // 1 6 2 7 8 9 3 4 5의 경우,
            // {1 2 7 8 9}, {1 2 3 4 5}, --- 0번째 (1)부터 시작한 모든 경우를 검사함
            // {6 7 8 9}, {2 7 8 9} ...
            int res = 0;
            for (int i = 0; i < N; i++) {
                res = Math.max(res, lis2(i));
            }

            // lis3 은 S[-1]부터 시작. 입력받은 수열 앞에 가상의 -1을 추가함 : 마지막 반환 결과에 -1을 빼면 개수는 동일
            // {-1 1 2 7 8 9}, { -1 1 2 3 4 5} --- 등 한번에 다 검사함
            //  int res = lis3(-1) - 1;
            System.out.println(res);
        }

        sc.close();
    }

    static int lis2(int start) {
        if (cache[start] != -1) return cache[start];

        cache[start] = 1;
        for (int next = start + 1; next < N; next++) {
            if (S[start] < S[next])
                cache[start] = Math.max(cache[start], lis2(next) + 1);
        }

        return cache[start];
    }

    static int lis3(int start) {
        int ret = cache[start + 1];
        if (ret != -1) return ret;

        cache[start + 1] = 1; // S[start] 는 항상 값이 있으므로 길이 최하는 1
        for (int next = start + 1; next < N; next++) {
            if (start == -1 || S[start] < S[next])
                cache[start + 1] = Math.max(cache[start + 1], lis3(next) + 1);
        }
        return cache[start + 1];
    }
}
