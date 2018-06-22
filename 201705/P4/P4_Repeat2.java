/**
 * Created by cutececil on 2017. 5. 20..
 */

import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 5. 19..
 */
public class P4_Repeat2 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201705/P4/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = 5;

            int A[] = new int[N];
            int sum = 0;
            for (int i = 0; i < N; i++) {
                A[i] = sc.nextInt();
                sum += A[i];
            }

            System.out.println(Arrays.toString(A));
            for(int i=1; i<=10000;i++){
                if(isKaprekar(i)) System.out.println(i);
            }

            long s = 0;
            BigInteger p = new BigInteger("0");
            for (int i = 0; ; i++) {
                s += (long)A[i % 5];
                //System.out.println(p + " " + s);
                p = p.add(BigInteger.ONE);

                if (s > 10) {
                    String test = String.valueOf(s);
                    boolean found = true;
                    for (int j = 1; j < test.length(); j++) {
                        if (test.charAt(j - 1) != test.charAt(j)) {
                            found = false;
                            break;
                        }
                    }
                    if (found) {
                        BigInteger index = p.subtract(BigInteger.ONE);
                        System.out.println("found " + test + " " + index);
                        String result = index + " " + test.charAt(0) + "(" + test.length() + ")";
                        System.out.println(result);
                        wr.write(result + "\n");
                        break;
                    }
                }
            }

            //int result = 0;
            //System.out.println(result);
            //wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }
    public static boolean isKaprekar(int n){
        int square=n*n, factor=1;
        for(int temp=n;temp>0; temp/=10){
            factor*=10;
        }

        return n==(square/factor)+(square%factor);
    }

}
