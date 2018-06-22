import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 2. 11..
 */
public class SortGame {
    // Map, HashMap, LinkedHashMap, HashTable usages
    // Queue<> q = new LinkedList<>();
    // int[] -> string : to remove [], use regex
    // char[] -> string : String.valueOf(char[])

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        // 모든 순열에 대해 미리 생성해둠, 46233가지
        // {0}, {0,1}, {1,0}, {0,1,2}, {1,0,2}, {2,1,0}, {0,2,1}, {2,0,1}, {1,2,0}..
        LinkedHashMap<String, Integer> toSort = new LinkedHashMap<>();
        for (int i = 1; i <= 8; i++) { // 최대 8자리
            precalc(i, toSort);
        }

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            int N = Integer.parseInt(br.readLine());
            String[] NString = br.readLine().split(" ");
            int[] input = new int[N];
            for (int i = 0; i < N; ++i) input[i] = Integer.parseInt(NString[i]);

            int result = solve(input, toSort);
            System.out.println(result);
        }

        br.close();
        out.close();
    }

    static void precalc(int n, LinkedHashMap<String, Integer> toSort) {
        int[] perm = new int[n];
        for (int i = 0; i < n; i++) perm[i] = i;

        Queue<String> queue = new LinkedList<>(); // queue를 LinkedList로 정의

        String root = Arrays.toString(perm).replaceAll("\\[|\\]|,|\\s", "");
        queue.add(root);
        toSort.put(root, 0); // LinkedHashMap to setWithoutDup new value

        while (!queue.isEmpty()) {
            String here = queue.poll(); // retrieve and remove the front
            int cost = toSort.get(here);

            for (int i = 0; i < n; i++) { // 0번째자리 부터 2,3,..n개 뒤집고, 그다음 1자리부터 2,3,..n뒤집기
                for (int j = i + 2; j <= n; j++) { // 0자리부터 2개씩 뒤집기, 3개씩 뒤집기,... n개씩 뒤집기
                    String reversed = reverse(here, i, j);
                    if (toSort.containsKey(reversed) == false) {
                        queue.add(reversed);
                        toSort.put(reversed, cost + 1);
                    }
                    //reverse(here, i, j);
                }
            }
        }
    }

    static int solve(int[] input, LinkedHashMap<String, Integer> toSort) {
        // original input is like [3, 90, 24, 6, 80] -> [0, 4, 2, 1, 3]
        // perm should be transformed as 0, 1, 2.. n-1 because toSort graph already calculated distance by BFS
        ArrayList<Integer> fixed = new ArrayList<>(input.length);
        for (int me : input) {
            int cnt = getCountSmallerThanMe(input, me);
            fixed.add(cnt);
        }

        // return distance to "20431" out of "01234"
        String fixedStr = fixed.toString().replaceAll("\\[|\\]|,|\\s", "");
        return toSort.get(fixedStr);
    }

    static int getCountSmallerThanMe(int[] perm, int me) {
        int smaller = 0;
        for (int a : perm) {
            if (a < me) ++smaller;
        }
        return smaller;
    }

    // array reverse
    static void reverse(int[] array) {
        Collections.reverse(Arrays.asList(array));
    }

    static String reverse(String str, int start, int end) {
        char arr[] = str.toCharArray();
        int len = end - start;
        if (len <= 0) return "";

        int len2 = len >> 1;
        char temp;

        for (int i = 0; i < len2; ++i) {
            temp = arr[start + i];
            arr[start + i] = arr[end - i - 1];
            arr[end - i - 1] = temp;
        }

        return String.valueOf(arr); // char [] to string
    }
}
