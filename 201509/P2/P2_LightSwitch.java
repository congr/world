import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 26..
 */
public class P2_LightSwitch {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201509/P2/small_in.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int M = sc.nextInt();

            boolean[][] lights = new boolean[2][N];
            for (int i = 0; i < M; i++) {
                int y = sc.nextInt();
                int x = sc.nextInt();
                lights[y - 1][x - 1] = true;
            }

            int result = (M % 2 == 0) ? getCounts(lights, N) : -1;

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static int getCounts(boolean[][] lights, int N) {
        boolean isLastUp = false;
        int last = -1;
        int cnt = 0;

        for (int i = 0; i < N; i++) {
            if (last == -1) { // 시작
                if (lights[0][i] && lights[1][i]) { // 위아래 다 켜야하는경우
                    cnt++;
                } else if (lights[0][i]) { // 위가 켜짐
                    last = i;
                    isLastUp = true;
                } else if (lights[1][i]) { // 아래가 켜짐
                    last = i;
                    isLastUp = false;
                }
            } else { // pair 를 찾고있는 경우
                if (isLastUp) { // 마지막에 위를 켰다면
                    if (lights[0][i]) {// 위 라이트가 켜져야 한다면
                        cnt += i - last;
                        last = -1;
                    }
                    if (lights[1][i]) { // 아래를 켜야하는데 - 위 /아래 대각 방향
                        if (last == -1) {
                            last = i;
                            isLastUp = false;
                        } else {
                            cnt += i - last + 1;
                            last = -1;
                        }
                    }
                } else { // 마지막에 아래를 켰다면 아래 스위치를 먼저 본다
                    if (lights[1][i]) {// 아래 라이트가 켜져야 한다면
                        cnt += i - last;
                        last = -1;
                    }
                    if (lights[0][i]) { // 위를 켜야한다면 - 위 /아래 대각 방향이므로 +1 cnt
                        if (last == -1) {
                            last = i;
                            isLastUp = true;
                        } else {
                            cnt += i - last + 1;
                            last = -1;
                        }
                    }
                }
            }
        }
        return cnt;
    }
}
