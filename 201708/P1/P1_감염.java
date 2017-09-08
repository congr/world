import java.io.File;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 17..
 */
// 2017 08 2차 1번 감염
// bfs로 단순 코딩, 그래도 시간이 엄청 걸린 문제
public class P1_감염 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201708/P1/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();

            Queue<Cell> q = new LinkedList<>();
            LinkedHashMap<String, Integer> map = new LinkedHashMap<>(); // 좌표가 큰 값이라서 좌표 상태를 map에 표기

            for (int i = 0; i < N; i++) {
                int y = sc.nextInt();
                int x = sc.nextInt();
                Cell cell = new Cell(x, y, 2, 0);
                map.put(x + "," + y, 2);
                q.add(cell);
            }

            int result = infect(map, q);

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    // cell 에 매겨진 카운트가 2가 되면 감염을 일으키므로 q에 넣고, q에 더이상 아무것도 없을 때까지 반복
    static int infect(LinkedHashMap<String, Integer> map, Queue<Cell> q) {
        int dx[] = new int[]{-1, 0, 1, 0};
        int dy[] = new int[]{0, -1, 0, 1};

        int lastTime = 0;
        while (!q.isEmpty()) {
            Cell cell = q.poll();
            lastTime = cell.t;

            for (int i = 0; i < 4; i++) {
                int x = cell.x + dx[i];
                int y = cell.y + dy[i];
                //if (insideGrid(y, x, M, M) == false) continue; // 안해도 되네

                int t = cell.t;
                int c = map.getOrDefault(x + "," + y, 0);
                map.put(x + "," + y, c + 1);

                if (c == 1) q.add(new Cell(x, y, 2, t + 1)); // 이번에 감염 당첨
            }
        }

        return lastTime;
    }

    //static boolean insideGrid(int y, int x, int Y, int X) {
    //    if ((x >= 0 && y >= 0) && (x < X && y < Y)) return true;
    //    else return false;
    //}

    static class Cell {
        int x, y, t, c;

        Cell(int x, int y, int c, int t) {
            this.x = x;
            this.y = y;
            this.c = c;
            this.t = t;
        }
    }
}
