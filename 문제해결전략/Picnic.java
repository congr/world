import java.util.Scanner;

public class Picnic {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);

		int tc = sc.nextInt();
		int sNum = 0;
		int pNum = 0;
		boolean[][] friendMap = null;

		while (tc-- > 0) {
			sNum = sc.nextInt();
			pNum = sc.nextInt();
			friendMap = new boolean[sNum][sNum];

			for (int i = 0; i < pNum; i++) {
				int p1 = sc.nextInt();
				int p2 = sc.nextInt();

				friendMap[p1][p2] = true;
			}

			boolean taken[] = new boolean[sNum];
			int cnt = countPair(taken, sNum, friendMap);
			System.out.println(cnt);
		}

		sc.close();
	}

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
		for (int student = freeStudent + 1; student < sNum; student++) {
			if (!taken[student] && friendMap[freeStudent][student]) {
				taken[student] = taken[freeStudent] = true;
				cnt += countPair(taken, sNum, friendMap);
				taken[student] = taken[freeStudent] = false;
			}
		}

		return cnt;
	}
}
