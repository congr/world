import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Snake {
	static int[] genders = new int[2001];
	static int[] energes = new int[2001];
	static int[] blocked = new int[2001];
	static int[][] pre = new int[2001][2001];
	static long[][] d = new long[2001][2001];

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("sample.in"));
		// Scanner sc = new Scanner(new File(args[0]));
		// String out = args[0].replace("in", "out");
		// FileWriter wr = new FileWriter(new File(out));

		int tc = sc.nextInt();
		while (tc-- > 0) {

			// initialize
			Arrays.fill(genders, -1);
			Arrays.fill(energes, -1);
			Arrays.fill(blocked, 0);
			for (int i = 0; i < 2001; i++)
				Arrays.fill(d[i], -1);

			// input
			int N = sc.nextInt();
			for (int i = 0; i < N; i++) {
				energes[i] = sc.nextInt();
			}
			for (int i = 0; i < N; i++) {
				genders[i] = sc.nextInt();
			}

			// fill pre[][] with energy
			fillEnergy(pre, N);

			long ret = solve(0, 0, N);

			// wr.write(ret + "\n");
			System.out.println(ret);
		}

		sc.close();
		// wr.close();
	}

	static void fillEnergy(int[][] pre, int N) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (genders[i] == genders[j])
					pre[i][j] = 0;
				else {
					pre[i][j] = pre[j][i] = energes[i] * energes[j];
				}
			}
		}

		System.out.println("-----");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(pre[i][j] + " ");
			}
			System.out.println();
		}
	}

	// 환형 고
	static boolean isBlocked(int male, int female, int N) {
		boolean inBlocked = false;
		boolean outBlocked = false;

		for (int i = male; i <= female; i++) {
			if (blocked[i] == 1) {
				inBlocked = true;
				break;
			}
		}

		for (int i = male; i >= 0; i--) {
			if (blocked[i] == 1) {
				outBlocked = true;
				break;
			}
		}
		for (int i = female; i < N; i++) {
			if (blocked[i] == 1) {
				outBlocked = true;
				break;
			}
		}

		return inBlocked && outBlocked;
	}

	static long solve(int male, int female, int N) {
		male = getNextMale(male, N);
		if (male == N)
			return 0;
		
		long result = 0;
		for (int j = 0; j < N; j++) {
			if (pre[male][j] != 0 && blocked[j] == 0 && !isBlocked(male, j, N)) {
				
				blocked[male] = blocked[j] = 1;
				result = Math.max(result, solve(male, 0, N) + pre[male][j]);
				blocked[male] = blocked[j] = 0;
			}
		}

		return result;
	}

	static int getNextMale(int male, int N) {
		int next = male+1;
		
		if (male == N) return N;// 
		for (int i = male; i < N; i++) {
			if (blocked[i] == 0)
				break;
		}

		return next;
	}
}
