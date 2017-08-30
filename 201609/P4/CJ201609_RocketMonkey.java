import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 8..
 */

// 4번 로케트 원숭이
public class CJ201609_RocketMonkey {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201609/P4/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int M = sc.nextInt();

            for (int i = 0; i < N; i++) {

            }

            int result = 0;

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }


}
