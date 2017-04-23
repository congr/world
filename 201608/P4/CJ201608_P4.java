import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 4. 23..
 */
// 유적지 - convex hull 두 개로 나누는데 최소 둘레길이를 출력
public class CJ201608_P4 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201608/P4/input001.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);
        
        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            
            Point2D[] pts = new Point2D[N];
            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                pts[i] = new Point2D(x, y);
            }
            
            if (pts.length <= 3) {
                System.out.println("0.00000");
                continue;    
            }
            
            // 1. sort points y - x order
            Arrays.sort(pts);
            System.out.println(Arrays.toString(pts));
            
            // 2. 중앙 두점을 분리하여 polar order sorting
            Point2D[] downside = Arrays.copyOfRange(pts, 0, N / 2);
            Point2D[] upside = Arrays.copyOfRange(pts, N / 2, N);
            Arrays.sort(downside, downside[0].polarOrder());
            Arrays.sort(upside, upside[0].polarOrder());
    
            System.out.println(Arrays.toString(downside));
            System.out.println(Arrays.toString(upside));
            
            //Arrays.sort(pts, 1, N / 2, pts[0].polarOrder());
            //Arrays.sort(pts, N / 2 + 1, N - 1, pts[N / 2 + 1].polarOrder());
            
            double downPerimeter = Point2D.polygonPerimeter(downside);
            double upPerimeter = Point2D.polygonPerimeter(upside);
            
            System.out.println("down: " + downPerimeter + ", up:" + upPerimeter);
            String result = String.format("%.5f", downPerimeter + upPerimeter); // 100.123456 -> 100.12346
            System.out.println(result);
            wr.write(result + "\n");
            //System.out.println(cnt);
        }
        
        sc.close();
        wr.close();
    }
}
