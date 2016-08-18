import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by cutececil on 2016. 8. 18..
 */
public class LIS {

    public static void main(String[] args) throws Exception {
        String filename = (args.length > 0) ? args[0] : "sample.in";
        Scanner sc = new Scanner(new File(filename));
        String out = filename.replace("in", "out");
        FileWriter wr = new FileWriter(new File(out));

        int tc = sc.nextInt();
        while (tc-- > 0) {

            // input
            int N = sc.nextInt();
            for (int i = 0; i < N; i++) {

            }
        }

        sc.close();
        wr.close();
    }
}
