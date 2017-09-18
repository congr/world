import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 18..
 */
// 부분문자열이 짝수단어가 되는 총개수
// 입력 최대 50,000
// appall -> pp, appa, ll, appall 총 4개
// N^2으로 Large set이 1분30초 정도로 풀린다. 비트 연산으로 NLogN이나 N으로도 할 수 있는 방법은 editorial 참고
public class P3_짝수단어 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201606/P3/inputL004.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            String S = sc.next();

            int result = getEvenCnt(S);
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static int getEvenCnt(String s) {
        boolean[] A = new boolean[26];
        int N = s.length();
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            Arrays.fill(A, true);

            for (int j = i; j < N; j++) {
                int ind = s.charAt(j) - 'a';
                A[ind] ^= true;
                if ((j - i + 1) % 2 == 0 && isEven(A)) cnt++; // 짝수개수 단어만 전체 짝수개인지 체크
            }
        }

        return cnt;
    }

    static boolean isEven(boolean[] A) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] == false) return false;
        }
        return true;
    }
}
