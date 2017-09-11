import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 5. 19..
 */
public class P1_ {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201705/P1/Set1.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        //Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int D = sc.nextInt();
            String first = sc.next();
            String second = sc.next();

            //String result = anagram(first, second, D) ? "O" : "X";
            String result = test2(first, second, D)/* && test2(second, first, D)*/ ? "O" : "X";

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static boolean test2(String first, String second, int D) {
        //System.out.println(first);
        //System.out.println(second + ", " + D);
        if (D == 0)
            return first.equals(second);
        if (first.length() != second.length()) return false;

        boolean[] used = new boolean[second.length()];

        for (int i = 0; i < first.length(); i++) {
            char f = first.charAt(i);

            boolean found = false;

            int j = i - D;
            if (j < 0) j = 0;
            int m = i + D;
            if (m >= second.length()) m = second.length()-1;

            for (; j <= m; j++) {
                char s = second.charAt(j);
                if (f == s && used[j] == false) {
                    //System.out.println(i+ "found at " + j);
                    used[j] = true;
                    found = true;
                    break;
                }
            }

            if (found == false) return false;

        }

        return true;
    }

    /*static boolean test(String first, String second, int D) {
        for (int i = 0; i < first.length(); i++) {
            char f = first.charAt(i);
            int spos = second.indexOf(f, 0);

            if (Math.abs(i - spos) <= D) ;
            else {
                while (spos < second.length()) {
                    spos = second.indexOf(f, spos + 1);
                    if (spos < 0) return false;
                    if (Math.abs(i - spos) <= D) ;
                }
            }
        }

        return true;
    }*/
}
