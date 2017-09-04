import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 3..
 */
public class P3_볼록사각형 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201609/P3/input5.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);
        
        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            
            Point2D[] pt = new Point2D[N];
            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                pt[i] = new Point2D(x, y);
            }
            
            Arrays.sort(pt);
            double minArea = Double.MAX_VALUE;
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    for (int k = j + 1; k < N; k++) {
                        for (int l = k + 1; l < N; l++) {
                            Point2D[] convexQuad = new Point2D[]{pt[i], pt[j], pt[k], pt[l]};
                            Boolean isConvexQuad = isConvexQuadrilateral(convexQuad);
                            if (isConvexQuad) {
                                //System.out.println(Arrays.toString(convexQuad));
                                
                                double area = Point2D.polygonArea(convexQuad);
                                if (area != 0)
                                    minArea = Math.min(area, minArea);
                            }
                        }
                    }
                }
            }
            
            String result = String.format("%.1f", minArea); // 100.25 -> 100.3
            
            System.out.println(result);
            wr.write(result + "\n");
        }
        
        sc.close();
        wr.close();
    }
    
    static boolean isConvexQuadrilateral(Point2D[] points) {
        //Arrays.sort(points); // points input이 이미 소팅된 상태라면 다시 소팅할 필요 없다
        Arrays.sort(points, 1, points.length, points[0].polarOrder());
        
        if (Point2D.ccw(points[0], points[1], points[2]) <= 0) // ccw가 아니면
            return false;
        if (Point2D.ccw(points[1], points[2], points[3]) <= 0) // ccw가 아니면
            return false;
        if (Point2D.ccw(points[2], points[3], points[0]) <= 0) // ccw가 아니면
            return false;
        if (Point2D.ccw(points[3], points[0], points[1]) <= 0) // ccw가 아니면
            return false;
        
        return true;
    }
}
