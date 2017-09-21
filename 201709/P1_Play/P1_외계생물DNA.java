import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 21..
 */
public class P1_외계생물DNA {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201709/P1_Play/Set2.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);
        
        // logic starts here
        int TC = sc.nextInt();
        while (TC-- > 0) {
            int N1 = sc.nextInt();
            int N2 = sc.nextInt();
            int I = sc.nextInt();
            int S = sc.nextInt();
            int J = sc.nextInt() - 1;
            String T1 = sc.next();
            String T2 = sc.next();
            
            long n = N1 + N2 + N2 + N1; // 1세대 T
            for (int i = 2; i <= I; i++) { // I세대의 T길이
                n *= 2;
            }
            
            //System.out.println("J " + J + " t1 len " + (n / 2));
            char ch = 'X';
            if (n / 2 > J) {
                // 1세대 T는 만들어야 함 (0세대는 T1, T2길이가 다름 1세대부터 두 길이가 같음)
                char[] t1 = T1.toCharArray();
                char[] t2 = T2.toCharArray();
                char[] temp1 = concatArray(t1, t2);
                char[] temp2 = concatArray(t2, t1);
                char[] t = concatArray(temp1, temp2); // 1세대 T
                
                //System.out.println(Arrays.toString(t));
                int s = S;
                long l = n / 2, j = J; // l: 1세대 T1,T2길이
                long k = 0;
                for (int i = I; i > 0; i--) {
                    //System.out.println("n " + n + " l " + l);
                    if (s == 0) k = j * 2;
                    else k = j * 2 + 1; // t index
                    
                    l = n / 4; // 이전 세대 t1, t2길이
                    n = n / 2; // 이전 세대 t길이
                    
                    j = k % l; // t1 or t2 index
                    int ss = (int) (k / l); // 0,1,2,3
                    if (ss == 0 || ss == 3) s = 0; // t1 남자
                    else if (ss == 1 || ss == 2) s = 1; // t2 여자
                }
                
                ch = t[(int) k];
            }
            
            char result = ch;
            System.out.println(result);
            wr.write(result + "\n");
        }
        
        sc.close();
        wr.close();
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
