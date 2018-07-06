public class LPS {

    static public String longestPalindrome(String s) {
        int longestLen = 0, start = 0, end = 0;

        for (int i = 0; i < s.length() - 1; i++) {
            int[] odd = expand(s, i, i);
            int[] even = expand(s, i, i + 1);

            int len1 = odd[1] - odd[0];
            int len2 = even[1] - even[0];
            int len = Math.max(len1, len2);
            if (longestLen < len) {
                longestLen = len;

                if (len1 == longestLen) {
                    start = odd[0];
                    end = odd[1];
                } else {
                    start = even[0];
                    end = even[1];
                }
            }
        }

        return s.substring(start, end + 1);
    }

    static int[] expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }

        return new int[]{left+1, right-1};
    }

    public static void main(String[] args) {
        String str = longestPalindrome("babad");
        System.out.println(str);
    }
}
