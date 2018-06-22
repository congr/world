import java.util.Scanner;

/**
 * Created by cutececil on 2017. 12. 11..
 */
public class Q01_01_Is_Unique {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String S = sc.next();
        System.out.println(isUniqueWithBitManipulation(S));
    }

    // https://www.ascii-code.com/
    // standard ASCII characters(0-127) and Extended ASCII characters(128-255).
    static boolean isUnique(String S) {
        if (S.length() > 128) return false; // if it's longer then 128, it must have a dup

        boolean[] checker = new boolean[128];
        for (int i = 0; i < S.length(); i++) { // !!! S.length(), not checker.length()
            int idx = S.charAt(i);
            if (checker[idx]) return false; // already found
            checker[idx] = true;
        }

        return true;
    }

    // Buf all the characters are lowercase, we can save memory. 26 characters -> bit
    static boolean isUniqueWithBitManipulation(String S) {
        int checker = 0;

        for (int i = 0; i < S.length(); i++) {
            int idx = S.charAt(i) - 'a'; // if S is 'pear', then idx is 15, 4, 0, 17
            //System.out.println(idx);
            if ((checker & (1 << idx)) > 0) return false; // 1 << val is a 1 shifted left val times
            checker |= (1 << idx);
        }

        return true;
    }

    /* Java integer is of size 32 and Number of lower case alphabets is 26
    0 & 0 -> 0
    0 & 1 -> 0
    1 & 0 -> 0
    1 & 1 -> 1

    checker = 0000 0000 0000 0000 0000 1000 1000 0010   &
    'b'     = 0000 0000 0000 0000 0000 0000 0000 0010
    -------------------------------------------------------
    result  = 0000 0000 0000 0000 0000 0000 0000 0010
    */
}
