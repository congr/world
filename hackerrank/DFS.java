/**
 * Created by cutececil on 2017. 3. 18..
 */

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class DFS {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int grid[][] = new int[n][m];
        for (int grid_i = 0; grid_i < n; grid_i++) {
            for (int grid_j = 0; grid_j < m; grid_j++) {
                grid[grid_i][grid_j] = in.nextInt();
            }
        }

        class Sol {
            int dx[] = {-1, -1, -1, 0, 0, +1, +1, +1}; // 9칸이 connected cells
            int dy[] = {-1, 0, +1, -1, +1, -1, 0, +1};

            boolean insideGrid(int y, int x) {
                if ((x >= 0 && y >= 0) && (x < m && y < n))
                    return true;
                else
                    return false;
            }

            int dfs(int y, int x, int mark, int cnt) {
                cnt++;
                grid[y][x] = mark; // 1인 곳을 2,2,2, 3,3,3,3, 으로 변경해서 다음에 1인 곳만 다시 dfs할 수 있도록..

                // { 0, 1, 2,
                //   3, *, 4,
                //   5, 6, 7}
                for (int k = 0; k < dx.length; k++) { // gird[y][x]를 중심으로 k {0: 대각선왼위, 1:바로위, 2:오른위, 3:왼옆, 4:오른옆 5:왼아래, 6: 아래, 7:오른아래}
                    int nextX = x + dx[k], nextY = y + dy[k];
                    if (insideGrid(nextY, nextX) && grid[nextY][nextX] == 1)
                        cnt = dfs(nextY, nextX, mark, cnt);
                }
                return cnt;
            }

            int dfsAll() {
                int max = 0;
                int mark = 2;
                for (int i = 0; i < n; i++) { // y
                    for (int j = 0; j < m; j++) { // x
                        if (grid[i][j] == 1)
                            max = Math.max(max, dfs(i, j, mark++, 0));
                    }
                }
                return max;
            }
        }

        System.out.println(new Sol().dfsAll());
    }
}
