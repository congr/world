import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 5. 20..
 */
public class P3_RomeYang1 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201705/P3/Set1.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // pre calc
        int va[] = new int[]{1, 4, 5, 9, 10};
        int wa[] = new int[]{1, 3, 2, 3, 2};

        ArrayList<Integer>[] table = new ArrayList[11];
        for (int i = 1; i < table.length; i++) {
            table[i] = new ArrayList();
            if (i == 1)
                table[i].add(1);
            else if (i == 2)
                table[i].add(2);
            else if (i == 3)
                table[i].add(3);
            else if (i == 4) {
                table[i].add(3);
                table[i].add(4);
            } else if (i == 5) {
                table[i].add(2);
                table[i].add(4);
                table[i].add(5);
            } else if (i == 6) {
                table[i].add(3);
                table[i].add(5);
                table[i].add(6);
            } else if (i == 7) {
                table[i].add(4);
                table[i].add(6);
                table[i].add(7);
            } else if (i == 8) {
                table[i].add(5);
                table[i].add(6);
                table[i].add(7);
                table[i].add(8);
            } else if (i == 9) {
                table[i].add(3);
                table[i].add(5);
                table[i].add(6);
                table[i].add(7);
                table[i].add(8);
                table[i].add(9);
            } else if (i == 10) {
                table[i].add(2);
                table[i].add(4);
                table[i].add(6);
                table[i].add(7);
                table[i].add(8);
                table[i].add(9);
                table[i].add(10);
            }
        }


        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int K = sc.nextInt();

            //System.out.println("[N, K] " + N + " " + K);
            boolean found = false;
            for (Integer a : table[N]) {
                if (a == K) {
                    found = true;
                }
            }

            String result = "X";
            if (found) result = "O";

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }
}
