import java.util.Scanner;

public class Picnic_BIT {

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

            int taken = 0;
            int cnt = countPair(taken, sNum, friendMap);
            System.out.println(cnt);
        }

        sc.close();
    }

    // 1 단계: int [] 를 bit 로 만듦
    public static int countPair(int taken, int sNum, boolean[][] friendMap) {
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

        int cnt = 0;

        // freeStudent 와 짝이 될 학생을 결정.
        for (int i = freeStudent + 1; i < sNum; i++) {
            if ((taken & (1 << i)) == 0 && friendMap[freeStudent][i]) {
                taken += (1 << i);
                taken += (1 << freeStudent);
                cnt += countPair(taken, sNum, friendMap);
                taken -= (1 << freeStudent);
                taken -= (1 << i);
            }
        }

        return cnt;
    }
}
