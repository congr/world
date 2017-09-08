import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 8. 17..
 */
public class P3_ConnectingPoints {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201708/P3/sample.in"; // path from root
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
            int L = sc.nextInt();

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

            int E = 100000;//N * M + N * L + N * N;  // Number of edges in graph
            List<PrimHeap.Edge>[] edges = new List[E];
            for (int i = 0; i < E; i++) {
                edges[i] = new ArrayList<>();
            }
            System.out.println("E " + E);
            int ecnt = 0;

            int[] Lcare = new int[E];
            int[] Mcare = new int[E];
            for (int i = 0; i < N; i++) { // N 보라
                for (int j = 0; j < L; j++) { // L 빨강
                    //if (Lcare[Ls[j]] > 0 && Lcare[Ls[j]] > Math.abs(Ns[i] - Ls[j])) {
                        edges[Ns[i]].add(new PrimHeap.Edge(Ns[i], Ls[j], Math.abs(Ns[i] - Ls[j]), 'L'));
                        edges[Ls[j]].add(new PrimHeap.Edge(Ls[j], Ns[i], Math.abs(Ns[i] - Ls[j]), 'L'));
                        ecnt++;
                        Lcare[Ls[j]] = Math.abs(Ns[i] - Ls[j]);
                    //}
                }

                for (int j = 0; j < M; j++) { // M 파랑
                   // if (Mcare[Ms[j]] > 0 && Mcare[Ms[j]] > Math.abs(Ns[i] - Ls[j])) {
                        edges[Ns[i]].add(new PrimHeap.Edge(Ns[i], Ms[j], Math.abs(Ns[i] - Ms[j]), 'M'));
                        edges[Ms[j]].add(new PrimHeap.Edge(Ms[j], Ns[i], Math.abs(Ns[i] - Ms[j]), 'M'));
                        ecnt++;
                        Mcare[Ms[j]] = Math.abs(Ns[i] - Ms[j]);
                    //}
                }

                for (int j = 0; j < N; j++) {
                    if (i != j) {
                        edges[Ns[i]].add(new PrimHeap.Edge(Ns[i], Ns[j], Math.abs(Ns[i] - Ns[j]), 'N'));
                        ecnt++;
                    }
                }
                System.out.println("ecnt " + ecnt);
            }

            int[] pred = new int[E];
            System.out.println(PrimHeap.mst(edges, pred));

            int result = 0;

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static public class PrimHeap {

        public static long mst(List<Edge>[] edges, int[] pred) {
            int n = edges.length;
            Arrays.fill(pred, -1);
            boolean[] used = new boolean[n];
            int[] prio = new int[n];
            int[] color = new int[n];
            boolean[] vertexUsed = new boolean[n];
            Arrays.fill(prio, Integer.MAX_VALUE);
            prio[0] = 0;
            PriorityQueue<Long> q = new PriorityQueue<>();
            q.add(0L);
            long res = 0;

            while (!q.isEmpty()) {
                long cur = q.poll();
                int u = (int) cur;
                if (used[u])
                    continue;
                used[u] = true;

                res += cur >>> 32;
                 System.out.println("res " + (cur >>> 32) + " u " + u);

                for (Edge e : edges[u]) {
                    int v = e.t;

                    if (!used[v] && prio[v] > e.cost) {
                        prio[v] = e.cost;
                        pred[v] = u;
                        color[v] = e.color;

                        q.add(((long) prio[v] << 32) + v);
                       //System.out.println("u " + u + " v " + v + " w " + e.cost + " color " + e.color);
                    }
                }
            }
            return res;
        }

        static class Edge {
            int s, t, cost;
            char color;
            boolean used;

            public Edge(int s, int t, int cost, char color) {
                this.s = s;
                this.t = t;
                this.cost = cost;
                this.color = color;
                System.out.println("s " + s + " t " + t + " w " + cost + " color " + color);
            }
        }

        // Usage example
        //public static void main(String[] args) {
        //    int[][] cost = { { 0, 1, 2 }, { 1, 0, 3 }, { 2, 3, 0 } };
        //    int n = cost.length;
        //    List<Edge>[] edges = new List[n];
        //    for (int i = 0; i < n; i++) {
        //        edges[i] = new ArrayList<>();
        //        for (int j = 0; j < n; j++) {
        //            if (cost[i][j] != 0) {
        //                edges[i].add(new Edge(j, cost[i][j]));
        //            }
        //        }
        //    }
        //    int[] pred = new int[n];
        //    System.out.println(mst(edges, pred));
        //}
    }
}
