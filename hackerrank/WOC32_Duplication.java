/**
 * Created by cutececil on 2017. 5. 15..
 */

import java.util.Scanner;

public class WOC32_Duplication {

    static int MAX = 1002;
    static int array[] = new int[MAX];

    public static String duplication(int x) {
        // Complete this function

        return String.valueOf(array[x]);
    }

    public static void makeString() {
        int len = 2;
        while (len < MAX * 2) {
            for (int i = len / 2, j = 0; i < len && i < MAX; i++, j++) {
                array[i] = array[j] == 0 ? 1 : 0;
                if (i >= MAX) break;
            }
            //System.out.println(Arrays.toString(array));
            len *= 2;
            //System.out.println(len);
        }

        //System.out.println(Arrays.toString(array));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        makeString();
        int q = in.nextInt();
        for (int a0 = 0; a0 < q; a0++) {
            int x = in.nextInt();
            String result = duplication(x);
            System.out.println(result);
        }
    }

}
