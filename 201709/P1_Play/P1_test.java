import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 21..
 */
public class P1_test {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201709/P1_Play/Set11.in"; // path from root
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
            int J = sc.nextInt() - 1;
            String T1 = sc.next();
            String T2 = sc.next();
            
            int n = N1 + N2 + N2 + N1; // 1세대 T
            for (int i = 2; i <= I; i++) { // I세대의 T길이
                n *= 2;
            }
    
            System.out.println("J " + J + " t1 len " + (n/2));
            char[] t1 = T1.toCharArray();
            char[] t2 = T2.toCharArray();
            char[] temp1 = concatArray(t1, t2);
            char[] temp2 = concatArray(t2, t1);
            char[] t = concatArray(temp1, temp2); // 1세대 T
    
            System.out.println(Arrays.toString(t));
            for (int i = 0; i < I; i++) {
                Pair<char[], char[]> pair = gen(t, S);
                t1 = pair.getKey();
                t2 = pair.getValue();
                temp1 = concatArray(t1, t2);
                temp2 = concatArray(t2, t1);
                t = concatArray(temp1, temp2);
                
            }
            
            char ch = 'X';
            if (S == 0 && J < t1.length) ch = t1[J];
            if (S == 1 && J < t2.length) ch = t2[J];
            
            char result = ch;
            System.out.println(result);
            wr.write(result + "\n");
        }
        
        sc.close();
        wr.close();
    }
    
    static Pair<char[], char[]> gen(char[] t, int s) {
        char[] t1 = new char[t.length / 2];
        char[] t2 = new char[t.length / 2];
        
        for (int i = 0, j = 0, k = 0; i < t.length; i ++) {
            if (i % 2 == 0)
                t1[j++] = t[i];
            else
                t2[k++] = t[i];
        }
        
        return new Pair<>(t1, t2);
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
