import java.util.Scanner;

/**
 * Created by cutececil on 2017. 11. 21..
 */
public class KOI_NQueen {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        boolean[] col = new boolean[N];
        boolean[] inc = new boolean[N * 2];
        boolean[] dec = new boolean[N * 2];

        backtrack(0, N, col, inc, dec);
        System.out.println(cnt);

        //solve(1, N, col, inc, dec);
        //System.out.println(ans);
    }

    static int cnt;

    static void backtrack(int row, int N, boolean[] col, boolean[] inc, boolean[] dec) {
        if (row >= N) {
            //System.out.println("row " + row);
            //System.out.println("col" + Arrays.toString(col));
            //System.out.println("inc" + Arrays.toString(inc));
            //System.out.println("dec" + Arrays.toString(dec));
            cnt++;
            return;
        }

        for (int i = 0; i < N; i++) { // col
            if (!col[i] && !inc[row + i] && !dec[row - i + N - 1]) {
                col[i] = inc[row + i] = dec[row - i + N - 1] = true;
                backtrack(row + 1, N, col, inc, dec); // !!! row 를 진행 시켜야 함, i는 col이고 for문으로 제어하고 있음
                col[i] = inc[row + i] = dec[row - i + N - 1] = false;
            }
        }
    }

    // 책에 나온 솔루션
    static int ans;

    static void solve(int r, int n, boolean[] col, boolean[] inc, boolean[] dec) {
        if (r > n) {
            ans++;
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (!col[i] && !inc[r + i] && !dec[n + r - i + 1]) {
                col[i] = inc[r + i] = dec[n + (r - i) + 1] = true;
                solve(r + 1, n, col, inc, dec);
                col[i] = inc[r + i] = dec[n + (r - i) + 1] = false;
            }
        }
    }
}
