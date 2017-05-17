/**
 * Created by cutececil on 2017. 5. 16..
 */

import java.util.Arrays;
import java.util.Scanner;

public class WOC32_FightTheMonsters {

    static int getMaxMonsters(int n, int hit, int t, int[] h) {
        Arrays.sort(h);

        int cnt = 0;
        for (int i = 0, j=0; i < t; i++) {
            //if (h[i] <= 0)
            //    continue;

            if (h[j] <= hit) {
                h[j] -= hit;
                cnt++;
                j++;
            } else if (h[j] > hit) {
                h[j] -= hit;
            }
        }

        return cnt;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int hit = in.nextInt();
        int t = in.nextInt();
        int[] h = new int[n];
        for (int h_i = 0; h_i < n; h_i++) {
            h[h_i] = in.nextInt();
        }
        int result = getMaxMonsters(n, hit, t, h);
        System.out.println(result);
    }
}