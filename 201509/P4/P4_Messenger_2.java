import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 29..
 */
// 2015 본선 4번 메신저
// 왕자 베이스점에서 공주 순시일이 오버랩되는 선분 구간에 대해서만 모든점을 만들어 보는 방식 (mid 까지 가능, larges는 input set없음)
public class P4_Messenger_2 {
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
            
            Path base = prince[0];
            int minDay = -1;
            prcess[0].d = 1;
            
            for (int i = 0; i < N; i++) {
                prince[i].d = base.dist(prcess[i]) + 1; // 왕자 거리는 왕자 첫번째 지점에서 공주 i지점까지 거리
                if (i == 0) continue;
                prcess[i].d = prcess[i - 1].d + prcess[i].dist(prcess[i - 1]);
                int min = Math.min(prince[i].d, prince[i - 1].d);
                int max = Math.max(prince[i].d, prince[i - 1].d);
                if (intersects(prcess[i - 1].d, prcess[i].d, min, max)) {
                    int d = calcAllDist(prcess[i - 1], prcess[i], base);
                    if (d > 0) {
                        minDay = d;
                        break;
                    }
                }
            }
            
            int result = minDay;
            
            System.out.println(result);
            wr.write(result + "\n");
        }
        
        sc.close();
        wr.close();
    }
    
    // 공주 시작 - 끝 선분에서 모든점을 구한다 왕자 base에서 거리를 구하고 공주가 크거나 같아지는 인덱스를 리턴.
    static int calcAllDist(Path start, Path end, Path base) { // 공주 선분 시작-끝, 왕자 시작점
        //System.out.println(start + " ~ " + end + " base " + base);
        int M = dist(start.x, start.y, end.x, end.y) + 1;
        Path[] paths = new Path[M]; // 공주 모든점
        
        int dx = 0, dy = 0;
        if (start.x > end.x) dx = -1;
        else if (start.x < end.x) dx = 1;
        if (start.y > end.y) dy = -1;
        else if (start.y < end.y) dy = 1;
        
        int x = start.x;
        int y = start.y;
        int d = start.d;
        for (int i = 0; i < M; i++) {
            int w = dist(base.x, base.y, x, y) + 1; // 왕자베이스에서 공주 이점까지 도달일수
            if (w <= d) return d;
            x += dx;
            y += dy;
            d += 1;
        }
        return -1;
    }
    
    static boolean intersects(int from1, int to1, int from2, int to2) {
        return from1 <= from2 && to1 >= from2   //  (.[..)..] or (.[...]..)
                || from1 >= from2 && from1 <= to2; // [.(..]..) or [..(..)..
    }
    
    static int dist(int x1, int y1, int x2, int y2) {
        return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
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
