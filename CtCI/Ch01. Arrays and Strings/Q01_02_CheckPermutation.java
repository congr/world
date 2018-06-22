import java.util.Scanner;

/**
 * Created by cutececil on 2017. 12. 12..
 */
public class Q01_02_CheckPermutation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String A = sc.next();
        String B = sc.next();
        System.out.println(isPermutation_(A, B));
    }

    // my solution
    static boolean isPermutation(String A, String B) {
        if (A.length() != B.length()) return false;

        int[] checkA = new int[26];
        int[] checkB = new int[26];
        for (int i = 0; i < A.length(); i++) {
            int idx = A.charAt(i) - 'a'; // a: 0, b: 1, c: 2 ..
            checkA[idx]++;
        }

        for (int i = 0; i < B.length(); i++) {
            int idx = B.charAt(i) - 'a'; // a: 0, b: 1, c: 2 ..
            checkB[idx]++;

            if (checkA[idx] < checkB[idx]) return false; // optimization
        }

        for (int i = 0; i < checkA.length; i++) {
            if (checkA[i] != checkB[i]) return false;
        }

        return true;
    }

    // book solution : 3rd for statement is not necessary, if we use check[idx]--
    // {QWER!@#$} {!@#$REWQ} two strings are permutation
    static boolean isPermutation_(String A, String B) {
        if (A.length() != B.length()) return false;

        int[] check = new int[128]; // assume ASCII
        for (int i = 0; i < A.length(); i++) {
            int idx = A.charAt(i);  // a:97, A:65
            check[idx]++;
        }

        for (int i = 0; i < B.length(); i++) {
            int idx = B.charAt(i);
            check[idx]--;           // --
            if (check[idx] < 0) return false;
        }

        return true;
    }
}
