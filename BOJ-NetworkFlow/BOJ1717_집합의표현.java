import java.util.Scanner;

/**
 * Created by cutececil on 2017. 4. 18..
 */
// Union Find
public class BOJ1717_집합의표현 {
    public static void main(String[] args) { // class Solution
        Scanner in = new Scanner(System.in);

        int N = in.nextInt();
        int M = in.nextInt();
        DisjointSet ds = new DisjointSet(N + 1);

        for (int i = 0; i < M; i++) {
            int op = in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();

            if (op == 0) { // union
                ds.union(a, b);

            } else if (op == 1) { // find
                int aRoot = ds.find(a);
                int bRoot = ds.find(b);
                System.out.println(aRoot == bRoot ? "YES" : "NO");
            }
        }
    }
}

// http://www.geeksforgeeks.org/disjoint-set-data-structures-java-implementation/
class DisjointSet {
    int[] rank, parent;
    int n, count;

    public DisjointSet(int n) {
        rank = new int[n];
        parent = new int[n];
        count = n;                                  // number of components
        this.n = n;

        makeSet(n);
    }

    void makeSet(int n) {
        for (int i = 0; i < n; i++) {
            parent[i] = i;                          // Initially, all elements are in their own set.
        }
    }

    // Returns representative of x's set
    int find(int x) {
        if (parent[x] != x) {                       // Finds the representative of the set that x is an element of
            parent[x] = find(parent[x]);            // if x is not the parent of itself Then x is not the representative of his set,
            // so we recursively call Find on its parent
            // and move i's node directly under the representative of this set
        }
        return parent[x];
    }

    // Unites the set that includes x and the set that includes x
    void union(int x, int y) {
        int xRoot = find(x), yRoot = find(y);       // Find representatives of two sets

        if (xRoot == yRoot)                         // Elements are in the same set, no need to unite anything.
            return;

        if (rank[xRoot] < rank[yRoot])              // If x's rank is less than y's rank
            parent[xRoot] = yRoot;                  // Then move x under y  so that depth of tree remains less

        else if (rank[yRoot] < rank[xRoot])         // Else if y's rank is less than x's rank
            parent[yRoot] = xRoot;                  // Then move y under x so that depth of tree remains less

        else {                                      // if ranks are the same
            parent[yRoot] = xRoot;                  // Then move y under x (doesn't matter which one goes where)
            rank[xRoot] = rank[xRoot] + 1;          // And increment the the result tree's rank by 1
        }

        count--;                                    // number of components
    }

    int count() {
        return count;
    }
}
