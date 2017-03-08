import java.util.Arrays;
import java.util.Scanner;

public class JumpGame {
    static int N;
    static int[][] board = new int[100][100];

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int C = Integer.parseInt(sc.next());
        while (C-- > 0) {
            N = Integer.parseInt(sc.next());

            for (int i = 0; i < N; i++) {
                Arrays.fill(cache[i], -1);
                for (int j = 0; j < N; j++)
                    board[i][j] = Integer.parseInt(sc.next());
            }

            // String ret = jump(0,0) ? "YES":"NO"; // without memoization
            String ret = (jump2(0, 0) != 0 ? "YES" : "NO"); // jump2내에 return시 덧셈을 하면 jump2(0,0)>0으로 하면 안됨 왜? 덧셈하다보면 오버플로우가 발생해서 0보다 작은 수가 있음

            System.out.println(ret);
        }
        sc.close();
    }

    static boolean jump(int y, int x) {
        if (y >= N || x >= N)
            return false;

        if (y == N - 1 && x == N - 1)
            return true;

        int jumpSize = board[y][x];
        return jump(y + jumpSize, x) || jump(y, x + jumpSize);
    }

    static int cache[][] = new int[100][100];

    static int jump2(int y, int x) {
        if (y >= N || x >= N)
            return 0;

        if (y == N - 1 && x == N - 1)
            return 1;

        if (cache[y][x] != -1)
            return cache[y][x];

        int jumpSize = board[y][x];

        return cache[y][x] = jump2(y + jumpSize, x) | jump2(y, x + jumpSize); // '|' 대신 덧셈을 하면 오버플로우 발생
    }
}
