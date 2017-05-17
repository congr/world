/**
 * Created by cutececil on 2017. 5. 17..
 */

import java.util.Arrays;
import java.util.Scanner;

public class WOC32_CircularWalk {
    public static int[] R;
    public static int[] D;
    
    static void set(int n, int pos, int time, int r, int t) {
        if (D[pos] <= time) return;
        
        D[pos] = time;
        if (R[pos] == 0 || pos == t) return;
        
        int left = pos, right = pos;
        
        System.out.println(pos + " " + Arrays.toString(D));
        for (int i = 1; i <= r; i++) {
            left = pos - i;
            right = pos + i;
            
            if (left < 0) left = n + left;
            if (right >= n) right = right - n + 1;
            
            set(n, right, time + 1, R[right], t);
            
            if (left != right)
                set(n, left, time + 1, R[left], t);
        }
    }
    
    static int circularWalk(int n, int s, int t, int r_0, int g, int seed, int p) {
        R = new int[n];
        R[0] = r_0;
        D = new int[n];
        Arrays.fill(D, 987654321);
        
        // pre calc
        for (int i = 1; i < n; i++) {
            R[i] = (R[i - 1] * g + seed) % p;
        }
        if (R[s] == 0) return -1;
        if (s == t) return 0;
        if (Math.abs(s - t) <= R[s]) return 1;
        
        System.out.println(Arrays.toString(R));
        
        set(n, s, 0, R[s], t);
        
        System.out.println(Arrays.toString(D));
        
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
        
        int result = circularWalk(n, s, t, r_0, g, seed, p);
        System.out.println(result);
    }
}
