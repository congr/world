import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 30..
 */
public class P1_저울추 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201609/P1/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int K = sc.nextInt();

            int allSum = 0;
            int[] A = new int[N + 1];
            boolean[] D = new boolean[K + 1];
            for (int i = 0; i < N; i++) {
                A[i] = sc.nextInt();
                D[A[i]] = true;
                allSum += A[i];
            }

            int a = find(A, D, allSum, N);
            if (a != 0) { // a가 0인경우는 추가 저울추가 필요없음
                allSum += a;
                D[a] = true;
                A[N] = a;
                Arrays.sort(A);
                if (find(A, D, allSum, N+1) != 0) // 추를 추가하고 한번더 돌림
                    a = -1;
            }
            int result = a;

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static int find(int[] A, boolean[] D, int allSum, int N) {
        for (int i = 1; i < D.length; i++) {
            if (D[i]) continue;

            int n = i;
            if (allSum >= i) {
                int mid = -1;
                for (int j = 0; j < N; j++) {
                    if (n>=A[j])
                        mid = j;
                }
                for (int j = mid; j >= 0; j--) {
                    n -= A[j];
                    if (D[n]) {
                        n = 0;
                        break;
                    }
                }
            } else {
                n = allSum - i;
            }

            int add = 0;
            if (n != 0) { // 추 추가가 필요한데 어떤값의 추를 추가할까?
                for (int j = 0; j < N; j++) {
                    if (A[j] + 1 != A[j + 1]) {
                        add = A[j] + 1;
                        return add;
                    }
                }
            }
            D[i] = true;
        }

        return 0;
    }
}
