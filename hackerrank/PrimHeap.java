import java.util.*;


/**
 * Created by cutececil on 2017. 4. 14..
 */

public class PrimHeap {
    static int resa = 0;
    static int resb = 0;

    public static int[] mst(List<Edge>[] edges, int[] pred) {
        int n = edges.length;
        Arrays.fill(pred, -1);
        boolean[] used = new boolean[n];
        int[] prioa = new int[n];
        int[] priob = new int[n];
//        Arrays.fill(prioa, -1);
//        Arrays.fill(priob, -1);
//        prioa[0] = 0;
//        priob[0] = 0;

        PriorityQueue<Edge> q = new PriorityQueue<>((o1, o2) -> {
//            double adiv = o1.a / o1.b;
//            double bdiv = o2.a / o2.b;
//            if (adiv - bdiv > 0) return -1;
//            else if (adiv - bdiv < 0) return 1;
//            else return 0;

            double thisFrac = ((double) resa + o1.a) / ((double) resb + o1.b);
            double thatFrac = ((double) resa + o2.a) / ((double) resb + o2.b);

            if (thisFrac - thatFrac > 0) return -1;//
            else if (thisFrac - thatFrac < 0) return 1;
            else return 0;
        });
        Edge root = edges[0].get(0);
        q.add(new Edge(root.t, 0, 0, 0));
        resa = 0;
        resb = 0;

        while (!q.isEmpty()) {
            Edge cur = q.poll();
            int u = (int) cur.t;
            if (used[u])
                continue;
            used[u] = true;
            resa += cur.a;
            resb += cur.b;
            System.out.println("=====");
            System.out.println(u);
            System.out.println(cur.a + " " + cur.b);

            for (Edge e : edges[u]) {
                int v = e.t;
                double prio = (double) ((double) resa + prioa[v]) / ((double) (resa) + priob[v]);
                double cost = (double) ((double) resa + e.a) / ((double) resb + e.b);

                if (prioa[v] == 0 || Double.isNaN(prio)) {
                    prio = 0;
                }
                if (!used[v] && prio < cost) {
                    prioa[v] = e.a;
                    priob[v] = e.b;
                    pred[v] = u;
                    q.add(new Edge(v, 0, e.a, e.b));
                }
            }
        }
        return reduceFraction(resa, resb);
    }

    public static int[] reduceFraction(int bunja, int bunmo) {
        int[] frac = new int[2];
        frac[0] = bunja;
        frac[1] = bunmo;

        if (frac[1] == 0) {
            frac[0] = 0;
            frac[1] = 0;
            return frac;
        }

        int gcd_result = gcd(frac[0], frac[1]);

        frac[0] = frac[0] / gcd_result;
        frac[1] = frac[1] / gcd_result;

        return frac;
    }

    public static int gcd(int a, int b) {

        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }

        return Math.abs(a);
    }

    static class Edge {
        int t, cost;
        int a, b;

        public Edge(int t, int cost, int a, int b) {
            this.t = t;
            this.cost = cost;
            this.a = a;
            this.b = b;
        }
    }

    static long as = 0, bs = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();

        List<Edge>[] edges = new List[n];
        for (int i = 0; i < n; i++) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();
            edges[u].add(new Edge(v, 0, a, b));
            edges[v].add(new Edge(u, 0, a, b));
            as += a;
            bs += b;
        }
        int[] pred = new int[n];
        int[] ans = mst(edges, pred);
        System.out.println(ans[0] + "/" + ans[1]);
    }
}