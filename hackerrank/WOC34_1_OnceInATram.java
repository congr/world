import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 7. 17..
 */
public class WOC34_1_OnceInATram {
    static String onceInATram(int x) {
        String s = String.valueOf(x);
        int[] A = toIntArray(s);
        //System.out.println(Arrays.toString(A));
        
        while (true) {
            String result = handle(A);
            if (result != "") return result;
            A = increase(A);
        }
        
        //if (s2.length() > 3) {
        //    int temp = Integer.parseInt(s1) * 1000 + Integer.parseInt(s2);
        //    int s2pos = s2.length() - 3;
        //    String s11 = String.format("%03d", temp);
        //    s1 = s11.substring(0, 3);
        //    s2 = s2.substring(s2pos, s2pos + 3);
        //}
        //return s1 + s2;
    }
    
    static int[] increase(int[] A) {
        if (A[2] < 9) A[2] += 1;
        else {
            A[2] = 0;
            if (A[1] < 9) A[1] += 1;
            else {
                A[1] = 0;
                A[0] += 1;
            }
        }
        A[3] = 0;
        A[4] = 0;
        A[5] = 0;
        //System.out.println(Arrays.toString(A));
        
        return A;
    }
    
    static String handle(int[] A) {
        int lucky = getLucky(A);
        
        String s1 = "" + A[0] + A[1] + A[2];
        String s2 = "" + A[3] + A[4] + A[5];
        int m = Integer.parseInt(s2);
        while (m <= 999) {
            m++;
            s2 = String.format("%03d", m);
            int a = sumString(s2);
            if (lucky == a)
                return s1 + s2;
        }
        return "";
    }
    
    static int getLucky(int[] A) {
        int lucky = 0;
        for (int i = 0; i < 3; i++) {
            lucky += A[i];
        }
        return lucky;
    }
    
    // string 를 int[] 로 리턴, toCharArray와 동일
    static int[] toIntArray(String s) {
        int[] A = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            A[i] = s.charAt(i) - 48;
        }
        return A;
    }
    
    static int sumString(String s) {
        int[] A = new int[s.length()];
        int sum = 0;
        
        for (int i = s.length() - 3; i < s.length(); i++) {
            A[i] = s.charAt(i) - 48;
            sum += A[i];
        }
        return sum;
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        String result = onceInATram(x);
        System.out.println(result);
    }
}
