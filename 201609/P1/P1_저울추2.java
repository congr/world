import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 31..
 */
public class P1_저울추2 {
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

            int[] A = new int[N + 1];
            boolean[] D = new boolean[5001]; // 5000
            for (int i = 0; i < N; i++) {
                A[i] = sc.nextInt();
                //D[A[i]] = true; // !!! 미리 초기화 한것은 쓰지 않음
            }

            int ans = -1;
            int last = setWithoutDup(A, D, N, K);// last index까지 만들수 있었다
            //System.out.println("last setWithoutDup " + last);

            if (last == K) ans = 0;                         // 0인경우는 추가 저울추가 필요없음
            else if (last < K) {                            // 추를 추가한다
                int keep = N;
                for (int i = 1; i <= K; i++) {              // 1부터 K까지 모든 수를 만들어서 적합한 추 값을 찾는다
                    int mid = Arrays.binarySearch(A, i);    // 이미 있는 값이면 continue, A[]에 없는 값을 하나씩 추가해본다
                    if (mid >= 0)                           // found
                        continue;

                    A[keep] = i;                            // candidate 처음에는 keep이 N이다
                    Arrays.sort(A);                         // 맨뒤에 추가하거나 중간에 추가되므로 sort
                    Arrays.fill(D, false);              // !!! 추가된 추를 가지고 D를 다시 채우기 위해 초기화

                    last = setWithoutDup(A, D, N + 1, K); // 저울 맞춘 last index
                    //System.out.println("2 last setWithoutDup " + last);
                    if (last == K) {                        // 이번에 추가한 추로 K까지 다 만들었다면 break
                        ans = i;
                        break;
                    }
                    keep = Arrays.binarySearch(A, i);       // i추를 추가했는데 소팅후 위치를 keep에 두고 다음 추를 keep index에 추가한다
                }
            }

            int result = ans;
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    // 중복 없이 A[]추를 선택하여 D배열을 채운다
    static int setWithoutDup(int[] A, boolean[] D, int N, int K) { // N은 A배열 길이 (추의 개수)
        D[0] = true;

        for (int i = 0; i < N; i++) {// A모든 추로 만들 수 있는 값
            for (int j = K; j >= A[i]; j--) { // K는 추로 만들 최대값, j는 모든 수
                //for(int j = A[i] ; j<=K ; j++) { // !!! 이렇게 만들면 안됨 j 구간은 같아도 D[j-A[i]] 채우는 순서에 차이 있음
                if (j - A[i] >= 0 && D[j - A[i]]) {
                    D[j] = true;
                }
            }
        }

        //System.out.println("D" + Arrays.toString(D));

        for (int i = 1; i <= K; i++) {
            if (D[i] == false) return i - 1; // !!! 여기서 false면 마지막까지 맞춘 구간은 i-1
        }
        return K;// D[1 ~ K] 모두 true
    }
}
