import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 23..
 */
// 201608 2번 삼거리 주유소
public class P2_GasStation {
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
            int N = sc.nextInt() - 1;

            int[] horiCity = new int[N];
            int[] horiDist = new int[N];
            for (int i = 0; i < N; i++) {
                horiCity[i] = sc.nextInt();
                horiDist[i] = sc.nextInt();
            }

            int interCity = sc.nextInt() - 1; // 삼거리가 되는 도시
            int M = sc.nextInt(); // 세로 도시 개수

            // 삼거리 되기전 왼쪽 가로 도시 계산
            long[] horiCost = getHoriLeftCost(horiCity, horiDist, interCity);
            long minCost = horiCost[0]; // 최소값 주유비
            long leftSumCost = horiCost[1]; // 최종 왼쪽 주유비 총합

            // 오른쪽 가로 도시 길이 계산
            long rightDistSum = 0; // 오른쪽 총 거리
            for (int i = interCity; i < N; i++) {
                rightDistSum += (long) horiDist[i];
            }

            // 세로 도시 입력
            int[] vertCity = new int[M];
            int[] vertDist = new int[M];
            for (int i = 0; i < M; i++) {
                vertDist[i] = sc.nextInt();
                vertCity[i] = sc.nextInt();
            }

            // 세로도시와 남은 오른쪽 가로 도시 계산
            long remainedMinSum = minCost * rightDistSum;// !!! 세로 도시 안가는 경우도 있으므로 최소값에 넣어줌
            for (int i = 0; i < M; i++) {
                long[] vertCost = getVertCost(vertCity, vertDist, minCost, i + 1); // !!! i+1 로 end를 줘야하는 이유? exclusive to index
                long minC = vertCost[0];
                long sumC = vertCost[1];
                //System.out.println("vertCost " + i + " " + minC + " " + sumC);
                remainedMinSum = Math.min(remainedMinSum, sumC + minC * rightDistSum);
            }

            //System.out.println(leftSumCost + " " + remainedMinSum);
            long result = leftSumCost + remainedMinSum; // right sum은 일정하고, left sum은 최소 경로 값을 찾아서 더함
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static long[] getVertCost(int[] city, int[] dist, long minCost, int end) {
        int min = (int) minCost;
        long sum = 0;
        int distSum = 0; // 세로 도시 거쳐온 길이
        for (int i = 0; i < end; i++) {
            sum += (long) dist[i] * min;
            min = Math.min(city[i], min);
            distSum += dist[i];
        }
        sum += min * distSum; // !!! 최소 주유비로 세로 도시를 되돌아 거슬러 올라가는 비용을 더해줘야함
        return new long[]{min, sum};
    }

    static long[] getHoriLeftCost(int[] city, int[] dist, int end) {
        int min = city[0];
        long sum = 0;
        for (int i = 0; i < end; i++) {
            min = Math.min(city[i], min);
            sum += (long) dist[i] * min;
        }
        if (end < city.length)
            min = Math.min(min, city[end]);
        return new long[]{min, sum};
    }
}
