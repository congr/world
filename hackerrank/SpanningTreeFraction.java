import java.util.Arrays;
import java.util.Scanner;
/**
 * Created by cutececil on 2017. 4. 13..
 */
// Kruskal - hill climbing
// https://www.hackerrank.com/contests/w31/challenges/spanning-tree-fraction
public class SpanningTreeFraction {
    static long as = 0, bs = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();

        as = 0;
        bs = 0;
        Graph graph = new Graph(n, m);
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();

            graph.edge[i].src = u;
            graph.edge[i].dest = v;
            graph.edge[i].a = a;
            graph.edge[i].b = b;
            as += a;
            bs += b;

            graph.edge[i].weight = 0;
        }

        float best = 0;
        while (true) {
            long keepa = as;
            long keepb = bs;

            int[] ans = graph.KruskalMST();
            as = ans[0];
            bs = ans[1];

            best = Math.max(best, (float) ans[0] / ans[1]);

            System.out.println(ans[0] + "/" + ans[1]);
            System.out.println(best);

            if (keepa == ans[0] && keepb == ans[1]) {
                break;
            }
        }

        System.out.println(as + "/" + bs);
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

    static class Graph {
        // A class to represent a graph edge
        class Edge implements Comparable<Edge> {
            int src, dest;
            int a, b;
            long weight;

            @Override
            public String toString() {
                return a + " " + b;
            }

            // Comparator function used for sorting edges based on
            // their weight
            public int compareTo(Edge that) {
                //float ab = as / bs;
                float thisFrac = ((float) as + this.a) / ((float) bs + this.b);
                float thatFrac = ((float) as + that.a) / ((float) bs + that.b);

                if (thisFrac - thatFrac > 0) return -1;//
                else if (thisFrac - thatFrac < 0) return 1;
                else return 0;
            }
        }

        // A class to represent a subset for union-find
        class subset {
            int parent, rank;
        }

        int V, E;    // V-> no. of vertices & E->no.of edges
        Edge edge[]; // collection of all edges

        // Creates a graph with V vertices and E edges
        Graph(int v, int e) {
            V = v;
            E = e;
            edge = new Edge[E];
            for (int i = 0; i < e; ++i)
                edge[i] = new Edge();
        }

        // A utility function to find set of an element i
        // (uses path compression technique)
        int find(subset subsets[], int i) {
            // find root and make root as parent of i (path compression)
            if (subsets[i].parent != i)
                subsets[i].parent = find(subsets, subsets[i].parent);

            return subsets[i].parent;
        }

        // A function that does union of two sets of x and y
        // (uses union by rank)
        void Union(subset subsets[], int x, int y) {
            int xroot = find(subsets, x);
            int yroot = find(subsets, y);

            // Attach smaller rank tree under root of high rank tree
            // (Union by Rank)
            if (subsets[xroot].rank < subsets[yroot].rank)
                subsets[xroot].parent = yroot;
            else if (subsets[xroot].rank > subsets[yroot].rank)
                subsets[yroot].parent = xroot;

                // If ranks are same, then make one as root and increment
                // its rank by one
            else {
                subsets[yroot].parent = xroot;
                subsets[xroot].rank++;
            }
        }

        // The main function to construct MST using Kruskal's algorithm
        int[] KruskalMST() {
            Edge result[] = new Edge[V];  // Tnis will store the resultant MST
            int e = 0;  // An index variable, used for result[]
            int i = 0;  // An index variable, used for sorted edges
            for (i = 0; i < V; ++i)
                result[i] = new Edge();

            // Step 1:  Sort all the edges in non-decreasing order of their
            // weight.  If we are not allowed to change the given graph, we
            // can create a copy of array of edges
            Arrays.sort(edge);
            //System.out.println(Arrays.toString(edge));

//            Arrays.sort(edge, new Comparator<Edge>() {
//                @Override
//                public int compare(Edge o1, Edge o2) {
//                    return o1.b - o2.b;
//                }
//            });

            // Allocate memory for creating V ssubsets
            subset subsets[] = new subset[V];
            for (i = 0; i < V; ++i)
                subsets[i] = new subset();

            // Create V subsets with single elements
            for (int v = 0; v < V; ++v) {
                subsets[v].parent = v;
                subsets[v].rank = 0;
            }

            i = 0;  // Index used to pick next edge

            // Number of edges to be taken is equal to V-1
            while (e < V - 1) {
                // Step 2: Pick the smallest edge. And increment the index
                // for next iteration
                Edge next_edge = new Edge();
                next_edge = edge[i++];

                int x = find(subsets, next_edge.src);
                int y = find(subsets, next_edge.dest);

                // If including this edge does't cause cycle, include it
                // in result and increment the index of result for next edge
                if (x != y) {
                    result[e++] = next_edge;
                    Union(subsets, x, y);
                }
                // Else discard the next_edge
            }

            // print the contents of result[] to display the built MST
            // System.out.println("Following are the edges in the constructed MST");
            int aSum = 0, bSum = 0;
            for (i = 0; i < e; ++i) {
                // System.out.println(result[i].src + " -- " + result[i].dest + " == " + result[i].weight + "(" + result[i].a + " " + result[i].b + ")");
                aSum += result[i].a;
                bSum += result[i].b;
            }

            int[] ans = reduceFraction(Math.abs(aSum), Math.abs(bSum));

            return ans;
        }
    }
}

