import java.util.Arrays;

public class EditDist {
        static public int minDistance(String word1, String word2) {
            int m = word1.length(), n = word2.length();
            if (m == 0) return n;
            if (n == 0) return m;

            int[][] d = new int[m+1][n+1];
            //for (int i = 0; i < m; i++) Arrays.fill(d[i], -1);

            System.out.println(edit(d, word1, word2, m, n)); // !!! top-down

            for (int i = 0; i < m; i++) {
                System.out.println(Arrays.toString(d[i]));
            }
            return d[m-1][n-1];
        }

       static Integer edit(int[][] d, String a, String b, int u, int v) {
            if(u == 0) return v; // return a remaining count
            if(v == 0) return u;

            if (d[u][v] > 0) return d[u][v];

            if (a.charAt(u-1) == b.charAt(v-1))
                d[u][v] = edit(d, a, b, u-1, v-1);
            else {
                int insert = edit(d, a, b, u-1, v);
                int delete = edit(d, a, b, u, v-1);
                int replace = edit(d, a, b, u-1, v-1);
                d[u][v] = 1 + Math.min(insert, Math.min(delete, replace));
            }
            return d[u][v];
        }

    public static void main(String[] args) {
        System.out.println(minDistance("sunday", "saturday"));



        System.out.println(minDistance("", "a"));
    }
}
