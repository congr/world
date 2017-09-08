import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 8. 18..
 */
public class P3_ConnectingPoints_UnionFind {
    static public int MAX = 10000;

    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201708/P3/Set1.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int L = sc.nextInt();
            int M = sc.nextInt();
            int N = sc.nextInt();

            int[] Ns = new int[N];
            int[] Ms = new int[M];
            int[] Ls = new int[L];

            for (int i = 0; i < L; i++) { // 빨강
                Ls[i] = sc.nextInt();
            }

            for (int i = 0; i < M; i++) { // 파랑
                Ms[i] = sc.nextInt();
            }

            for (int i = 0; i < N; i++) { // 보라
                Ns[i] = sc.nextInt();
            }

            Arrays.sort(Ls);
            Arrays.sort(Ms);
            Arrays.sort(Ns);

            // mst
            int[] p = new int[MAX];
            for (int i = 0; i < MAX; i++) {
                p[i] = i;
            }

            ArrayList<Edge> edges = new ArrayList<Edge>();
            //for (int i = 0; i < N; i++) {
            //    for (int j = 0; j < L; j++) {
            //        edges.add(new Edge(i, Ls[j], Math.abs(Ns[i] - Ls[j]), 'L'));
            //    }
            //    for (int j = 0; j < M; j++) {
            //        edges.add(new Edge(Ns[i], Ms[j], Math.abs(Ns[i] - Ms[j]), 'M'));
            //    }
            //    for (int j = 0; j < N; j++) {
            //        if (i != j) edges.add(new Edge(Ns[i], Ns[j], Math.abs(Ns[i] - Ns[j]), 'N'));
            //    }
            //}

            for (int j = 0; j < M; j++) {
                for (int i = j + 1; i < M; i++) {
                    //if (i != j)
                    edges.add(new Edge(j, i, Math.abs(Ms[i] - Ms[j]), 'M'));
                }
            }

            for (int j = 0; j < L; j++) {
                for (int i = j + 1; i < L; i++) {
                    //if (i != j)
                    edges.add(new Edge(j, i, Math.abs(Ls[i] - Ls[j]), 'M'));
                }
            }

            Collections.sort(edges, new Compare());
            long ans = 0;
            for (Edge e : edges) {
                int x = find(p, e.start);
                int y = find(p, e.end);
                if (x != y) {
                    {
                        disjoint_union(p, e.start, e.end);
                        ans += e.cost;
                        //System.out.println("cost " + e.cost + " s " + e.start + " e " + e.end);

                        //if (e.color == 'M' || e.color == 'L') {
                        //con[e.start] = 1;
                        //con[e.end] = 1;
                        //con[e.start] = 1;
                        //}
                    }
                }
            }

            long result = ans;

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static class Compare implements Comparator<Edge> {
        public int compare(Edge one, Edge two) {
            return Long.compare(one.cost, two.cost);
        }
    }

    public static void disjoint_union(int[] p, int x, int y) {
        x = find(p, x);
        y = find(p, y);
        p[x] = y;
    }

    public static int find(int[] p, int x) {
        if (x == p[x]) {
            return x;
        } else {
            return (p[x] = find(p, p[x]));
        }
    }

    static class Edge {
        public int start;
        public int end;
        public long cost;
        char color;

        public Edge() {
            this(0, 0, 0, ' ');
        }

        public Edge(int start, int end, long cost, char color) {
            this.start = start;
            this.end = end;
            this.cost = cost;
            this.color = color;
        }
    }

}
