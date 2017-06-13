import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 13..
 */
public class WOC33_2_PatternCount {
    static int patternCount(String s) {
        char[] a = s.toCharArray();
        int n = a.length;
        int cnt = 0;
        for (int i = 0, j = -1; i < n; i++) {
            if (a[i] == '1' && i + 1 < n && a[i + 1] == '0') { // start
                if (j >= 0) {
                    cnt++;
                }
                j = i;
            } else if (a[i] == '1' && i > 0 && a[i - 1] == '0') { // end
                if (j >= 0) {
                    cnt++;
                    j = i; // another starting point
                }
            } else if (a[i] > '1') {
                j = -1;
            }
        }
        return cnt;
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for (int a0 = 0; a0 < q; a0++) {
            String s = in.next();
            int result = patternCount(s);
            System.out.println(result);
        }
    }
}

