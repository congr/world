import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 20..
 */
public class P3_Mid {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201708/P3/input002.txt"; // path from root
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

            int[] Ns = new int[N];
            int[] Ms = new int[M];
            int[] Ls = new int[L];

            for (int i = 0; i < L; i++)  // 빨강
                Ls[i] = sc.nextInt();

            for (int i = 0; i < M; i++)  // 파랑
                Ms[i] = sc.nextInt();

            for (int i = 0; i < N; i++)  // 보라
                Ns[i] = sc.nextInt();

            int[] tempNM = concat(Ns, Ms);
            Arrays.sort(tempNM);
            int[] tempNL = concat(Ns, Ls);
            Arrays.sort(tempNL);

            Arrays.sort(Ls);
            Arrays.sort(Ms);
            Arrays.sort(Ns);

            //System.out.println(Arrays.toString(tempNL));
            //System.out.println(Arrays.toString(tempNM));

            long Ledges = findEdge(tempNL, Ls);
            long Medges = findEdge(tempNM, Ms);
            //long Nedges = findEdge(Ns, Ns);

            long nw = 0;
            if (N > 0) nw = (long) Ns[N - 1] - Ns[0];
            long longest = Math.max (tempNL[tempNL.length-1], tempNM[tempNM.length-1]);
            long shortest = Math.min (tempNL[0], tempNM[0]);
            //System.out.println("L " + Ledges + " M " + Medges + " Nw " + nw);

            long result = longest-shortest + Medges + Ledges;
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static long findEdge(int[] Ns, int[] LorM) {
        if (LorM.length == 0) return 0;

        boolean[] used = new boolean[LorM.length];
        long total = 0;
        for (int i = 0; i < LorM.length; i++) {

            int me = LorM[i];
            int index = Arrays.binarySearch(Ns, LorM[i]);
            long min = -1;
            int connectedValue = -1;
            if (index == 0) {
                min = Math.abs(me - Ns[index + 1]);
                connectedValue = Ns[index + 1];
            } else if (index == Ns.length - 1) {
                min = Math.abs(me - Ns[index - 1]);
                connectedValue = Ns[index - 1];
            } else {
                int big = Math.abs(me - Ns[index + 1]);
                int sm = Math.abs(me - Ns[index - 1]);
                if (big < sm) {
                    connectedValue = Ns[index + 1];
                } else
                    connectedValue = Ns[index - 1];
                min = Math.min(Math.abs(me - Ns[index + 1]), Math.abs(me - Ns[index - 1]));
            }
            total += min;

            //used[i] = true;
            //int f = Arrays.binarySearch(LorM, connectedValue);
            //if (f >= 0)
            //    used[f] = true;
            //System.out.println("end " +Arrays.toString(used));
            //System.out.println("weight "+ min);
        }
        return total;
    }

    static long matchEdge(int[] Ns, int[] LorM) {
        if (LorM.length == 0) return 0;

        int[] arr = new int[LorM.length]; // N 의 찾은 인덱스
        long total = 0;
        //Arrays.fill(arr, -1);

        // diff 가 가장 작은 N, M을 찾는다
        int minDiff = 987654321;
        for (int i = 0; i < Ns.length; i++) {
            int diff = Math.abs(LorM[0] - Ns[i]);
            if (minDiff > diff && diff > 0) {
                minDiff = diff;
                arr[0] = i;
            }
        }

        total += minDiff;
        int last = arr[0];
        boolean found;

        for (int i = 1; i < LorM.length; i++) {
            int targetDiff = Math.abs(Ns[arr[i - 1]] - LorM[i]);
            int jMinDiff = 987654321;
            found = false;
            for (int j = arr[i - 1]; j < Ns.length; j++) {
                int diff = Math.abs(Ns[j] - LorM[i]);
                if (jMinDiff > diff && diff > 0) {
                    arr[i] = j;
                    jMinDiff = diff;
                    found = true;
                }
                if (diff > targetDiff)
                    break;

            }
            total += Math.abs(Ns[arr[i]] - LorM[i]);
            //System.out.println(Math.abs(Ns[arr[i]] - LorM[i]));
        }

        //System.out.println("found arr " + Arrays.toString(arr));
        return total;
    }

    // N배열에서 가장 근접한 diff weight 를 Ms, Ls 배열에 셋팅한다
    static long findWeight(int[] Ns, int[] LorM) {
        long diff = 0;

        for (int i = 0; i < LorM.length; i++) {
            //System.out.println("LorM[i] " + LorM[i]);
            int target = LorM[i];
            int next = 1;
            int mid = Arrays.binarySearch(Ns, target);
            while (mid < 0) {
                int targetSmaller = LorM[i] - next;
                int targetLarger = LorM[i] + next;

                target = targetSmaller;
                mid = Arrays.binarySearch(Ns, target);
                if (mid < 0) {
                    target = targetLarger;
                    mid = Arrays.binarySearch(Ns, target);
                }
                next++;
            }
            // found
            diff += Math.abs(Ns[mid] - LorM[i]);
        }

        return diff;
    }

    static public int[] concat(int[] a, int[] b) {
        int aLen = a.length;
        int bLen = b.length;
        int[] c = new int[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
