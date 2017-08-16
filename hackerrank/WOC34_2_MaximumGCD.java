import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by cutececil on 2017. 7. 20..
 */
/*
https://www.hackerrank.com/contests/w34/challenges/maximum-gcd-and-sum
 */
public class WOC34_2_MaximumGCD {
    
    public static int[] combineInt(int[] a, int[] b) {
        int length = a.length + b.length;
        int[] result = new int[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
    
    static int maximumGcdAndSum(int[] A, int[] B, int N) {
        Arrays.sort(A);
        Arrays.sort(B);
        //Object[] C = sortRemoveDup(combineInt(A, B));
        
        int[] AD = new int[N];
        int[] BD = new int[N];
        int maxD = 0;
        int ai = 0, bi = 0;
        for (int k = 2; k <= 1000000; k++) {
            if (k > A[N-1] || k > B[N-1]) break;
            
            int d = k;
            int ma =0, mb =0;
            
            int a = 0, b = 0;
            for (int i = 0; i < N; i++) {
                a = (A[i] % d == 0) ? d : 0;
                AD[i] = Math.max(AD[i], a);
                ma = Math.max(ma, a);
            }
            
            for (int i = 0; i < N; i++) {
                b = (B[i] % d == 0) ? d : 0;
                BD[i] = Math.max(BD[i], b);
                mb = Math.max(mb, b);
            }
            
            if (ma == d && mb == d)
                maxD = d;
        }
        
        ai = Arrays.binarySearch(AD, maxD);
        bi = Arrays.binarySearch(BD, maxD);
        
        
        return A[ai] + B[bi];
    }
    
    // int [] -> Treeset : remove duplicate elements
    static Object[] sortRemoveDup(int[] A) {
        List<Integer> list = IntStream.of(A).boxed().collect(Collectors.toList());
        TreeSet<Integer> ts = new TreeSet<>(list);
        return ts.toArray();
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] A = new int[n];
        for (int A_i = 0; A_i < n; A_i++) {
            A[A_i] = in.nextInt();
        }
        int[] B = new int[n];
        for (int B_i = 0; B_i < n; B_i++) {
            B[B_i] = in.nextInt();
        }
        int res = maximumGcdAndSum(A, B, n);
        System.out.println(res);
    }
}
