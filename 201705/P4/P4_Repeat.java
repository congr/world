import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 5. 19..
 */
public class P4_Repeat {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201705/P4/Set1.in"; // path from root
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
            for (int i = 0; i < N; i++) {
                A[i] = sc.nextInt();
            }

            int s = 0;
            int p = 0;
            for (int i = 0; i < N * 1000; i++) {
                s += A[i % 5];
                //System.out.println(p + " " + s);
                p++;

                if (s > 10) {
                    String test = String.valueOf(s);
                    boolean found = true;
                    for (int j = 1; j < test.length(); j++) {
                        if (test.charAt(j - 1) != test.charAt(j)) {
                            found = false;
                        }
                    }
                    if (found) {
                        //System.out.println("====" + test + " " + (p - 1));
                        int index = p - 1;
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
}
