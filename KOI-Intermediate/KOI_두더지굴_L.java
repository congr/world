import java.util.*;

/**
 * Created by cutececil on 2017. 12. 1..
 */
public class KOI_두더지굴_L {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int A[][] = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = sc.nextInt();
            }
        }

        ArrayList<Integer> al = bfsAll(N, A);
        Collections.reverse(al);

        // 답 출력
        System.out.println(al.size());
        for (int i = 0; i < al.size(); i++) {
            System.out.println(al.get(i));
        }
    }

    // N * N 테이블에 셀 단위로 진행하다가 bfs로 연결된 셀을 다 돌면서 A[i][j] 를 moleIndex를 2이상으로 마킹한다
    static ArrayList<Integer> bfsAll(int N, int[][] A) {
        ArrayList<Integer> al = new ArrayList<>();

        int moleIndex = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (A[i][j] == 1) {
                    int moleCnt = bfs(A, N, i, j, moleIndex + 1);
                    al.add(moleCnt);
                }
            }
        }
        return al;
    }

    // (y, x) 셀에서 시작하여 연결된 셀을 bfs로 다 돌아서 총 몇개 셀이 연결되었는지를 리턴한다
    static int bfs(int[][] A, int N, int y, int x, int moleIndex) {
        int cnt = 0;
        Queue<int[]> pq = new LinkedList<>();
        pq.add(new int[]{y, x});

        while (!pq.isEmpty()) {
            int[] adjCell = pq.remove();
            for (int i = 0; i < 4; i++) {
                int ny = adjCell[0] + dy[i], nx = adjCell[1] + dx[i];
                if (insideGrid(ny, nx, N, N) && A[ny][nx] == 1) {
                    pq.add(new int[]{ny, nx});
                    A[ny][nx] = moleIndex;
                    cnt++;
                }
            }
        }
        return cnt;
    }

    static int dx[] = new int[]{-1, 0, 1, 0};
    static int dy[] = new int[]{0, -1, 0, 1};

    static boolean insideGrid(int y, int x, int my, int mx) {
        if ((x >= 0 && y >= 0) && (x < mx && y < my)) return true;
        else return false;
    }
}
