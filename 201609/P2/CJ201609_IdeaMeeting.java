import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 4. 24..
 */
// Line segment 가 있을 때 plane sweeping을 통해 모두 겹치는 구간을 검색
// 여러구간이 겹친다면 가장 먼저 겹치는 x 기준으로 출력하라
// line segmentHV, range search, horizontal line merge
public class CJ201609_IdeaMeeting {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201609/P2/input002.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);
        
        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt(); // 사원수
            int K = sc.nextInt(); // 부서 개수
            
            // horizontal line merge를 위해 y가 같은 라인 그룹을 array list에 둔다
            ArrayList<SegmentHV>[] kList = new ArrayList[K];
            for (int i = 0; i < K; i++) {
                kList[i] = new ArrayList<>();
            }
            
            SegmentHV[] segArray = new SegmentHV[N]; // horizontal seg for HighChart
            for (int i = 0; i < N; i++) {
                int s = sc.nextInt(); //start
                int e = sc.nextInt(); // end
                int y = sc.nextInt(); // 부서
                
                SegmentHV seg = new SegmentHV(s, y, e, y);
                kList[y - 1].add(seg);
                segArray[i] = seg;
            }
            
            //printHighChartLine(segArray);
            
            PriorityQueue<Event> pq = new PriorityQueue<Event>();
            Set<Integer> set = new HashSet<>();             // Vertical line을 추가하기 위해
            for (int i = 0; i < K; i++) {
                ArrayList<SegmentHV> al = merge(kList[i]);  // horizontal line merge
                //System.out.println(Arrays.toString(al.toArray()));
                for (SegmentHV seg : al) {
                    pq.add(new Event(seg.x1, seg));
                    pq.add(new Event(seg.x2, seg));
                    set.add(seg.x1);
                    set.add(seg.x2);
                }
            }
            
            for (Integer i : set) {                         // 겹치는 구간 range search 를 위해 virtual vertical line을 삽입
                SegmentHV seg = new SegmentHV(i, -99999, i, 99999); // virtual vertical line
                pq.add(new Event(i, seg));
            }
            
            int[] ans = solve(pq, K);
            String result = (ans[0] == 987654321) ? "-1" : ans[0] + " " + ans[1]; // 겹치는 회의 시간이 없을 경우 -1 출력
            System.out.println(result);
            wr.write(result + "\n");
        }
        
        sc.close();
        wr.close();
    }
    
    static ArrayList<SegmentHV> merge(ArrayList<SegmentHV> iList) {
        if (iList.size() < 2) return iList;
        
        Collections.sort(iList, (o1, o2) -> o1.x1 - o2.x1);
        
        SegmentHV prevIntvl = iList.get(0);
        ArrayList<SegmentHV> res = new ArrayList<>();
        for (int i = 1; i < iList.size(); i++) {
            SegmentHV curIntvl = iList.get(i);
            if (curIntvl.x1 <= prevIntvl.x2) {
                prevIntvl.x2 = (prevIntvl.x2 > curIntvl.x2 ? prevIntvl.x2 : curIntvl.x2);
            } else {
                res.add(prevIntvl);
                prevIntvl = curIntvl;
            }
        }
        
        res.add(prevIntvl);
        return res;
    }
    
    static void printHighChartLine(SegmentHV[] arr) {
        System.out.println("HighChart line series");
        System.out.println("---------------------------");
        for (int i = 0; i < arr.length; i++) {
            System.out.print("{data: [[" + arr[i].x1 + "," + arr[i].y1 + "]," + "[" + arr[i].x2 + "," + arr[i].y2 + "]]}");
            if (i != arr.length - 1)
                System.out.println(",");
        }
        System.out.println("\n---------------------------");
    }
    
    static int[] solve(PriorityQueue<Event> pq, int target) {// 매치 카운트 목표 = 부서 개수
        RangeSearch<SegmentHV> rangeSearchTree = new RangeSearch<>();
        int INF = 987654321;
        int allSatisfiedStart = INF;
        int allSatisfiedEnd = -INF;
        int open = INF;
        
        while (!pq.isEmpty()) {
            Event e = pq.remove();
            int sweep = e.time;
            SegmentHV seg = e.segment;
            
            if (seg.isVertical()) {
                //System.out.println("vertical " + sweep);
                //System.out.println("vertical check " + rangeSearchTree.size());
                if (rangeSearchTree.size() >= target) {
                    SegmentHV seg1 = new SegmentHV(-INF, seg.y1, -INF, seg.y1);
                    SegmentHV seg2 = new SegmentHV(+INF, seg.y2, +INF, seg.y2);
                    Iterable<SegmentHV> list = rangeSearchTree.range(seg1, seg2);
                    Set<Integer> set = new HashSet<>();
                    int cnt = 0;
                    for (SegmentHV s : list) {
                        set.add(s.y1);
                        cnt++;
                    }
                    
                    if (set.size() >= target) {
                        if (sweep == seg.x1 && open == INF)
                            open = sweep;
                        else if (sweep == seg.x2 && open != INF && !(cnt > target)) { // 다른 동료가 더 있었고 이 동료의 시간이 종료되는 타이밍이다
                            if (allSatisfiedEnd - allSatisfiedStart < sweep - open) { // 갭이 큰 것이 더 좋은 것
                                allSatisfiedStart = open;
                                allSatisfiedEnd = sweep;
                                open = INF;
                            }
                        }
                    } else {
                        allSatisfiedStart = INF;
                        allSatisfiedEnd = -INF;
                        open = INF;
                    }
                }
            } else if (sweep == seg.x1) {
                //System.out.println("add " + sweep + " " + seg);
                rangeSearchTree.add(seg);
            } else if (sweep == seg.x2) {
                //System.out.println("remove " + sweep + " " + seg);
                rangeSearchTree.remove(seg);
                open = INF; // 빠지는 구간이 있으므로 open 은 리셋해야 함
            }
        }
        
        return new int[]{allSatisfiedStart, allSatisfiedEnd};
    }
    
    // helper class for events in sweep line algorithm
    static class Event implements Comparable<Event> {
        int time;
        SegmentHV segment;
        
        public Event(int time, SegmentHV segment) {
            this.time = time;
            this.segment = segment;
        }
        
        public int compareTo(Event that) {
            if (this.time < that.time)
                return -1;
            else if (this.time > that.time)
                return +1;
            else {
                // 세로줄과 가로줄이 겹치는데, 세로줄이 겹치는 지점이 시작점 x1일 때는 horizontal 먼저 add하고 끝점 x2일때는 horizontal 나중에 remove하도록
                if (this.segment.x1 == that.segment.x1) {
                    if (this.segment.isHorizontal() && that.segment.isVertical())
                        return -1;
                    else if (this.segment.isVertical() && that.segment.isHorizontal())
                        return 1;
                } else if (this.segment.x2 == that.segment.x1) {
                    if (this.segment.isHorizontal() && that.segment.isVertical())
                        return 1;
                    else if (this.segment.isVertical() && that.segment.isHorizontal())
                        return -1;
                }
                return 0;
            }
        }
        
        @Override
        public String toString() {
            return segment.toString();
        }
    }
    
    static class SegmentHV implements Comparable<SegmentHV> {
        public int x1, y1;  // lower left
        public int x2, y2;  // upper right
        
        // precondition: x1 <= x2, y1 <= y2 and either x1 == x2 or y1 == y2
        public SegmentHV(int x1, int y1, int x2, int y2) {
            if (x1 > x2 || y1 > y2) throw new RuntimeException("Illegal hv-segment");
            if (x1 != x2 && y1 != y2) throw new RuntimeException("Illegal hv-segment");
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
        
        // is this segment horizontal or vertical?
        public boolean isHorizontal() {
            return (y1 == y2);
        }
        
        public boolean isVertical() {
            return (x1 == x2);
        }
        
        // compare on y1 coordinate; break ties with other coordinates
        public int compareTo(SegmentHV that) {
            if (this.y1 < that.y1) return -1;
            else if (this.y1 > that.y1) return +1;
            else if (this.y2 < that.y2) return -1;
            else if (this.y2 > that.y2) return +1;
            else if (this.x1 < that.x1) return -1;
            else if (this.x1 > that.x1) return +1;
            else if (this.x2 < that.x2) return -1;
            else if (this.x2 > that.x2) return +1;
            return 0;
        }
        
        public String toString() {
            String s = "";
            if (isHorizontal()) s = "horizontal: ";
            else if (isVertical()) s = "vertical:   ";
            return s + "[" + x1 + ", " + y1 + "] -> [" + x2 + ", " + y2 + "]";
        }
    }
    
    static class RangeSearch<Key extends Comparable<Key>> {
        
        private Node root;   // root of the BST
        
        // BST helper node data type
        private class Node {
            Key key;            // key
            Node left, right;   // left and right subtrees
            int N;              // node count of descendents
            
            public Node(Key key) {
                this.key = key;
                this.N = 1;
            }
        }
        
        /***************************************************************************
         *  BST search
         ***************************************************************************/
        
        public boolean contains(Key key) {
            return contains(root, key);
        }
        
        private boolean contains(Node x, Key key) {
            if (x == null) return false;
            int cmp = key.compareTo(x.key);
            if (cmp == 0) return true;
            else if (cmp < 0) return contains(x.left, key);
            else return contains(x.right, key);
        }
        
        /***************************************************************************
         *  randomized insertion
         ***************************************************************************/
        public void add(Key key) {
            root = add(root, key);
        }
        
        // make new node the root with uniform probability
        private Node add(Node x, Key key) {
            if (x == null) return new Node(key);
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                return x;
            }
            //if (StdRandom.bernoulli(1.0 / (size(x) + 1.0))) return addRoot(x, key);
            if (Math.random()/(size(x) + 1.0) > 0) return addRoot(x, key);
            if (cmp < 0) x.left = add(x.left, key);
            else x.right = add(x.right, key);
            // (x.N)++;
            fix(x);
            return x;
        }
        
        
        private Node addRoot(Node x, Key key) {
            if (x == null) return new Node(key);
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                return x;
            } else if (cmp < 0) {
                x.left = addRoot(x.left, key);
                x = rotR(x);
            } else {
                x.right = addRoot(x.right, key);
                x = rotL(x);
            }
            return x;
        }
        
        
        /***************************************************************************
         *  deletion
         ***************************************************************************/
        private Node joinLR(Node a, Node b) {
            if (a == null) return b;
            if (b == null) return a;
            
            if (Math.random() * (double) size(a) / (size(a) + size(b))>0) {
            //if (StdRandom.bernoulli((double) size(a) / (size(a) + size(b)))) {
                a.right = joinLR(a.right, b);
                fix(a);
                return a;
            } else {
                b.left = joinLR(a, b.left);
                fix(b);
                return b;
            }
        }
        
        private Node remove(Node x, Key key) {
            if (x == null) return null;
            int cmp = key.compareTo(x.key);
            if (cmp == 0) x = joinLR(x.left, x.right);
            else if (cmp < 0) x.left = remove(x.left, key);
            else x.right = remove(x.right, key);
            fix(x);
            return x;
        }
        
        // remove given key if it exists
        public void remove(Key key) {
            root = remove(root, key);
        }
        
        
        /***************************************************************************
         *  Range searching
         ***************************************************************************/
        
        // return all keys between k1 and k2
        public Iterable<Key> range(Key k1, Key k2) {
            Queue<Key> list = new LinkedList<Key>();
            if (less(k2, k1)) return list;
            range(root, k1, k2, list);
            return list;
        }
        
        private void range(Node x, Key k1, Key k2, Queue<Key> list) {
            if (x == null) return;
            if (lte(k1, x.key)) range(x.left, k1, k2, list);
            if (lte(k1, x.key) && lte(x.key, k2)) list.add(x.key);
            if (lte(x.key, k2)) range(x.right, k1, k2, list);
        }
        
        
        /***************************************************************************
         *  Utility functions
         ***************************************************************************/
        
        // return the smallest key
        public Key min() {
            Key key = null;
            for (Node x = root; x != null; x = x.left)
                key = x.key;
            return key;
        }
        
        // return the largest key
        public Key max() {
            Key key = null;
            for (Node x = root; x != null; x = x.right)
                key = x.key;
            return key;
        }
        
        
        /***************************************************************************
         *  useful binary tree functions
         ***************************************************************************/
        
        // return number of nodes in subtree rooted at x
        public int size() {
            return size(root);
        }
        
        private int size(Node x) {
            if (x == null) return 0;
            else return x.N;
        }
        
        // height of tree (empty tree height = 0)
        public int height() {
            return height(root);
        }
        
        private int height(Node x) {
            if (x == null) return 0;
            return 1 + Math.max(height(x.left), height(x.right));
        }
        
        
        /***************************************************************************
         *  helper BST functions
         ***************************************************************************/
        
        // fix subtree count field
        private void fix(Node x) {
            if (x == null) return;                 // check needed for remove
            x.N = 1 + size(x.left) + size(x.right);
        }
        
        // right rotate
        private Node rotR(Node h) {
            Node x = h.left;
            h.left = x.right;
            x.right = h;
            fix(h);
            fix(x);
            return x;
        }
        
        // left rotate
        private Node rotL(Node h) {
            Node x = h.right;
            h.right = x.left;
            x.left = h;
            fix(h);
            fix(x);
            return x;
        }
        
        
        /***************************************************************************
         *  Debugging functions that test the integrity of the tree
         ***************************************************************************/
        
        // check integrity of subtree count fields
        public boolean check() {
            return checkCount() && isBST();
        }
        
        // check integrity of count fields
        private boolean checkCount() {
            return checkCount(root);
        }
        
        private boolean checkCount(Node x) {
            if (x == null) return true;
            return checkCount(x.left) && checkCount(x.right) && (x.N == 1 + size(x.left) + size(x.right));
        }
        
        
        // does this tree satisfy the BST property?
        private boolean isBST() {
            return isBST(root, min(), max());
        }
        
        // are all the values in the BST rooted at x between min and max, and recursively?
        private boolean isBST(Node x, Key min, Key max) {
            if (x == null) return true;
            if (less(x.key, min) || less(max, x.key)) return false;
            return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
        }
        
        
        /***************************************************************************
         *  helper comparison functions
         ***************************************************************************/
        
        private boolean less(Key k1, Key k2) {
            return k1.compareTo(k2) < 0;
        }
        
        private boolean lte(Key k1, Key k2) {
            return k1.compareTo(k2) <= 0;
        }
    }
}
