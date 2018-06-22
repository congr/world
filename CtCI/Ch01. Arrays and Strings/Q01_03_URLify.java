/**
 * Created by cutececil on 2017. 12. 13..
 */

// {abc d e    } - at then end, there should be enough space to replace in place
// {abc%20d%20e}
public class Q01_03_URLify {
    public static void main(String[] args) {
        // String S = "abc de   f "; // there should be enough space at the end
        String S = "abc de   f        "; // like this, so we can replace it with %20 in place

        // last character index which is not space
        int lastIndex = getLastIndex(S);

        // get space count
        int spCnt = 0;
        for (int i = lastIndex; i >= 0; i--) {
            if (S.charAt(i) == ' ') spCnt++;
        }

        // replace space with %20 in place
        int total = lastIndex + spCnt * 2;
        char[] C = S.toCharArray();
        for (int i = lastIndex, j = total; j >= 0; i--, j--) {
            if (S.charAt(i) == ' ') {
                C[j--] = '0'; // assign '0' and then j-- works
                C[j--] = '2';
                C[j] = '%';
            } else
                C[j] = S.charAt(i);
        }
        System.out.println(charArrayToString(C));
    }

    static int getLastIndex(String S) {
        for (int i = S.length() - 1; i >= 0; i--) {
            if (S.charAt(i) == ' ') ;
            else return i;
        }
        return -1; // all characters are space
    }

    public static String charArrayToString(char[] array) {
        StringBuilder buffer = new StringBuilder(array.length);
        for (char c : array) {
            if (c == 0) {
                break;
            }
            buffer.append(c);
        }
        return buffer.toString();
    }
}
