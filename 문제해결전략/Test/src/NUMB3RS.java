import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2016. 8. 22..
 */
public class NUMB3RS {
    static final int MAXN = 51;
    static int N, D, P, T;
    static double[][] cache = new double[MAXN][101];
    static int[][] connected = new int[MAXN][MAXN];
    static int[] deg = new int[MAXN];

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int tc = Integer.parseInt(sc.next());
        while (tc-- > 0) {

            // input
            N = Integer.parseInt(sc.next()); // 50이하
            D = Integer.parseInt(sc.next()); // day
            P = Integer.parseInt(sc.next()); // 교도소 위치
            for (int i = 0; i < N; i++) {
                int degree = 0;
                for (int j = 0; j < N; j++) {
                    connected[i][j] = Integer.parseInt(sc.next());
                    if (connected[i][j] == 1)
                        degree++;
                }
                deg[i] = degree;
            }

            T = Integer.parseInt(sc.next()); // 확률 계산할 마을수
            for (int i = 0; i < T; i++) {
                initCache();
                int town = Integer.parseInt(sc.next());
                double res = search3(town, D);
                System.out.print(res + " ");
            }
            System.out.println();
        }

        sc.close();
    }

    static void initCache() {
        for (int i = 0; i < MAXN; i++)
            Arrays.fill(cache[i], -1.0);
    }

    static double search3(int here, int days) {
        // base case : 0th day
        if (days == 0) return (here == P ? 1.0 : 0.0); // P - 시작점

        if (cache[here][days] > -0.5) return cache[here][days];

        cache[here][days] = 0.0;
        for (int there = 0; there < N; there++) {
            if (connected[here][there] > 0)
                cache[here][days] += search3(there, days - 1) / deg[there];
        }

        return cache[here][days];
    }

}
