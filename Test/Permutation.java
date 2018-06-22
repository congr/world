import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Permutation {
    public static void main(String[] args) throws java.lang.Exception {
        int[] a = {1, 2, 3, 4};
        //ArrayList<int[]> list = permutations(a);
        //for (int[] s : list) {
        //    System.out.println(Arrays.toString(s));
        //}

        allPermutation(a);

        //perm("1234"); // all permutation
        //
        //int n = 4, k = 10;
        //System.out.println("n" + n + "k" + k);
        //System.out.println(getPermutation(n, k));
    }

    static void allPermutation(int[] arr) {
        System.out.println(Arrays.toString(arr));

        while (true) {
            int i = arr.length - 2;
            while(i >= 0 && arr[i] >= arr[i+1]) i--;
            if (i < 0) break;// 4321

            int j = arr.length - 1;
            while (j >= 0 && arr[i] > arr[j]) j--; // rightmost largest id -> j
            swap(arr, i, j);
            reverseArr(arr, i + 1);
            System.out.println(Arrays.toString(arr));
        }
    }

    static void reverseArr(int[] arr, int start) {
        int end = arr.length - 1;

        while (start < end) {
            swap(arr, start, end);
            start++;
            end--;
        }
    }


    static void perm(String str) {
        perm(str, "");
    }

    static void perm(String str, String prefix) {
        //System.out.println("\tstr "+str + " / prefix " + prefix);
        if (str.length() == 0) {
            System.out.println(prefix);
        } else {
            for (int i = 0; i < str.length(); i++) {
                //System.out.println("loop["+i + "] " + str + " / " + prefix);
                String rem = str.substring(0, i) + str.substring(i + 1);
                perm(rem, prefix + str.charAt(i));
            }
        }
    }

    //
    static ArrayList<int[]> permutations(int[] a) {
        ArrayList<int[]> ret = new ArrayList<>();
        permutation(a, 0, ret);
        return ret;
    }

    public static void permutation(int[] arr, int pos, ArrayList<int[]> list) {
        //System.out.println(Arrays.toString(arr));
        if (arr.length - pos == 1) {
            list.add(arr.clone());
        } else
            for (int i = pos; i < arr.length; i++) {
                swap(arr, pos, i);
                permutation(arr, pos + 1, list);
                swap(arr, pos, i);
            }
    }

    public static void swap(int[] arr, int pos1, int pos2) {
        int h = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = h;
    }

    static public String getPermutation(int n, int k) {
        List<Integer> num = new LinkedList<Integer>();
        for (int i = 1; i <= n; i++) num.add(i);
        int[] fact = new int[n];  // factorial
        fact[0] = 1;
        for (int i = 1; i < n; i++) fact[i] = i * fact[i - 1];
        System.out.println(Arrays.toString(fact));
        k = k - 1;
        StringBuilder sb = new StringBuilder();
        for (int i = n; i > 0; i--) {
            int ind = k / fact[i - 1];
            k = k % fact[i - 1]; // k: 3 1 0 0
            sb.append(num.get(ind)); // ind : 1 1 1 0
            num.remove(ind); // 1234 (1 remove) - 234 (2 remove) - 34 (3 remove) - 4 (4 remove)
        }
        return sb.toString();
    }
}
