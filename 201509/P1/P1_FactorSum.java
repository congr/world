import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 25..
 */
public class P1_FactorSum {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201509/P1/problem_1_large.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int A = sc.nextInt();
            int B = sc.nextInt();
            int cnt = 0;
            for (int i = A; i <= B; i++) {
                int f = (factor(i));
                if (f >= i) {
                    cnt++;
                }
            }
            int result = cnt;

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    // 소인수 분해 합
    public static int factor(int num) {
        int sum = 1; // 소인수 분해하면 항상 1이 포함

        for (int i = 2; i * i <= num; i++) { // i는 나누는 수
            if ((num % i) == 0) { // 나누어 진다면
                sum += num / i;
                if (i * i != num) // 1 2 3 6 일때 6은 더하지 않도록
                    sum += i;
            }
        }

        return sum;
    }
}
