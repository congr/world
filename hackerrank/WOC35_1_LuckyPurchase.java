import java.util.Scanner;

/**
 * Created by cutececil on 2017. 11. 13..
 */
public class WOC35_1_LuckyPurchase {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        int best = 987654321;
        String result = "-1";
        for (int a0 = 0; a0 < m; a0++) {
            String s = in.next();
            int n = in.nextInt();
            int len = getLuckyLen(n);
            if (best > len) {
                best = len;
                result = s;
            }
            //System.out.println(len);
        }
        
        if (best == 987654321) System.out.println(-1);
        else System.out.println(result);
        in.close();
    }
    
    public static int getLuckyLen(int n) {
        int cnt4 = 0, cnt7 = 0;
        String s = n + "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '4') cnt4++;
            else if (c == '7') cnt7++;
            else return 987654321;
        }
        
        if (cnt4 == cnt7) return cnt4;
        else return 987654321;
    }
}
