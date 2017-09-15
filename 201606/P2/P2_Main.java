
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

public class P2_Main {

	static int N;

	public static void main(String[] args) throws Exception {
		String inFilename = (args != null && args.length > 0) ? args[0] : "201606/P2/input002.txt"; // path from root
		File inFile = new File(inFilename);
		File outFile = new File(inFilename.replace("in", "out"));
		FileWriter wr = new FileWriter(outFile);
		Scanner sc = new Scanner(System.in);
		if (inFile.exists()) sc = new Scanner(inFile);


		int tc = sc.nextInt();
		while (tc-- > 0) {
			N = sc.nextInt();

			int[] aRo = new int[N];
			int[] bRo = new int[N];
			for (int i = 0; i < N; i++) {
				aRo[i] = sc.nextInt();
			}

			for (int i = 0; i < N; i++) {
				bRo[i] = sc.nextInt();
			}

			int ret = solve(aRo, bRo);

			wr.write(ret + "\n");
			System.out.println(ret);
		}

		sc.close();
		wr.close();
	}

	static int solve(int[] aRo, int[] bRo) {
		int[] cRo = new int[N * 2];

		// 방향 반대일 경우, 부호 반대로..
		// 경로가 (-2, 3, 2, -2, -3, -4, -7, -5)였다면 뒤집으면 (5, 7, 4, 3, -2, -2, 3, 2)
		for (int i = N - 1, j = 0; i >= 0; i--, j++) {
			cRo[j] = Math.abs(aRo[i]); // 부호는 버림.
			int ii = getPrevI(i);
			cRo[j] = aRo[ii] < 0 ? cRo[j] : -cRo[j]; // 부호 반대.

			cRo[j + N] = cRo[j];
		}

		// 반대방향일 경우 비교, -1 은 같지 않음.
		//System.out.println(Arrays.toString(aRo));
		//System.out.println(Arrays.toString(bRo));
		//System.out.println(Arrays.toString(cRo));
		int kmp = kmpMatcher(cRo, bRo);

		// 같은 방향 경우 비교
		// (1 5 4 3 6 7 1 5 4 3 6 7 -> 3 6 7 1 5 4 : 포함 된다면 같음.) 
		if (kmp == -1) { // aRo를 두번 이어 붙여서 cRo를 만들어 비교.
			System.arraycopy(aRo, 0, cRo, 0, N);
			System.arraycopy(aRo, 0, cRo, N, N);
			kmp = kmpMatcher(cRo, bRo);
		}

		int result = kmp == -1 ? 0 : 1;
		return result;
	}

	static int getPrevI(int i) {
		return i == 0 ? N - 1 : i - 1;
	}

	public static int[] prefixFunction(int[] s) {
		int[] p = new int[s.length];
		int k = 0;
		for (int i = 1; i < s.length; i++) {
			while (k > 0 && s[k] != s[i])
				k = p[k - 1];
			if (s[k] == s[i])
				++k;
			p[i] = k;
		}
		return p;
	}

	public static int kmpMatcher(int[] s, int[] pattern) {
		int m = pattern.length;
		if (m == 0)
			return 0;
		int[] p = prefixFunction(pattern);
		for (int i = 0, k = 0; i < s.length; i++)
			for (;; k = p[k - 1]) {
				if (pattern[k] == s[i]) {
					if (++k == m)
						return i + 1 - m;
					break;
				}
				if (k == 0)
					break;
			}
		return -1;
	}

}
