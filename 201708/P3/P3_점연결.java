import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 12..
 */
// Set 1: N은 반드시 0, L = 0 또는 M = 0
// Set 2는 L = 0 또는 M = 0 이다.
// Set 3은 N, L, M 이 다 있는 듯
// Red (L), Blue (M), Purple (N)
public class P3_점연결 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201708/P3/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int L = sc.nextInt();
            int M = sc.nextInt();
            int N = sc.nextInt();

            int[] red = new int[L];
            int[] blue = new int[M];
            int[] purple = new int[N];

            for (int i = 0; i < L; i++) red[i] = sc.nextInt();
            for (int i = 0; i < M; i++) blue[i] = sc.nextInt();
            for (int i = 0; i < N; i++) purple[i] = sc.nextInt();

            Arrays.sort(red);
            Arrays.sort(blue);
            Arrays.sort(purple);

            long sum = 0;
            if (N == 0) {// set1
                if (L != 0) sum += red[L - 1] - red[0];
                if (M != 0) sum += blue[M - 1] - blue[0];
            } else if (L == 0 && M == 0) { // set2
                sum = purple[N - 1] - purple[0];
            } else if (L != 0 && M == 0) { // set2
                sum = getSubGraph(purple, red);
            } else if (M != 0 && L == 0) { // set2
                sum = getSubGraph(purple, blue);
            } else { // set3
                sum = getFullGraph(purple, red, blue);
            }
            long result = sum;
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    /*
        Purple 앞이나 뒤에 있는 Blue, Red 점들은 모두 purple 앞이나 뒤에 죽 붙이면 된다
     */
    static long getFirstLast(int[] purple, int[] other) {
        long sum = 0;
        if (purple[0] > other[0])
            sum += purple[0] - other[0];

        if (purple[purple.length - 1] < other[other.length - 1])
            sum += other[other.length - 1] - purple[purple.length - 1];

        return sum;
    }

    /*
        Purple 을 연결하고 Red, Blue 를 가까운 쪽에 연결하는 것과
        Purple 은 연결하지 않고 Red, Blue 끼리 쭉 연결하는 것중 작은 값을 Purple 구간별로 선택해서 더한다
     */
    static long getFullGraph(int[] purple, int[] red, int[] blue) {
        long sum = getFirstLast(purple, red) + getFirstLast(purple, blue);

        for (int p = 0; p < purple.length - 1; p++) {
            long rs = calcMinSum(purple[p], purple[p + 1], red);
            long bs = calcMinSum(purple[p], purple[p + 1], blue);
            long ps = purple[p + 1] - purple[p];
            sum += Math.min(rs + bs + ps, ps * 2);
        }
        return sum;
    }

    /*
        Purple과 어느 한 그래프 Red, Blue 만 있는데 서브그래프 R, B는 항상 존재해야 하므로
        Red 점이 없다해도 Purple 만으로도 R그래프를 형성해야한다
        그래서 항상 Purple을 끝까지 죽 연결 해놓고 Red나 Blue점을 연결한다
        구간 별 가장 긴 간선을 제거하는 방식으로 연결해야한다
     */
    static long getSubGraph(int[] purple, int[] other) {
        long sum = getFirstLast(purple, other);

        for (int p = 0; p < purple.length - 1; p++) {
            sum += calcMinSum(purple[p], purple[p + 1], other);
        }
        sum += purple[purple.length - 1] - purple[0];
        return sum;
    }

    /*
        Purple 구간에 해당하는 Blue, Red 점들 중 가장 긴 간선을 구하여 제거하고 나머지 합을 리턴
        인덱스를 넘어가거나 Purple eVal을 넘지 않도록 예외 필요
     */
    static long calcMinSum(int sVal, int eVal, int[] other) {
        int i = upperBound(other, other.length, sVal);
        if (i >= other.length || other[i] > eVal) return 0;
        int maxd = other[i] - sVal;
        long sum = maxd;

        while (i + 1 < other.length && other[i + 1] < eVal) {
            int d = other[i + 1] - other[i];
            maxd = Math.max(maxd, d);
            sum += d;
            i++;
        }

        int d = eVal - other[i];
        maxd = Math.max(maxd, d);
        sum += d;

        return sum - maxd;
    }

    public static int upperBound(int[] array, int length, int value) {
        int low = 0;
        int high = length;
        while (low < high) {
            final int mid = (low + high) / 2;
            if (value >= array[mid]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}
