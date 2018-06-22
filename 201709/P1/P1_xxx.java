import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 18..
 */
public class P1_xxx {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201709/P1/Set2.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N1 = sc.nextInt();
            int N2 = sc.nextInt();
            int I = sc.nextInt();
            int S = sc.nextInt();
            int J = sc.nextInt();

            String T1 = sc.next();
            String T2 = sc.next();

            char ch = 'X';

            int N = 0;
            for (int i = 0; i < I; i++) {
                N = N1 + N2;
                //System.out.println(N + " " + N1 + " " + N2 + " j "+ J);

                N *= 2;
                N1 = N / 2;
                N2 = N / 2;
            }

            N /= 2;
            char[] D = new char[J + 1];
            char[] t1 = T1.toCharArray();
            char[] t2 = T2.toCharArray();
            //System.out.println("\nN " + N + " J " + J);
            if (N >= J) {
                for (int i = 0; i < I; i++) {

                    //System.out.println(Arrays.toString(t1));
                    //System.out.println(Arrays.toString(t2));
                    Pair<char[], char[]> pair = gen(t1, t2, I, J);
                    t1 = pair.getKey();
                    t2 = pair.getValue();

                    //System.out.println("T1 len" + t1.length);
                    //System.out.println("T2 len" + t2.length);
                    //System.out.println("N len" + N * I);
                }


                if (S == 0 && J - 1 >= 0 && t1.length > J - 1) {// male
                    ch = t1[J - 1];
                } else if (S == 1 && J - 1 >= 0 && t2.length > J - 1) { // fe
                    ch = t2[J - 1];
                }
            }
            char result = ch;
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    private static Pair<char[], char[]> gen(char[] t1, char[] t2, int sex, int end) {
        char[] t12 = concatArray(t1, t2);
        char[] t21 = concatArray(t2, t1);
        char[] t1221 = concatArray(t12, t21);
        //System.out.println(Arrays.toString(t1221));

        char[] female = new char[t1221.length / 2];
        char[] male = new char[t1221.length / 2];
        for (int i = 0, j = 0, k = 0; i < t1221.length /*&& j<=end*/; i++) {
            if (i % 2 == 1) {// 여자
                female[j++] = t1221[i];

            } else {
                male[k++] = t1221[i];

            }
        }

        return new Pair<>(male, female);
    }

    static public char[] concatArray(char[] a, char[] b) {
        int aLen = a.length;
        int bLen = b.length;
        char[] c = new char[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

}
