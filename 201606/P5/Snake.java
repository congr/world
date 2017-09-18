import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Snake {
    static int[] genders = new int[2001];
    static int[] energes = new int[2001];
    //    static int[] blocked = new int[2001];
    static int[][] pre = new int[2001][2001];
    //    static long[][] d = new long[2001][2001];
    static long[] cache = new long[1 << 20];

    public static void main(String[] args) throws Exception {
        String filename = (args.length > 0) ?  args[0] : "input003.txt";
        Scanner sc = new Scanner(new File(filename));
        // String out = filename.replace("in", "out");
        // FileWriter wr = new FileWriter(new File(out));

        int tc = sc.nextInt();
        while (tc-- > 0) {

            // initialize
            Arrays.fill(genders, -1);
            Arrays.fill(energes, -1);
//            Arrays.fill(blocked, 0);
            Arrays.fill(cache, 0);
//            for (int i = 0; i < 2001; i++)
//                Arrays.fill(d[i], -1);

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

            long ret = solve(-1, 0, N);

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

//		System.out.println("-----");
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < N; j++) {
//				System.out.print(pre[i][j] + "\t");
//			}
//			System.out.println();
//		}
    }

    // 환형 고
//    static boolean isBlocked(int male, int female, int N) {
//        boolean inBlocked = false;
//        boolean outBlocked = false;
//
//        int a = Math.min(male, female);
//        int b = Math.max(male, female);
//
//        for (int i = a + 1; i < b; i++) {
//            if (blocked[i] == 1) {
//                inBlocked = true;
//                break;
//            }
//        }
//
//        for (int i = a - 1; i >= 0; i--) {
//            if (blocked[i] == 1) {
//                outBlocked = true;
//                break;
//            }
//        }
//        for (int i = b + 1; i < N; i++) {
//            if (blocked[i] == 1) {
//                outBlocked = true;
//                break;
//            }
//        }
//
//        return inBlocked && outBlocked;
//    }

    static boolean isBlocked(int male, int female, int N, int bit) {
        boolean inBlocked = false;
        boolean outBlocked = false;

        int a = Math.min(male, female);
        int b = Math.max(male, female);

        for (int i = a + 1; i < b; i++) {
            if ((bit & (1 << i)) == 1) {
//            if (blocked[i] == 1) {
                inBlocked = true;
                break;
            }
        }

        for (int i = a - 1; i >= 0; i--) {
//            if (blocked[i] == 1) {
            if ((bit & (1 << i)) == 1) {
                outBlocked = true;
                break;
            }
        }
        for (int i = b + 1; i < N; i++) {
//            if (blocked[i] == 1) {
            if ((bit & (1 << i)) == 1) {
                outBlocked = true;
                break;
            }
        }

        return inBlocked && outBlocked;
    }


//    static int bit;

    static long solve(int male, int taken, int N) {
        male = getNextMale(male, taken, N);
        if (male == N)
            return 0;

        if (male >= 0 && cache[taken] != 0) return cache[taken];

        long temp = 0;
        for (int j = 0; j < N; j++) {
            //if (blocked[male] == 0 && blocked[j] == 0) {
            if ((taken & (1 << male)) == 0 && (taken & (1 << j)) == 0) {
//                bit |= (1 << male);
//                bit |= (1 << j);
//                blocked[male] = blocked[j] = 1;
                int take = (1 << j);
                take += (1 << male);
                temp = solve(male, taken | take, N);
                if (!isBlocked(male, j, N, taken))
                    temp += pre[male][j];
                cache[taken] = Math.max(cache[taken], temp);
//                blocked[male] = blocked[j] = 0;
//                bit &= ~(1 << male);
//                bit &= ~(1 << j);
            }
        }

        return cache[taken];
    }

    static int getNextMale(int male, int taken, int N) {
        int next = male + 1;

        if (next == N) return N;//
        for (int i = next; i < N; i++) {
//            if (blocked[i] == 0) {
            if ((taken & (1 << i)) == 0) {
                next = i;
                break;
            }
        }

        //åSystem.out.println("next " + next);
        return next;
    }
}
