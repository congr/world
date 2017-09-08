import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 8. 18..
 */
public class P1_Queue {
    public static int MAX = 1000000000;

    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201708/P1/Set3.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();

            Queue<Bomb> q = new LinkedList<>();
            LinkedHashMap<String, Bomb> map = new LinkedHashMap<>();
            LinkedHashMap<String, Integer> puk = new LinkedHashMap<>();
            for (int i = 0; i < N; i++) {
                int y = sc.nextInt() - 1;
                int x = sc.nextInt() - 1;
                q.add(new Bomb(y, x, 0, 0));

                String key = y + "," + x;
                puk.put(y + "," + x, 987654321);
                //System.out.println("puk " + key + " 9876");
            }

            int dx[] = new int[]{-1, 0, 1, 0};
            int dy[] = new int[]{0, -1, 0, 1};
            ArrayList<Bomb> toRemove = new ArrayList<>();

            int time = 0;
            for (; ; time++) {
                //System.out.println("time " + time + " =============");
                while (!q.isEmpty()) {
                    Bomb bomb = q.remove();
                    //System.out.println(bomb);

                    int px = 0, py = 0;
                    for (int i = 0; i < 4; i++) { // 좌 위 우 하
                        py = bomb.y + dy[i];
                        px = bomb.x + dx[i];
                        if (insideGrid(py, px, MAX, MAX) == false)
                            continue;

                        String key = py + "," + px;
                        if (!puk.containsKey(key)) { // 이미 폭탄이 아니면, cnt ++
                            Bomb mb = map.getOrDefault(key, new Bomb(py, px, time + 1, 0));
                            mb.cnt++;
                            map.put(key, mb);
                        }
                    }
                }

                //if (map.size() == 0) break;
                for (Map.Entry<String, Bomb> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Bomb b = entry.getValue();
                    if (b.cnt >= 2) {
                        toRemove.add(b);
                        //move(b.y, b.x, puk, map);
                        q.add(b);
                    }
                }

                if (toRemove.size() == 0) break;
                while (toRemove.size() > 0) {
                    Bomb b = toRemove.get(0);
                    move(b, puk, map);
                    toRemove.remove(b);
                }
            }

            int result = time;
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static void move(Bomb b, LinkedHashMap puk, LinkedHashMap map) {
        String bkey = b.y + "," + b.x;
        puk.put(bkey, 987654321);
        map.remove(bkey);
        // System.out.println("move " + bkey + " 9876");
    }

    static boolean insideGrid(int y, int x, int Y, int X) {
        if ((x >= 0 && y >= 0) && (x < X && y < Y)) return true;
        else return false;
    }

    static class Bomb {
        int x, y, time, cnt;

        Bomb(int y, int x, int time, int cnt) {
            this.x = x;
            this.y = y;
            this.time = time;
            this.cnt = cnt;
        }

        @Override
        public String toString() {
            return "y " + y + " x " + x + " time " + time + " cnt " + cnt;
        }
    }
}
