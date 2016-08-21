import java.util.Arrays;
import java.util.Scanner;

public class Quantize {
    static final int INF = 987654321;
    static final int MAX = 101;
    static int N;
    static int S;
    static int[] A = new int[MAX];
    static int[] pSum = new int[MAX];
    static int[] pSqSum = new int[MAX];
    static int[][] cache = new int[MAX][11];

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int tc = Integer.parseInt(sc.next());
        while (tc-- > 0) {
            // init
            Arrays.fill(A, -1);
            Arrays.fill(pSum, -1);
            Arrays.fill(pSqSum, -1);
            for (int i = 0; i < MAX; i++)
                Arrays.fill(cache[i], -1);

            // input
            N = Integer.parseInt(sc.next());
            S = Integer.parseInt(sc.next());
            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(sc.next());
            }
            precalc();
            int res = quantize(0, S);
            System.out.println(res);
        }

        sc.close();
    }

    static int quantize(int from, int parts) {
        // 기저 사례 : 모든 숫자를 다 양자화했을 때
        if (from == N)
            return 0;

        // 숫자는 더 남았는데, 양자화 숫자 개수는 다 써서 더이상 묶을 수 없을 때
        if (parts == 0)
            return INF;

        // memoization 된적이 있다면
        if (cache[from][parts] != -1)
            return cache[from][parts];

        cache[from][parts] = INF; // 큰값으로 초기화

        // 조각의 길이를 변화시켜 가며 최소치를 찾는다.
        for (int partSize = 1; from + partSize <= N; partSize++) {
            cache[from][parts] = Math.min(cache[from][parts],
                    minError(from, from + partSize - 1) + quantize(from + partSize, parts - 1));
        }

        return cache[from][parts];
    }

    // A[lo]...A[hi] 구간을 하나의 숫자로 표현할 때 최소 오차 합을 계산한다.
    private static int minError(int lo, int hi) {
        // A[lo]...A[hi] 부분합
        int sum = pSum[hi] - (lo == 0 ? 0 : pSum[lo - 1]);
        int sqSum = pSqSum[hi] - (lo == 0 ? 0 : pSqSum[lo - 1]);

        // 평균을 반올림한 값으로 이 수들을 표현한다
        int m = (int) Math.round((double) sum / (hi - lo + 1));

        // sum(A[i] - m)^2를 전개한 결과를 부분합으로 표현
        int ret = sqSum - 2 * m * sum + m * m * (hi - lo + 1);
        return ret;
    }

    static void precalc() {
        Arrays.sort(A, 0, N); // [0 ... N) fromIndex is inclusive, whereas toIndex is exclusive
        pSum[0] = A[0];
        pSqSum[0] = A[0] * A[0];

        for (int i = 1; i < N; i++) {
            pSum[i] = pSum[i - 1] + A[i];
            pSqSum[i] = pSqSum[i - 1] + A[i] * A[i];
        }
    }
}