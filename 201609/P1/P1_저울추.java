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
            
            int[] A = new int[N + 1];
            boolean[] D = new boolean[5000 + 1];
            for (int i = 0; i < N; i++) {
                A[i] = sc.nextInt();
                D[A[i]] = true;
            }
            
            int ans = -1;
            int last = set(A, D, 1, K, N) - 1;
            System.out.println("last set " + last);
            if (last == K) ans = 0;//  0인경우는 추가 저울추가 필요없음
            else if (last < K) { // 추를 추가한다
                int add = 1;
                if (last > 0) {
                    int diff = 0;
                    for (int i = 0; i < N; i++) {
                        if (A[i] > last) {
                            diff = A[i] - last - 1;
                            System.out.println("diff " + diff);
                            int mid = Arrays.binarySearch(A, diff);
                            if (mid < 0) add = diff;
                            else {
                                for (int j = 0; j < i; j++) {
                                    if (Arrays.binarySearch(A, A[j] + diff) < 0) {
                                        add = A[j] + diff;
                                        break;
                                    }
                                }
                            }
                            
                            break;
                        }
                    }
                }
                System.out.println("K " + K + " add " + add + Arrays.toString(A));
                D[add] = true;
                A[N] = add;
                Arrays.sort(A);
                last = set(A, D, last, K, N + 1) - 1; // 저울 맞춘 last index
                if (last == K) // 추를 추가하고 한번더 돌림
                    ans = add;
                else
                    ans = -1;// 저울을 완성하지 못함
            }
            int result = ans;
            
            System.out.println(result);
            wr.write(result + "\n");
        }
        
        sc.close();
        wr.close();
    }
    
    static int set(int[] A, boolean[] D, int start, int end, int N) { // N은 A배열 길이 (추의 개수)
        int i = start;
        for (; i <= end; i++) {
            if (D[i]) continue;
            
            int n = i;
            int ind = N - 1;
            int sum = 0;
            for (int j = 0; j < N; j++) {
                if (n < A[j]) {
                    ind = j - 1;
                    break;
                }
                sum += A[j];
            }
            //System.out.println("ind " + ind);
            if (n <= sum) {
                for (int j = ind; j >= 0; j--) {
                    if (n == 12) {
                        System.out.println("i " + i + " ind " + ind + " A[j]" + A[j] + " sum " + sum + Arrays.toString(A));
                        System.out.println("D["+ n+ "]" + D[n]);
                        for (int k = 1; k < D.length; k++) {
                            if (D[k] == false) {
                                System.out.println("false d " + k);
                                break;
                            }
                        }
                    }
                    n -= A[j];
                    if (n < 0) break;
                    if (D[n]) {
                        D[i] = true;
                        break;
                    }
                }
            } else
                ;//break;
        }
        
        return i;
    }
}
