import java.io.File;
import java.util.Scanner;

public class IDTDemo {
    static int[] input;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("sample.in"));
        int tc = sc.nextInt();
        while (tc-- > 0) {
            int N = sc.nextInt();
            input = new int[N];
            for (int i = 0; i < N; i++)
                input[i] = sc.nextInt();
            IDT.buildIDT(input);

            int ret = IDT.rmqMin(sc.nextInt(), sc.nextInt());
            System.out.println(ret);
        }

        sc.close();
    }

    // segment Tree
    static class IDT {
        static int IDT[] = new int[1 << 18];
        static int IDT2[] = new int[1 << 18];
        static int base;

        static void buildIDT(int[] input) {
            int size = input.length;
            for (base = 1; base < size; base *= 2)
                ;

            for (int i = base, j = 0; i < size + base; i++, j++) {
                IDT[i] = input[j];
            }

            for (int i = base - 1; i > 0; i--) {
                IDT[i] = Math.min(IDT[2 * i], IDT[2 * i + 1]);
                IDT2[i] = (IDT[i] == IDT[2 * i]) ? 2 * i : 2 * i + 1;
                // IDT[i] = IDT[2 * i] + IDT[2 * i + 1]; // sum
            }
        }

        static int rmqSum(int qs, int qe) {
            int bs = base + qs;
            int be = base + qe;
            int sum = 0;

            while (bs < be) {
                if (bs % 2 == 1) {
                    sum += IDT[bs];
                    bs++;
                }
                if (be % 2 == 0) {
                    sum += IDT[be];
                    be--;
                }
                bs >>= 1;
                be >>= 1;
            }
            if (bs == be)
                sum += IDT[bs];
            return sum;
        }

        static int rmqMin(int qs, int qe) {
            int bs = base + qs;
            int be = base + qe;
            int min = 987654321;

            while (bs < be) {
                if (bs % 2 == 1) {
                    min = Math.min(min, IDT[bs]);
                    bs++;
                }
                if (be % 2 == 0) {
                    min = Math.min(min, IDT[be]);
                    be--;
                }
                bs >>= 1;
                be >>= 1;
            }
            if (bs == be)
                min = Math.min(min, IDT[bs]);
            return min;
        }
    }
}
