import java.util.Scanner;

/**
 * Created by cutececil on 2017. 11. 14..
 */
public class WOC35_2_TripleRecursion {
    static void tripleRecursion(int n, int m, int k) {
        int a[][] = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) a[i][j] = m;
                else if (i == j) a[i][j] = a[i - 1][j - 1] + k;
                else if (i > j) a[i][j] = a[i - 1][j] - 1;
                else if (i < j) a[i][j] = a[i][j - 1] - 1;
            }
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        tripleRecursion(n, m, k);
        in.close();
    }
}
