import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 16..
 */
// 화성지
// 전체 직사각형의 넓이 구하기
// https://blog.sisobus.com/2014/02/plane-sweeping.html#.WZOibXdJadS 참고
/* 입력
 2
 10 10 20 20
 15 15 25 30
* */
public class BOJ3392_PlaneSweeping {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        RectInfo[] rect = new RectInfo[N * 2];
        int[] Y = new int[100];
        for (int i = 0; i < N; i++) {
            int x1 = sc.nextInt();
            int y1 = sc.nextInt();
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();

            rect[i] = new RectInfo(x1, y1, y2, true);
            rect[i + N] = new RectInfo(x2, y1, y2, false);
            //Y[i] = y1;
            //Y[i + N] = y2;
        }

        // Arrays.sort(Y);
        Arrays.sort(rect);
        System.out.println(Arrays.toString(rect));

        SegmentTree st = new SegmentTree(Y);
        int sum = 0;
        st.updateRange(rect[0].ys, rect[0].ye, Y.length - 1, 1);

        //System.out.println(rect[0].ys + " " + rect[0].ye + " " + st.sum2(1,0, Y.length-1, 0, Y.length-1));
        for (int i = 1; i < N * 2; i++) {
            if (rect[i].isStarted)
                st.updateRange(rect[i].ys, rect[i].ye, Y.length - 1, 1);
            else
                st.updateRange(rect[i].ys, rect[i].ye, Y.length - 1, -1);

            int xDiff = rect[i].x - rect[i - 1].x;
            int ySum = st.cntTree[1];
            //int ySum = st.sum2(1,0, Y.length-1, 0, Y.length-1);
            sum += xDiff * ySum;
            System.out.println("xDiff:" + xDiff + " ySum:" + ySum + " sum:" + sum);
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
        private int size;
        private int[] tree;
        public int[] cntTree;

        public SegmentTree(int[] array) {
            this.array = Arrays.copyOf(array, array.length);
            //The MAX_VALUE size of this array is about 2 * 2 ^ log2(n) + 1
            size = (int) (2 * Math.pow(2.0, Math.floor((Math.log((double) array.length) / Math.log(2.0)) + 1)));
            tree = new int[size];
            cntTree = new int[size];
            init(1, 0, array.length - 1);

        }

        private int init(int node, int start, int end) {
            if (start == end)
                return tree[node] = array[start];

            int mid = (start + end) / 2;

            return tree[node] = init(node * 2, start, mid) + init(node * 2 + 1, mid + 1, end);
        }

        void updateRange(int updateStart, int updateEnd, int n, int diff) {
            for (int i = updateStart; i < updateEnd; i++) {
                update(1, 0, n, i, diff);
            }
        }

        void update(int node, int start, int end, int index, int diff) {
            if (!(start <= index && index <= end))
                return;

            tree[node] += diff;

            if (start != end) {
                int mid = (start + end) / 2;
                update(node * 2, start, mid, index, diff);
                update(node * 2 + 1, mid + 1, end, index, diff);
            }

            if (tree[node] > 0) cntTree[node] = end - start + 1;
            else {
                if (start == end) cntTree[node] = 0;
                else cntTree[node] = cntTree[node * 2] + cntTree[node * 2 + 1];
            }
        }

        void update2(int node, int start, int end, int index, int diff) {
            if (!(start <= index && index <= end))
                return;

            //if (diff == 1)
            //    cntTree[node] = 1;
            //else cntTree[node] = 0;
            cntTree[node] += diff;

            if (start != end) {
                int mid = (start + end) / 2;
                update2(node * 2, start, mid, index, diff);
                update2(node * 2 + 1, mid + 1, end, index, diff);
            }

            if (tree[node] > 0) cntTree[node] = end - start + 1;
            else {
                if (start == end) cntTree[node] = 0;
                else cntTree[node] = cntTree[node * 2] + cntTree[node * 2 + 1];
            }
        }

        int sum(int node, int start, int end, int left, int right) {
            if (left > end || right < start)
                return 0;

            if (left <= start && end <= right)
                return tree[node];

            int mid = (start + end) / 2;
            return sum(node * 2, start, mid, left, right) + sum(node * 2 + 1, mid + 1, end, left, right);
        }

        int countSum() {
            return cntTree[1];
        }

        int sum2(int node, int start, int end, int left, int right) {
            if (left > end || right < start)
                return 0;

            if (left <= start && end <= right)
                return cntTree[node];

            int mid = (start + end) / 2;
            return sum2(node * 2, start, mid, left, right) + sum2(node * 2 + 1, mid + 1, end, left, right);
        }
    }
}
