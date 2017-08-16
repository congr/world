import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 16..
 */
// 화성지
// 전체 직사각형의 넓이 구하기
// https://blog.sisobus.com/2014/02/plane-sweeping.html#.WZOibXdJadS 참고
// http://jason9319.tistory.com/58
// http://www.crocus.co.kr/648
/* 입력
 2
 10 10 20 20
 15 15 25 30
* */
public class BOJ3392_PlaneSweeping {
    
    public static int MAX_N = 10000;
    public static int MAX_Y = 30000;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();
        RectInfo[] rect = new RectInfo[N * 2];
        //int[] Y = new int[100];
        for (int i = 0; i < N; i++) {
            int x1 = sc.nextInt();
            int y1 = sc.nextInt();
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();
            
            rect[i] = new RectInfo(x1, y1, y2, true);
            rect[i + N] = new RectInfo(x2, y1, y2, false);
        }
        
        Arrays.sort(rect);
        //System.out.println(Arrays.toString(rect));
        
        SegmentTree st = new SegmentTree();
        int sum = 0;
        
        st.updateCnt(rect[0].ys, rect[0].ye - 1, 1, 1, 0, MAX_Y);
        for (int i = 1; i < N * 2; i++) {
            int xDiff = rect[i].x - rect[i - 1].x;
            int ySum = st.tree[1]; // y 구간의 총합이 들어있음
            sum += xDiff * ySum;
            
            if (rect[i].isStarted)
                st.updateCnt(rect[i].ys, rect[i].ye - 1, 1, 1, 0, MAX_Y); // start, end 는 배열 index 0~부터 시작, 트리 노드번호는 1부터 시작
            else
                st.updateCnt(rect[i].ys, rect[i].ye - 1, -1, 1, 0, MAX_Y);
        }
        
        System.out.println(sum);
    }
    
    
    static class RectInfo implements Comparable<RectInfo> {
        int x;
        int ys, ye;
        boolean isStarted;
        
        RectInfo(int x, int ys, int ye, boolean isStarted) {
            this.x = x;
            this.ys = ys;
            this.ye = ye;
            this.isStarted = isStarted;
        }
        
        @Override
        public int compareTo(RectInfo o) {
            return this.x - o.x;
        }
        
        @Override
        public String toString() {
            return x + " " + ys + " " + ye;
        }
    }
    
    static public class SegmentTree {
        private int[] array;
        private int[] tree;
        public int[] cntTree;
        
        public SegmentTree(/*int[] array*/) {
            //this.array = Arrays.copyOf(array, array.length);
            tree = new int[MAX_Y * 4];
            cntTree = new int[MAX_Y * 4];
            //    init(1, 0, array.length - 1);
            
        }
        
        // array 데이터를 입력받아서 segment tree를 초기화하는 경우
        private int init(int node, int start, int end) {
            if (start == end)
                return tree[node] = array[start];
            
            int mid = (start + end) / 2;
            
            return tree[node] = init(node * 2, start, mid) + init(node * 2 + 1, mid + 1, end);
        }
        
        // 일반적인 rsq 합을 구하는 것이 아니고 1보다 큰 것의 개수를 구하는 것이라서 update 시에 cntTree를 하나 더 할당해서 1보다 큰 것만 카운트
        void updateCnt(int lo, int hi, int val, int node, int start, int end) {
            if (end < lo || hi < start)
                return;
            
            if (lo <= start && end <= hi)
                cntTree[node] += val;
            else {
                int mid = (start + end) >> 1; // 나누기 2를 하는 것과 같다
                updateCnt(lo, hi, val, node * 2, start, mid);
                updateCnt(lo, hi, val, node * 2 + 1, mid + 1, end);
            }
            
            if (cntTree[node] > 0) tree[node] = end - start + 1; // 이구간은 1이상이므로
            else {
                if (start == end) tree[node] = 0; // leaf node
                else tree[node] = tree[node * 2] + tree[node * 2 + 1];
            }
        }
    }
}
