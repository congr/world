import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 9..
 */
public class P3_로마의양치기 {
    static final int MAX = 10001;
    
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201705/P3/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);
        
        // logic starts here
        
        int[][] D = new int[MAX][MAX];
        D[1][1] = D[4][3] = D[5][2] = D[9][3] = D[10][2] = 1;
        
        for (int i = 2; i < MAX; i++) {
            for (int j = 1; j < MAX; j++) {
                if (i > 1 && j > 1) D[i][j] += D[i - 1][j - 1];
                if (i > 4 && j > 3) D[i][j] += D[i - 4][j - 3];
                if (i > 5 && j > 2) D[i][j] += D[i - 5][j - 2];
                if (i > 9 && j > 3) D[i][j] += D[i - 9][j - 3];
                if (i > 10 && j > 2) D[i][j] += D[i - 10][j - 2];
            }
        }
        
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int K = sc.nextInt();
            
            String result = D[N][K] > 0 ? "O" : "X";
            System.out.println(result);
            wr.write(result + "\n");
        }
        
        sc.close();
        wr.close();
    }
}
