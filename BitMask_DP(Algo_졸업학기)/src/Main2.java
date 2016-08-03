import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.BitSet;
import java.util.StringTokenizer;

public class Main2 {
	static int N, K, M, L;
	static BitSet cls[], pre[];

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
	 
	      String nextLine(){
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
	
	public static void main(String[] args) throws Exception {
		// Scanner sc = new Scanner(new File("problem.in"));
		MyScanner sc = new MyScanner();

		int tc = sc.nextInt();
		while (tc-- > 0) {
			// input tc #
			N = sc.nextInt(); // 총 과목수
			K = sc.nextInt(); // 들어야 졸업하는 과목수
			M = sc.nextInt(); // 학기수
			L = sc.nextInt(); // 한학기에 들을 수 있는 최대 과목수

			// 선수과목
			pre = new BitSet[N];
			for (int i = 0; i < N; i++) {
				pre[i] = new BitSet();
				int cnt = sc.nextInt();
				for (int j = 0; j < cnt; j++) {
					int p = sc.nextInt();
					pre[i].set(p);
				}
			}

			// 학기마다 개설 과목
			cls = new BitSet[N];
			for (int i = 0; i < M; i++) {
				cls[i] = new BitSet();
				int cnt = sc.nextInt();
				for (int j = 0; j < cnt; j++) {
					int p = sc.nextInt();
					cls[i].set(p);
				}
			}

			BitSet taken = new BitSet();
			// System.out.println(graduate(0, taken));

			int result = graduate(0, taken);
			if (result == INF)
				BinaryStdOut.write("IMPOSSIBLE\n");
			else
				BinaryStdOut.write(result + "\n");
		}
		
		BinaryStdOut.close();
		sc.close();
	}

	static final int INF = 987654321;
	int[][] cache = new int[10][12];

	// 학기수를 리턴
	static int graduate(int semester, BitSet taken) {
		// base case
		if (taken.cardinality() >= K)
			return 0;
		if (semester == M)
			return INF;

		long[] d = taken.toLongArray();
		int ret = 0;
		ret = INF; // 학기수
		
		// 이번학기에 들을 수 있는 과목중 아직 듣지 않은 과목을 찾아라
		BitSet canTake = new BitSet();
		canTake.or(cls[semester]);
		canTake.andNot(taken); // 이번학기에 개설되어 들을수 있는 과목

		// 선수과목을 다 듣지않은 과목은 canTake에서 0 초기화, 즉 선수과목까지 마쳐서 이번 학기에 실제로 들을 수 있는 과목을
		// 구한다.
//		BitSet alreadyTaken = new BitSet();
//		alreadyTaken.or(taken);
		
		BitSet alreadyTaken = (BitSet) taken.clone();
		for (int i = 0; i < N; i++) {
			if (canTake.get(i)) {
				alreadyTaken.and(pre[i]);
				if (alreadyTaken.equals(pre[i]) == false)
					canTake.clear(i);
			}
		}

		// 들을 수 있는 과목을 듣고 다음 학기로 이동하여 탐색
		BitSet takeNow = new BitSet();
		BitSet take = new BitSet();
		take.or(canTake);
		while (!take.isEmpty()) {
			// System.out.println(semester + " " + take);
			//long[] longs = take.toLongArray();
			if (take.cardinality() > L)
//			if (Long.bitCount(longs) > L)
				continue; // 한학기당 최대 L과목 들을 수 있다. - 재귀를 안들어간다

			takeNow.or(take);
			takeNow.or(taken);

			ret = Math.min(ret, graduate(semester + 1, takeNow) + 1);

			int lowestSetBit = take.nextSetBit(0);
			take.clear(lowestSetBit);
			take.and(canTake);
		}

		// 이번 학기에 아무것도 듣지 않을 경우,
		ret = Math.min(ret, graduate(semester + 1, taken)); // 안들음

		return ret;
	}

	public static class BinaryStdOut {
		private static BufferedOutputStream out = new BufferedOutputStream(System.out);

		private static int buffer; // 8-bit buffer of bits to write out
		private static int n; // number of bits remaining in buffer

		// don't instantiate
		private BinaryStdOut() {
		}

		/**
		 * Write the specified bit to standard output.
		 */
		private static void writeBit(boolean bit) {
			// add bit to buffer
			buffer <<= 1;
			if (bit)
				buffer |= 1;

			// if buffer is full (8 bits), write out as a single byte
			n++;
			if (n == 8)
				clearBuffer();
		}

		/**
		 * Write the 8-bit byte to standard output.
		 */
		private static void writeByte(int x) {
			assert x >= 0 && x < 256;

			// optimized if byte-aligned
			if (n == 0) {
				try {
					out.write(x);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}

			// otherwise write one bit at a time
			for (int i = 0; i < 8; i++) {
				boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
				writeBit(bit);
			}
		}

		// write out any remaining bits in buffer to standard output, padding
		// with 0s
		private static void clearBuffer() {
			if (n == 0)
				return;
			if (n > 0)
				buffer <<= (8 - n);
			try {
				out.write(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			n = 0;
			buffer = 0;
		}

		/**
		 * Flush standard output, padding 0s if number of bits written so far is
		 * not a multiple of 8.
		 */
		public static void flush() {
			clearBuffer();
			try {
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Flush and close standard output. Once standard output is closed, you
		 * can no longer write bits to it.
		 */
		public static void close() {
			flush();
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Write the specified bit to standard output.
		 * 
		 * @param x
		 *            the <tt>boolean</tt> to write.
		 */
		public static void write(boolean x) {
			writeBit(x);
		}

		/**
		 * Write the 8-bit byte to standard output.
		 * 
		 * @param x
		 *            the <tt>byte</tt> to write.
		 */
		public static void write(byte x) {
			writeByte(x & 0xff);
		}

		/**
		 * Write the 32-bit int to standard output.
		 * 
		 * @param x
		 *            the <tt>int</tt> to write.
		 */
		public static void write(int x) {
			writeByte((x >>> 24) & 0xff);
			writeByte((x >>> 16) & 0xff);
			writeByte((x >>> 8) & 0xff);
			writeByte((x >>> 0) & 0xff);
		}

		/**
		 * Write the r-bit int to standard output.
		 * 
		 * @param x
		 *            the <tt>int</tt> to write.
		 * @param r
		 *            the number of relevant bits in the char.
		 * @throws IllegalArgumentException
		 *             if <tt>r</tt> is not between 1 and 32.
		 * @throws IllegalArgumentException
		 *             if <tt>x</tt> is not between 0 and 2<sup>r</sup> - 1.
		 */
		public static void write(int x, int r) {
			if (r == 32) {
				write(x);
				return;
			}
			if (r < 1 || r > 32)
				throw new IllegalArgumentException("Illegal value for r = " + r);
			if (x < 0 || x >= (1 << r))
				throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
			for (int i = 0; i < r; i++) {
				boolean bit = ((x >>> (r - i - 1)) & 1) == 1;
				writeBit(bit);
			}
		}

		/**
		 * Write the 64-bit double to standard output.
		 * 
		 * @param x
		 *            the <tt>double</tt> to write.
		 */
		public static void write(double x) {
			write(Double.doubleToRawLongBits(x));
		}

		/**
		 * Write the 64-bit long to standard output.
		 * 
		 * @param x
		 *            the <tt>long</tt> to write.
		 */
		public static void write(long x) {
			writeByte((int) ((x >>> 56) & 0xff));
			writeByte((int) ((x >>> 48) & 0xff));
			writeByte((int) ((x >>> 40) & 0xff));
			writeByte((int) ((x >>> 32) & 0xff));
			writeByte((int) ((x >>> 24) & 0xff));
			writeByte((int) ((x >>> 16) & 0xff));
			writeByte((int) ((x >>> 8) & 0xff));
			writeByte((int) ((x >>> 0) & 0xff));
		}

		/**
		 * Write the 32-bit float to standard output.
		 * 
		 * @param x
		 *            the <tt>float</tt> to write.
		 */
		public static void write(float x) {
			write(Float.floatToRawIntBits(x));
		}

		/**
		 * Write the 16-bit int to standard output.
		 * 
		 * @param x
		 *            the <tt>short</tt> to write.
		 */
		public static void write(short x) {
			writeByte((x >>> 8) & 0xff);
			writeByte((x >>> 0) & 0xff);
		}

		/**
		 * Write the 8-bit char to standard output.
		 * 
		 * @param x
		 *            the <tt>char</tt> to write.
		 * @throws IllegalArgumentException
		 *             if <tt>x</tt> is not betwen 0 and 255.
		 */
		public static void write(char x) {
			if (x < 0 || x >= 256)
				throw new IllegalArgumentException("Illegal 8-bit char = " + x);
			writeByte(x);
		}

		/**
		 * Write the r-bit char to standard output.
		 * 
		 * @param x
		 *            the <tt>char</tt> to write.
		 * @param r
		 *            the number of relevant bits in the char.
		 * @throws IllegalArgumentException
		 *             if <tt>r</tt> is not between 1 and 16.
		 * @throws IllegalArgumentException
		 *             if <tt>x</tt> is not between 0 and 2<sup>r</sup> - 1.
		 */
		public static void write(char x, int r) {
			if (r == 8) {
				write(x);
				return;
			}
			if (r < 1 || r > 16)
				throw new IllegalArgumentException("Illegal value for r = " + r);
			if (x >= (1 << r))
				throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
			for (int i = 0; i < r; i++) {
				boolean bit = ((x >>> (r - i - 1)) & 1) == 1;
				writeBit(bit);
			}
		}

		/**
		 * Write the string of 8-bit characters to standard output.
		 * 
		 * @param s
		 *            the <tt>String</tt> to write.
		 * @throws IllegalArgumentException
		 *             if any character in the string is not between 0 and 255.
		 */
		public static void write(String s) {
			for (int i = 0; i < s.length(); i++)
				write(s.charAt(i));
		}

		/**
		 * Write the String of r-bit characters to standard output.
		 * 
		 * @param s
		 *            the <tt>String</tt> to write.
		 * @param r
		 *            the number of relevants bits in each character.
		 * @throws IllegalArgumentException
		 *             if r is not between 1 and 16.
		 * @throws IllegalArgumentException
		 *             if any character in the string is not between 0 and 2
		 *             <sup>r</sup> - 1.
		 */
		public static void write(String s, int r) {
			for (int i = 0; i < s.length(); i++)
				write(s.charAt(i), r);
		}

		/**
		 * Test client.
		 */
		public static void main(String[] args) {
			int m = Integer.parseInt(args[0]);

			// write n integers to binary standard output
			for (int i = 0; i < m; i++) {
				BinaryStdOut.write(i);
			}
			BinaryStdOut.flush();
		}

	}
}
