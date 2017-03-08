import java.util.Arrays;
import java.util.Scanner;

public class Picnic_BIT_DP {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int tc = Integer.parseInt(sc.next());
        while (tc-- > 0) {
            int sNum = Integer.parseInt(sc.next());
            int pNum = Integer.parseInt(sc.next());
            boolean[][] friendMap = new boolean[sNum][sNum];

            for (int i = 0; i < pNum; i++) {
                int p1 = Integer.parseInt(sc.next());
                int p2 = Integer.parseInt(sc.next());
                friendMap[p1][p2] = friendMap[p2][p1] = true;
            }

            // 동적표를 초기화
            Arrays.fill(D, 0);

            int taken = 0;
            int cnt = countPair(taken, sNum, friendMap, 0);
            System.out.println(cnt);
        }

        sc.close();
    }

    static int[] D = new int[1 << 10]; // 2 이상 10이하의 친구

    // 2단계 bit 상태를 이용해 동적표 memoization
    public static int countPair(int taken, int sNum, boolean[][] friendMap, int pairedN) {
        int freeStudent = -1;
        // 남은 첫 학생 구하기.
        for (int n = 0; n < sNum; n++) {
            if ((taken & (1 << n)) == 0) {
                freeStudent = n;
                break;
            }
        }

        if (freeStudent == -1) // 남은 학생 없다면, 친구를 모두 찾은 케이스 1가지 리턴
            return 1;

        if (D[taken] == 0) {
            // freeStudent 와 짝이 될 학생을 결정.
            for (int i = freeStudent + 1; i < sNum; i++) {
                if ((taken & (1 << i)) == 0 && friendMap[freeStudent][i]) {
                    // taken 자체를 바꾸면 안됨!! 재귀호출 리턴해왔을 때 값이 안맞음.
                    int take = (1 << i);
                    take += (1 << freeStudent);
                    // taken += (1 << i);
                    // taken += (1 << freeStudent);
                    D[taken] += countPair(taken | take, sNum, friendMap, pairedN + 2);
                    // taken -= (1 << freeStudent);
                    // taken -= (1 << i);
                }
            }
        }

        return D[taken];
    }
}
