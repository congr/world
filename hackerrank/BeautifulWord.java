import java.util.Scanner;

/**
 * Created by cutececil on 2017. 4. 11..
 */
// Week of Code 31
// https://www.hackerrank.com/contests/w31/challenges/beautiful-word
public class BeautifulWord {
    public static void main(String[] args) { // class Solution
        Scanner in = new Scanner(System.in);
        String w = in.next();

        System.out.println(isBeautiful(w) ? "Yes" : "No");
    }

    static boolean isBeautiful(String w) {
        boolean bVowel = isVowel(w.charAt(0)); // true if c is vowel
        for (int i = 1; i < w.length(); i++) {
            char c = w.charAt(i);

            if (isVowel(c)) {
                if (bVowel == true) return false;
                bVowel = true;

            } else {
                if (c == w.charAt(i - 1)) return false;
                bVowel = false;
            }
        }
        return true;
    }

    static boolean isVowel(char c) {
        char[] vowel = new char[]{'a', 'e', 'i', 'o', 'u', 'y'};
        for (int i = 0; i < vowel.length; i++) {
            if (c == vowel[i]) return true;
        }
        return false;
    }
}
