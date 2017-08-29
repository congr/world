import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 29..
 */
// 2015 본선 4번 메신저
// 왕자 - 공주 모든 점(선분 상의 모든 점을 만들어서)에 대해 왕자 1일째점에서 거리를 계산하는 방식 (느림)
public class P4_Messenger {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201509/P4/mid.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);
        
        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt() + 1;
            
            Path[] prince = new Path[N];
            Path[] prcess = new Path[N];
            for (int i = 0; i < N; i++) prince[i] = new Path(sc.nextInt(), sc.nextInt());
            for (int i = 0; i < N; i++) prcess[i] = new Path(sc.nextInt(), sc.nextInt());
            
            int minDay = -1;
            prcess[0].d = 1; // 공주는 날짜를 첫날을 1로 초기화
            
            ArrayList<Path> paths = new ArrayList<>();
            paths.add(new Path(prcess[0].x, prcess[0].y));
            for (int i = 1; i < N; i++) {
                if (i > 0) {
                    generatePath(paths, prcess[i - 1], prcess[i], prince[0]);
                }
            }
            
            for (int i = 0; i < paths.size(); i++) {
                int dist = prince[0].dist(paths.get(i));
                if (dist < i) {
                    minDay = i;
                    break;
                }
            }
            
            //System.out.println(paths);
            
            //// System.out.println(Arrays.toString(prince));
            //// System.out.println(Arrays.toString(prcess));
            
            int result = minDay;
            
            System.out.println(result);
            wr.write(result + "\n");
        }
        
        sc.close();
        wr.close();
    }
    
    /*static int getMinDay(int u, int v, int p, int q) {// 공주 시작-끝점, 왕자 시작-끝점
        //// System.out.println(u + "~" + v + " " + p + "~" + q);
        int N = Math.abs(q - p) + 1;
        int M = Math.abs(u - v) + 1;
        
        if (N != M) {
            // System.out.println("out");
            
        }
        int uvGap = 1, pqGap = 1;
        if (u > v) uvGap = -1;// 시작점이 더 크면 -1씩 한다
        if (p > q) pqGap = -1;
        
        int s1 = u, s2 = p;
        for (int i = 0; i < N; i++) {
            if (s1 >= s2) return s1;
            s1 += uvGap;
            s2 += pqGap;
        }
        return -1;
    }*/
    
    static void generatePath(ArrayList<Path> al, Path start, Path end, Path base) {
        int M = Math.max(Math.abs(end.x - start.x), Math.abs(end.y - start.y));
        int dx = 0, dy = 0;
        if (start.x > end.x) dx = -1;
        else if (start.x < end.x) dx = 1;
        if (start.y > end.y) dy = -1;
        else if (start.y < end.y) dy = 1;
        
        int x = start.x;
        int y = start.y;
        for (int i = 0; i < M; i++) {
            al.add(new Path(x, y));
            x += dx;
            y += dy;
        }
        //System.out.println("al " + al);
    }
    
    static boolean intersects(int from1, int to1, int from2, int to2) {
        return from1 <= from2 && to1 >= from2   //  (.[..)..] or (.[...]..)
                || from1 >= from2 && from1 <= to2; // [.(..]..) or [..(..)..
    }
    
    static class Path {
        int x;
        int y;
        int d;
        
        Path(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        int dist(Path other) {
            return Math.max(Math.abs(other.x - x), Math.abs(other.y - y));
        }
        
        @Override
        public String toString() {
            return x + " " + y + " (" + d + ")";
        }
    }
}
