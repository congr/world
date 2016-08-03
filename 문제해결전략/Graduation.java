import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

// MyScanner 와 Scanner 100ms 정도차로 MyScanner가 빠름
// System.out.println 과 scanner 로 해도 입출력양이 크다고 명시되지 않는 한 사용해도 무리가 없는 것 같다
// 결국 입출력의 속도가 아닌 그냥 알고리즘의 차이였다. memoization을 잘못한 것이 문제임.
public class Graduation {
	static int N, K, M, L;
	static int cls[] = new int[10], pre[] = new int[12];
	static int cache[][] = new int[10][1 << 12];

	public static void main(String[] args) throws Exception {
		// MyScanner sc = new MyScanner();
		Scanner sc = new Scanner(System.in);

		int tc = sc.nextInt();
		while (tc-- > 0) {
			// input tc #
			N = sc.nextInt(); // 총 과목수
			K = sc.nextInt(); // 들어야 졸업하는 과목수
			M = sc.nextInt(); // 학기수
			L = sc.nextInt(); // 한학기에 들을 수 있는 최대 과목수

			// 초기화
			for (int i = 0; i < 10; i++)
				Arrays.fill(cache[i], -1);
			Arrays.fill(pre, 0);
			Arrays.fill(cls, 0);

			// 선수과목
			for (int i = 0; i < N; i++) {
				int cnt = sc.nextInt();
				for (int j = 0; j < cnt; j++) {
					int p = sc.nextInt();
					pre[i] |= (1 << p);
				}
			}

			// 학기마다 개설 과목
			for (int i = 0; i < M; i++) {
				int cnt = sc.nextInt();
				for (int j = 0; j < cnt; j++) {
					int p = sc.nextInt();
					cls[i] |= (1 << p);
				}
			}

			int result = graduate(0, 0);
			if (result == INF)
				System.out.println("IMPOSSIBLE");
			else
				System.out.println(result);
		}

		sc.close();
	}

	static final int INF = 987654321;

	// 학기수를 리턴
	static int graduate(int semester, int taken) {
		// base case
		if (Integer.bitCount(taken) >= K)
			return 0;
		if (semester == M)
			return INF;

		if (cache[semester][taken] != -1)
			return cache[semester][taken];

		cache[semester][taken] = INF;

		// 이번학기에 들을 수 있는 과목중 아직 듣지 않은 과목을 찾아라
		int canTake = (cls[semester] & ~taken);

		// 선수과목을 다 듣지않은 과목은 canTake에서 0 초기화, 즉 선수과목까지 마쳐서 이번 학기에 실제로 들을 수 있는 과목을
		// 구한다.
		for (int i = 0; i < N; i++) {
			if ((canTake & (1 << i)) > 0 && ((taken & pre[i]) != pre[i])) {
				canTake &= ~(1 << i);
			}
		}

		// 들을 수 있는 과목을 듣고 다음 학기로 이동하여 탐색
		for (int take = canTake; take > 0; take = ((take - 1) & canTake)) {
			if (Integer.bitCount(take) > L)
				continue; // 한학기당 최대 L과목 들을 수 있다. - 재귀를 안들어간다

			cache[semester][taken] = Math.min(cache[semester][taken], graduate(semester + 1, taken | take) + 1);
		}

		// 이번 학기에 아무것도 듣지 않을 경우,
		cache[semester][taken] = Math.min(cache[semester][taken], graduate(semester + 1, taken)); // 안들음

		return cache[semester][taken];
	}

	public static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		public MyScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}

		void close() {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
