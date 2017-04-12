import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by cutececil on 2017. 4. 12..
 */
// https://www.hackerrank.com/contests/w31/challenges/zero-one-game
public class ZeroOneGame {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int g = in.nextInt();
        for (int a0 = 0; a0 < g; a0++) {
            int n = in.nextInt();
            int[] sequence = new int[n];
            for (int sequence_i = 0; sequence_i < n; sequence_i++) {
                sequence[sequence_i] = in.nextInt();
            }
            // If Alice wins, print 'Alice' on a new line; otherwise, print 'Bob'
            System.out.println(solve(sequence) ? "Alice" : "Bob"); //
        }
    }

    static boolean solve(int[] sequence) { // true if Alice wins
        List seq = Arrays.stream(sequence).boxed().collect(Collectors.toList());

        boolean isA = false;
        int p = 987654321;
        while (seq.size() >= 3) {
            p = kmpMatcher(seq, new int[]{0, 0, 0});
            if (p == -1) p = kmpMatcher(seq, new int[]{0, 1, 0});

            if (p == -1) break;

            seq.remove(p + 1);
            isA = !isA;
        }

        return isA;
    }

    public static int kmpMatcher(List s, int[] pattern) {
        int m = pattern.length;
        if (m == 0)
            return 0;
        int[] p = prefixFunction(pattern);
        for (int i = 0, k = 0; i < s.size(); i++)
            for (; ; k = p[k - 1]) {
                if (pattern[k] == (int) s.get(i)) {
                    if (++k == m)
                        return i + 1 - m;
                    break;
                }
                if (k == 0)
                    break;
            }
        return -1;
    }

    public static int[] prefixFunction(int[] s) {
        int[] p = new int[s.length];
        int k = 0;
        for (int i = 1; i < s.length; i++) {
            while (k > 0 && s[k] != s[i])
                k = p[k - 1];
            if (s[k] == s[i])
                ++k;
            p[i] = k;
        }
        return p;
    }
}
