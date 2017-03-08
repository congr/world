import java.util.Scanner;

public class Picnic {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int tc = sc.nextInt();
        while (tc-- > 0) {
            int sNum = sc.nextInt();
            int pNum = sc.nextInt();
            boolean [][] friendMap = new boolean[sNum][sNum];

            for (int i = 0; i < pNum; i++) {
                int p1 = sc.nextInt();
                int p2 = sc.nextInt();

                friendMap[p1][p2] = friendMap[p2][p1] = true;
            }

            boolean taken[] = new boolean[sNum];
            int cnt = countPair(taken, sNum, friendMap);
            System.out.println(cnt);
        }

        sc.close();
    }

    // taken 을 int[] or boolean[]으로 만들고, 짝이 지어진 친구를 1 (true)로 세팅하고, 짝을 찾지 못한 0(false)인 친구를 계속 찾는다
    // 짝을 지을 수 있는 경우의 수를 최종 리턴.
    public static int countPair(boolean[] taken, int sNum, boolean[][] friendMap) {
        int freeStudent = -1;
        // 남은 첫 학생 구하기.
        for (int n = 0; n < sNum; n++) {
            if (!taken[n]) {
                freeStudent = n;
                break;
            }
        }

        if (freeStudent == -1) // 남은 학생 없다면, 친구를 모두 찾은 케이스 1가지 리턴
            return 1;

        int cnt = 0;

        // freeStudent 와 짝이 될 학생을 결정.
        for (int i = freeStudent + 1; i < sNum; i++) {
            if (!taken[i] && friendMap[freeStudent][i]) {
                taken[i] = taken[freeStudent] = true;
                cnt += countPair(taken, sNum, friendMap);
                taken[i] = taken[freeStudent] = false;
            }
        }

        return cnt;
    }
}
