import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 5. 19..
 */
public class P2_Ants {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201705/P2/Set3.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();

            int PA[] = new int[N];
            for (int i = 0; i < N; i++) {
                PA[i] = sc.nextInt();
            }

            Arrays.sort(PA);
            //System.out.println("==========="+T);
            //System.out.println(Arrays.toString(PA));

            long max = 0;
            for (int i = 1; i < N - 1; i++) {
                double a = PA[i] - PA[i - 1];
                double b = PA[i + 1] - PA[i];
                //System.out.println(a + " " + b);
                max = Math.max(max, Math.round((a + b) / 2));
            }

            if (N == 2) {
                if ((PA[1] - PA[0]) % 2 == 1)
                    max = (PA[1] - PA[0]) / 2 + 1;
                else
                    max = (PA[1] - PA[0]) / 2;
                //System.out.println("N is 2");
            } else if (N <= 1) {
                max = 0;
                //System.out.println("N is 1");
            }

            //if (MAX_VALUE == 0)
            //    System.out.println(Arrays.toString(PA));

            long result = max;
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }
}
