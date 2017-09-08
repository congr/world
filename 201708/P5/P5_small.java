import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 8. 19..
 */
public class P5_small {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201708/P5/Set2.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int M = sc.nextInt();

            int k = 0;//문명
            ArrayList<Probe> probes = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                int dx = sc.nextInt();
                int dy = sc.nextInt();

                probes.add(new Probe(x, y, k));
                for (int j = 1; j <= M; j++) {
                    probes.add(new Probe(x + (dx * j), y + (dy * j), k));
                }
                k++;
            }

            if (probes.size() != N * (M + 1))
                System.out.println("error");

            //System.out.println(probes);
            Collections.sort(probes, (o1, o2) -> {
                long a = o1.x - o2.x;
                if (a == 0) return (int) (o1.y - o2.y);
                else return (int) a;

            });
            //System.out.println(probes);

            long capsule = 0;
            for (int i = 0; i < probes.size(); i++) {
                Probe ip = probes.get(i);

                long preXdiff = -1;
                for (int j = i + 1; j < probes.size(); j++) {
                    if (i == j)
                        continue;
                    Probe jp = probes.get(j);
                    //if (ip.type == jp.type) continue;

                    long xdiff = Math.abs(ip.x - jp.x);

                    long ydiff = Math.abs(ip.y - jp.y);
                    if (xdiff > 2) {
                        break;
                    }
                    if (xdiff == preXdiff && ydiff > 2) {
                        //System.out.println(j+" ydiff break" + ip + " " + jp + ", " + preXdiff + " xdiff" + xdiff);
                        //break;
                    }
                    if (Math.max(xdiff, ydiff) <= 2 && ip.type != jp.type) {
                        capsule += 2;
                        //System.out.println("capsule " + capsule + " "+ ip + " -> " + jp);
                    }
                    preXdiff = xdiff;
                }
            }

            long result = capsule;

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static class Probe {
        long x, y, type;

        Probe(long x, long y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        @Override
        public String toString() {
            return "[" + x + ", " + y + "] ";
        }
    }
}
