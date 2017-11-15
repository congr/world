import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 11. 15..
 */
public class KOI_두더지굴_S {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int A[][] = new int[N][N];
        int D[][] = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = sc.nextInt();
            }
        }

        ArrayList<Integer> al = dfsAll(N, A, D);
        Collections.reverse(al);

        // 답 출력
        System.out.println(al.size());
        for (int i = 0; i < al.size(); i++) {
            System.out.println(al.get(i));
        }
    }

    static ArrayList<Integer> dfsAll(int N, int[][] A, int[][] D) {
        int moleInd = 0; // 발견된 굴마다 index를 매김
        int moleCnt = 0; // 하나의 굴에 연결된 셀의 개수
        ArrayList<Integer> al = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (A[i][j] == 1 && D[i][j] == 0) { // !!! 별도 배열에 방문한 셀을 기록한다면 이전에 방문 안한 곳만 가면됨 D[][] == 0 을 꼭 넣자
                    moleInd++;
                    moleCnt = dfs(N, A, D, i, j, moleInd, 1); // moleCnt는 연결된 셀 개수, 이미 1개는 있으므로 1로 시작
                    al.add(moleCnt);
                }
            }
        }

        return al;
    }

    static int dx[] = new int[]{-1, 0, 1, 0};
    static int dy[] = new int[]{0, -1, 0, 1};

    static int dfs(int N, int[][] A, int[][] D, int y, int x, int moleInd, int moleCnt) {
        D[y][x] = moleInd;

        for (int k = 0; k < dx.length; k++) { // gird[y][x]를 중심으로 4가지 방향으로
            int ny = y + dy[k], nx = x + dx[k]; // 다음 방문할 위치

            if (insideGrid(ny, nx, N, N) && A[ny][nx] == 1 && D[ny][nx] == 0) { // !!! D[ny][nx] == 0 방문한 적이 없을 때
                moleCnt = dfs(N, A, D, ny, nx, moleInd, moleCnt) + 1;
            }
        }

        return moleCnt;
    }

    static boolean insideGrid(int y, int x, int my, int mx) {
        if ((x >= 0 && y >= 0) && (x < mx && y < my)) return true;
        else return false;
    }
}
