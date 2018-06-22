/**
 * Created by cutececil on 2017. 12. 14..
 */

// Palindrome Permutation : Given a string, write a function to check if it is a permutation of a palindrome
// input : Tact Coa
// output: taco cat, atco cta
public class Q01_04_PalindromePermutation {
    public static void main(String[] args) {
        String S = "Tact Coa";
        String D = "tacocat";

        System.out.println(isPalindromePermutation(S.toLowerCase(), D));
    }

    static boolean isPalindromePermutation(String S, String D) {
        if (S.length() != D.length()) return false;

        String s = S.replaceAll(" ", "");
        String d = D.replaceAll(" ", "");

        char[] SA = s.toCharArray();
        char[] DA = d.toCharArray();

        // char matching count
        int[] check = new int[26];
        for (int i = 0; i < SA.length; i++) {
            check[SA[i] - 'a']++;
        }

        for (int i = 0; i < SA.length; i++) {
            check[DA[i] - 'a']--;
            if (check[DA[i] - 'a'] < 0) return false;
        }

        // check if it's palindrome
        //for (int i = 0, j = DA.length - 1, l = DA.length / 2; i < l; i++, j--) {
        //    if (DA[i] != DA[j]) return false;
        //}

        // check space position
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == ' ' && D.charAt(i) != ' ') return false;
        }


        //System.out.println(Arrays.toString(SA));

        return true;
    }
}
