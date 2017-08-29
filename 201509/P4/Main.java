/**
 * Created by cutececil on 2017. 8. 29..
 */
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("201509/P4/mid.in"));
        FileWriter wr = new FileWriter(new File("201509/P4/mid_old.out"));
        Main m = new Main();
        
        int tc = sc.nextInt();
        while (tc-- > 0) {
            // input tc #
            int N = sc.nextInt(); // 선의 수.
            
            Solution sol = m.new Solution();
            for (int i = 0; i < N + 1; i++) { // 주어진 점의 수는 "선의 수 + 1"
                int p1 = sc.nextInt();
                int p2 = sc.nextInt();
                if (i == 0) {// 왕자 출발 좌표.
                    sol.setPrincePoint(p1, p2);
                }
            }
            
            for (int i = 0; i < N + 1; i++) {
                int p1 = sc.nextInt();
                int p2 = sc.nextInt();
                sol.setPrincessPoint(p1, p2);
            }
            
            int thisDay = sol.calc();
            // File write
            System.out.println(thisDay);
            wr.write(thisDay + "\n");
        }
        
        sc.close();
        wr.close();
    }
    
    class Solution {
        class Point {
            int x, y;
            
            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
        
        int a, b;
        ArrayList<Point> list;
        
        public Solution() {
            list = new ArrayList<Point>();
        }
        
        public void setPrincePoint(int a, int b) {
            this.a = a;
            this.b = b;
        }
        
        int day;
        
        // a, b 이전 좌표, c,d 새로운 좌표, 두 좌표사이의 점을 알아내서 list에 추가
        public void setPrincessPoint(int c, int d) {
            if (day > 0) {
                Point prev = list.get(day - 1);
                int a = prev.x;
                int b = prev.y;
                while (!(a == c && b == d)) {
                    if (a != c) {
                        if (a > c)
                            a--;
                        else
                            a++;
                    }
                    if (b != d) {
                        if (b > d)
                            b--;
                        else
                            b++;
                    }
                    
                    list.add(new Point(a, b));
                }
            } else
                list.add(new Point(c, d));
            
            day = list.size();
        }
        
        public int calc() {
            // for (Point point : list) {
            // System.out.println(point.x + " " + point.y);
            // }
            
            int thisDay = 0;
            for (int i = 0; i < day; i++) {
                int c = list.get(i).x, d = list.get(i).y;
                if (i >= dist(a, b, c, d)) {
                    thisDay = i + 1; // 1일차부터 카운트해야하는데, i(=day) 를 list 0부터 봤으므로,
                    // +1
                    return thisDay;
                }
            }
            
            return -1; // 못만난 경우.
        }
        
        public int dist(int a, int b, int c, int d) {
            return Math.max(Math.abs(a - c), Math.abs(b - d));
        }
    }
}