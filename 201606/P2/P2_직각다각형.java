import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 15..
 */
// 2016 06 P2 직각다각형
// 도형 문제 아니고, 샘플로 문자열 역순 규칙을 잘 해보다 보면 알게 된다
// 핵심은 부호를 숫자를 역순할때 그 숫자의 부호가 아닌 바로 앞의 부호를 반대로 해야한다
// 문자열 비교를 KMP 로 안하니까 set2가 잘 안돌아간다
public class P2_직각다각형 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201606/P2/input002.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);
        
        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            
            int[] A = new int[N];
            int[] B = new int[N];
            for (int i = 0; i < N; i++) A[i] = sc.nextInt();
            for (int i = 0; i < N; i++) B[i] = sc.nextInt();
            
            int[] BB = concatArray(B, B);
            int[] R = new int[N];
            
            // A -> R reverse
            // -2, 3, 2, -2, -3, -4, -7, -5 를 뒤집으면
            // 5, 7, 4, 3, -2, -2, 3, 2 이렇게 되야함
            for (int i = 0, j = N - 1; i < N; i++, j--) {
                int k = j - 1;
                if (k < 0) k = N - 1;
                R[i] = Math.abs(A[j]); // 부호 버리고 역순
                R[i] = A[k] < 0 ? R[i] : -R[i]; // 부호 바로 한칸 앞것의 반대
            }
            
            String BBstr = toStringfromIntArray(BB);
            String Astr = toStringfromIntArray(A); // original A
            String Rstr = toStringfromIntArray(R); // reverse A
            
            int isSame = 0;
            if (BBstr.indexOf(Astr) >= 0) isSame = 1;
            if (BBstr.indexOf(Rstr) >= 0) isSame = 1;
            
            int result = isSame;
            System.out.println(result);
            wr.write(result + "\n");
        }
        
        sc.close();
        wr.close();
    }
    
    static String toStringfromIntArray(int[] arr) {
        String str = "";
        for (int i = 0; i < arr.length; i++) {
            str += arr[i];
        }
        return str;
    }
    
    static public int[] concatArray(int[] a, int[] b) {
        int aLen = a.length;
        int bLen = b.length;
        int[] c = new int[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
