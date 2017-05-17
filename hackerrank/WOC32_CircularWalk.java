/**
 * Created by cutececil on 2017. 5. 17..
 */

import java.util.Arrays;
import java.util.Scanner;

public class WOC32_CircularWalk {

    static int circularWalk(int n, int s, int t, int r_0, int g, int seed, int p) {
        int[] rArr = new int[n];
        rArr[0] = r_0;
        int[] d = new int[n];

        // pre calc
        for (int i = 1; i < n; i++) {
            rArr[i] = (rArr[i - 1] * g + seed) % p;
        }

        System.out.println(Arrays.toString(rArr));

        int time = 0;
        int inc = 0, des = 0;
        for (int i = s; i < n; i++) {
            if (d[t] > 0) return d[t];

            int rvalue = rArr[i];
            for (int j = 1; j <= rvalue; j++) {
                inc = i + j;
                des = i - j;
                if (inc > n) inc = inc-1-n;
                if (des < 0) des = n + des;
                System.out.println("inc " + inc + "des " + des);
                d[inc] = d[des] = time;
                if (d[t] > 0) return d[t];
            }

            for (int j = inc; j < rvalue; j++) {
                inc = i + j;
                des = i - j;
                if (inc > n) inc = inc - n;
                if (des < 0) des = des + n;
                d[inc] = d[des] = time;
                if (d[t] > 0) return d[t];
            }

            System.out.println(Arrays.toString(d));
            time++;
        }

        return time;
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

        int result = circularWalk(n, s, t, r_0, g, seed, p);
        System.out.println(result);
    }
}
