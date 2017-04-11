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

        boolean isBeautiful = true;
        boolean bVowel = isVowel(w.charAt(0)); // 모음이면 true
        for (int i = 1; i < w.length(); i++) {
            char c = w.charAt(i);

            if (isVowel(c)) {// 모음
                if (bVowel == true) {
                    isBeautiful = false;
                    break;
                }
                bVowel = true;

            } else {
                if (c == w.charAt(i - 1)) {
                    isBeautiful = false;
                    break;
                }
                bVowel = false;
            }
        }

        System.out.println(isBeautiful ? "Yes" : "No");
    }

    static boolean isVowel(char c) {
        char[] vowel = new char[]{'a', 'e', 'i', 'o', 'u', 'y'};
        for (int i = 0; i < vowel.length; i++) {
            if (c == vowel[i]) return true;
        }
        return false;
    }

    static int solve() {
        int result = 0;

        return result;
    }
}
