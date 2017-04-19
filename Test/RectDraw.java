/******************************************************************************
 *  Compilation:  javac Point2DRandom.java
 *  Execution:    java Point2DRandom x0 y0 n
 *  Dependencies: StdDraw.java StdRandom.java
 *
 *  Immutable point data type for points in the plane.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Scanner;

public final class RectDraw {
    public static void main(String[] args) throws Exception {
//        Scanner sc = new Scanner(new File("Test/pointdata.txt"));
        Scanner sc = new Scanner(System.in);

        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 10); // 입력이 큰 수가 들어올 수록 max scale을 올려라 최고값이 40000일 경우 300 ~ 400정도.
        StdDraw.setYscale(0, 10);
        StdDraw.setPenRadius(0.0005);
//        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.enableDoubleBuffering();

        int N = sc.nextInt();

        // Ruler
        StdDraw.setPenColor(StdDraw.GRAY);
        int gridSize = 1000;
        for (int i = 0; i < gridSize; i++) {
            StdDraw.line(0, i, gridSize, i); // horizontal line
            StdDraw.line(i, 0, i, gridSize); // vertical line

        }

        StdDraw.setPenColor(StdDraw.BLUE);
        for (int i = 0; i < N; i++) {
            double x1 = Double.valueOf(sc.nextInt());
            double y1 = Double.valueOf(sc.nextInt());
            double x2 = Double.valueOf(sc.nextInt());
            double y2 = Double.valueOf(sc.nextInt());

            if (x1 > x2 || y1 > y2) {
                double t = x1;
                x1 = x2;
                x2 = t;

                t = y1;
                y1 = y2;
                y2 = t;
            }

            double cx = (x1 + x2) / 2;
            double cy = (y1 + y2) / 2;
            double hw = (x2 - x1) / 2;
            double hh = (y2 - y1) / 2;
            System.out.println(cx + " " + cy + " " + hw + " " + hh);
            StdDraw.rectangle(cx, cy, hw, hh); // center x, center y, half width, half height
        }
        StdDraw.show();

        // draw line segments from p to each point, one at a time, in polar order
//        StdDraw.setPenRadius();
//        StdDraw.setPenColor(StdDraw.BLUE);
//        Arrays.sort(points, p.polarOrder());
//        for (int i = 0; i < n; i++) {
//            p.drawTo(points[i]);
//            StdDraw.show();
//            StdDraw.pause(100);
//        }
    }
}