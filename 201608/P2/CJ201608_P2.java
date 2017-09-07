import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 8..
 */
public class CJ201608_P2 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201608/P2/input3.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt(); // 가로 개수

            int[] hPrice = new int[N];
            int[] hDistance = new int[N];
            for (int i = 0; i < N - 1; i++) {
                hPrice[i] = sc.nextInt();
                hDistance[i] = sc.nextInt();
            }

            //System.out.println(Arrays.toString(hPrice));
            //System.out.println(Arrays.toString(hDistance));

            int vPathIndex = sc.nextInt() - 1;
            int M = sc.nextInt(); // 세로 개수

            //System.out.println(vPathIndex + " " + M);
            int[] vPrice = new int[M + 1];
            int[] vDistance = new int[M + 1];
            vPrice[0] = hPrice[vPathIndex];
            for (int i = 1; i <= M; i++) {
                vDistance[i - 1] = sc.nextInt();
                vPrice[i] = sc.nextInt();
            }

            //System.out.println(Arrays.toString(vPrice));
            //System.out.println(Arrays.toString(vDistance));

            int[] left = sumHoriPath(hPrice, hDistance, vPathIndex);
            int leftHoriSum = left[0];
            int leftMinPrice = left[1];
            int rightHoriSum = sumDistance(hDistance, vPathIndex, hDistance.length);

            //System.out.println("----");
            int minSum = 987654321;
            int sum = 0;
            int mp = leftMinPrice;
            int vPartialSum = 0;
            for (int i = 0; i < M; i++) {
                mp = Math.min(vPrice[i], mp);
                vPartialSum += mp * vDistance[i];

                mp = Math.min(vPrice[i + 1], mp);
                sum = vPartialSum + mp * sumDistance(vDistance, 0, i + 1) + mp * rightHoriSum;
                minSum = Math.min(sum, minSum);

                //System.out.println("mp * rightHoriSum " + mp * rightHoriSum);
                //System.out.println("vPartialSum " + vPartialSum);
                //System.out.println("minSum " + minSum);
            }

            //System.out.println("leftHoriSum " + leftHoriSum);
            int withoutVertical = sumHoriPath(hPrice, hDistance, N)[0];
            int result = Math.min(minSum + leftHoriSum, withoutVertical);
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static int sumDistance(int[] distance, int from, int to) {
        int sum = 0;
        for (int i = from; i < to; i++) {
            sum += distance[i];
        }
        //System.out.println("from " + from + " to " + to + " " + sum);
        return sum;
    }

    static int[] sumHoriPath(int[] price, int[] distance, int toIndex) {
        int minPrice = 987654321;
        int sum = 0;
        for (int i = 0; i < toIndex; i++) {
            minPrice = Math.min(price[i], minPrice);
            sum += minPrice * distance[i];
        }
        return new int[]{sum, minPrice};
    }
}
