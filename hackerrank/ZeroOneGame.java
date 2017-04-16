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

            boolean isAWin = false;
            boolean more = false;
            int[] sequence = new int[n];
            Arrays.fill(sequence, -1);

            for (int i = 0, j = 2; i < n; i++) {
                sequence[i] = in.nextInt();

                if (i < 2) continue;

                more = false;
                if (match(sequence, i)) {
                    if (sequence[i - 1] == 1) more = true; // .. 0 0 1 0 -> .. 0 0 0 : should check one more after removing 1
                    sequence[i - 1] = sequence[i - 2];
                    isAWin ^= true;

                    if (i >= 3 && more && sequence[i - 3] == 0) {
                        sequence[i - 2] = sequence[i - 3];
                        if (match(sequence, i)) isAWin ^= true;
                    }
                }
            }

            System.out.println(isAWin ? "Alice" : "Bob");
        }
    }

    // true if pattern is 0 1 0 or 0 0 0
    static boolean match(int[] seq, int pos) {
        if (seq[pos] == 0 && seq[pos - 2] == 0) return true;
        else return false;
    }
}
