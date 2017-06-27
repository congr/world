/**
 * Created by cutececil on 2017. 5. 17..
 */

import javafx.util.Pair;

import java.math.BigInteger;
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
        discovered[start] = true;
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(start, 0));
        while (!queue.isEmpty()) {
            Pair here = queue.remove();
            int pos = (int) here.getKey();
            int time = (int) here.getValue();
            
            //System.out.println(pos + " " + " time: " + time + " " + Arrays.toString(D));
            if (pos == t) return; // time이 항상 작은게 먼저 poll 되므로 target을 만나면 리턴해도 됨
            
            int left, right;
            
            for (int i = R[pos]; i >= 1; i--) {
                left = pos - i;
                right = pos + i;
                
                if (left < 0) left = n + left;
                if (right >= n) right = right - n;
                if (right < 0) right = n + right;// 없어도됨
                if (left >= n) left = left - n; // 없어도됨
                
                if (/*D[left] > time + 1 || */!discovered[left]) { // D값이 더 큰 경우가 올수 없다 discovered는 queue에 한번 들어간 적이 있다는 뜻이고 그렇다면 time이 더 작다는 뜻
                    D[left] = time + 1;
                    queue.add(new Pair<>(left, time + 1));
                    discovered[left] = true;
                }
                
                if (/*D[right] > time + 1 || */!discovered[right]) {
                    D[right] = time + 1;
                    queue.add(new Pair<>(right, time + 1));
                    discovered[right] = true;
                }
            }
        }
    }
    
    private static long circularWalk(int n, int s, int t, int r_0, int g, int seed, int p) {
        R = new int[n];
        R[0] = r_0;
        D = new int[n];
        Arrays.fill(D, -1); // -1 초기화 필요함
        
        // pre calc
        for (int i = 1; i < n; i++) {
            BigInteger bt = new BigInteger(String.valueOf(R[i - 1]));
            bt = bt.multiply(BigInteger.valueOf(g));
            bt = bt.add(BigInteger.valueOf(seed));
            bt = bt.mod(BigInteger.valueOf(p));
            int temp = bt.intValue();
            R[i] = temp;
            //long temp = ((long) R[i - 1] * (long) g) + seed;
            //R[i] = (int) (temp % (long) p);
        }
        
        //System.out.println(Arrays.toString(R));
        
        //if (R[s] == 0) return -1; // !!! 이거 때문에 개고생. s가 t일수도 있어서 답이 0일 수도 있음
        if (s == t) return 0;
        if (Math.abs(s - t) <= R[s]) return 1;
        
        bfs(n, s, t);
        return D[t];
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
