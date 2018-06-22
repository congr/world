import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 14..
 */
public class WOC33_3_TransformToPalindrome {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int m = in.nextInt();
        
        DisjointSet ds = new DisjointSet(n+1);
        
        for (int a0 = 0; a0 < k; a0++) {
            int x = in.nextInt();
            int y = in.nextInt();
            ds.union(x, y);
        }
        
        int[] a = new int[m];
        for (int a_i = 0; a_i < m; a_i++) {
            a[a_i] = in.nextInt();
        }
        
        int result = lps(a, ds);
        System.out.println(result);
    }
    
    
    // A utility function to get max of two integers
    static int max(int x, int y) {
        return (x > y) ? x : y;
    }
    
    // Returns the length of the longest palindromic subsequence in seq
    static int lps(int[] seq, DisjointSet ds) {
        int n = seq.length;
        int i, j, cl;
        int L[][] = new int[n][n];  // Create a table to store results of subproblems
        
        // Strings of length 1 are palindrome of lentgh 1
        for (i = 0; i < n; i++)
            L[i][i] = 1;
        
        // Build the table. Note that the lower diagonal values of table are
        // useless and not filled in the process. The values are filled in a
        // manner similar to Matrix Chain Multiplication DP solution (See
        // http://www.geeksforgeeks.org/archives/15553). cl is length of
        // substring
        for (cl = 2; cl <= n; cl++) {
            for (i = 0; i < n - cl + 1; i++) {
                j = i + cl - 1;
                int iRoot = ds.find(seq[i]);
                int jRoot = ds.find(seq[j]);
                if (iRoot == jRoot && cl == 2)
                    L[i][j] = 2;
                else if (iRoot == jRoot)
                    L[i][j] = L[i + 1][j - 1] + 2;
                else
                    L[i][j] = max(L[i][j - 1], L[i + 1][j]);
            }
        }
        
        return L[0][n - 1];
    }
    
    // http://www.geeksforgeeks.org/disjoint-set-data-structures-java-implementation/
    static class DisjointSet {
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
                parent[i] = i;                          // Initially, all elements are in their own setWithoutDup.
            }
        }
        
        // Returns representative of x's setWithoutDup
        int find(int x) {
            if (parent[x] != x) {                       // Finds the representative of the setWithoutDup that x is an element of
                parent[x] = find(parent[x]);            // if x is not the parent of itself Then x is not the representative of his setWithoutDup,
                // so we recursively call Find on its parent
                // and move i's node directly under the representative of this setWithoutDup
            }
            return parent[x];
        }
        
        // Unites the setWithoutDup that includes x and the setWithoutDup that includes x
        void union(int x, int y) {
            int xRoot = find(x), yRoot = find(y);       // Find representatives of two sets
            
            if (xRoot == yRoot)                         // Elements are in the same setWithoutDup, no need to unite anything.
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
}
