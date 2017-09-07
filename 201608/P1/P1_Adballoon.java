import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 23..
 */
// 201608 1번 애드벌룬
public class P1_Adballoon {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201608/P1/Set1.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int p = sc.nextInt();
            int r = sc.nextInt();
            int q = sc.nextInt() - p;

            double A = Math.toDegrees(Math.atan2(p, r)); // Math.atan2(y, x) y길이, x길이
            double B = Math.toDegrees(Math.atan2(q, r));
            double X = (360 - 2 * A - 2 * B) ;
            double len = Math.ceil(2 * Math.PI * r * X / 360) + p + q;

            int result = (int) len;

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }
}
