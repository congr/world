import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 15..
 */
public class P2_직각다각형 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201606/P2/sample.in"; // path from root
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
            int[] C = Arrays.copyOf(A, N); // A -> C
            for (int i = 0; i < N; i++) {
                C[i] = Math.abs(C[i]);
            }

            C = reverseArrayXOR(C);
            System.out.println("reverseC" + Arrays.toString(C));

            //5, 7, 4, 3, -2, -2, 3, 2
            //C[0] = A[N - 1] < 0 ? C[0] : -C[0]; // 부호 반대
            for (int i = 0, j = N - 2; i < N; i++, j--) {
                if (j < 0) j = N - 1;
                C[i] = A[j] < 0 ? C[i] : -C[i];
            }

            int isSame = 0;
            String BBstr = toStringfromIntArray(BB);
            String Astr = toStringfromIntArray(A); // org A
            String Cstr = toStringfromIntArray(C); // reverse

            System.out.println("A " + Arrays.toString(A));
            System.out.println("B " + BBstr);
            System.out.println("C " + Arrays.toString(C));

            if (BBstr.indexOf(Astr) >= 0) isSame = 1;
            if (BBstr.indexOf(Cstr) >= 0) isSame = 1;

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

    static Object[] reverseArray(int[] array) {
        List list = Arrays.asList(array);
        Collections.reverse(list);
        return list.toArray();
    }

    static int[] reverseArrayXOR(int[] array) {
        int low = 0;
        int high = array.length - 1;

        while (low < high) {
            array[low] = (char) (array[low] ^ array[high]);
            array[high] = (char) (array[low] ^ array[high]);
            array[low] = (char) (array[low] ^ array[high]);
            low++;
            high--;
        }

        return array;
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
