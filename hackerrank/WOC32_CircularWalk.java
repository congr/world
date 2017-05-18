/**
 * Created by cutececil on 2017. 5. 17..
 */

import javafx.util.Pair;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class WOC32_CircularWalk {
    public static int[] R;
    public static int[] D; // time

    static void bfs(int n, int start, int t) {
        boolean[] discovered = new boolean[n];

        D[start] = 0;
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        queue.add(new Pair(start, 0));
        while (!queue.isEmpty()) {
            Pair here = queue.remove();
            int pos = (int) here.getKey();
            int time = (int) here.getValue();

            //discovered[pos] = true;
            //if (D[pos] < time) continue;
            //D[pos] = time;
            int left = pos, right = pos;

            //System.out.println(pos + " " + " time: " + time + " " + Arrays.toString(D));
            for (int i = R[pos]; i >= 1; i--) {
                left = pos - i;
                right = pos + i;

                if (left < 0) left = n + left;
                if (right >= n) right = right - n;

                if (D[left] > time + 1 || !discovered[left]) {
                    D[left] = time + 1;
                    queue.add(new Pair<>(left, time + 1));
                    discovered[left] = true;
                }

                if (D[right] > time + 1 || !discovered[right]) {
                    D[right] = time + 1;
                    queue.add(new Pair<>(right, time + 1));
                    discovered[right] = true;
                }
            }
        }
    }

    static long circularWalk(int n, int s, int t, int r_0, int g, int seed, int p) {
        R = new int[n];
        R[0] = r_0;
        D = new int[n];
        Arrays.fill(D, 987654321);

        // pre calc
        for (int i = 1; i < n; i++) {
            long temp = (R[i - 1] * (long) g) + seed;
            R[i] = (int) (temp % p);
        }
        //Arrays.fill(R, 2);
        //System.out.println(Arrays.toString(R));

        if (R[s] == 0) return -1;
        if (s == t) return 0;
        if (Math.abs(s - t) <= R[s]) return 1;

        bfs(n, s, t);

        return D[t] == 987654321 ? -1 : D[t];
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int s = in.nextInt();
        int t = in.nextInt();
        int r_0 = in.nextInt();
        int g = in.nextInt();
        int seed = in.nextInt();
        int p = in.nextInt();

        long result = circularWalk(n, s, t, r_0, g, seed, p);
        System.out.println(result);
    }
}
