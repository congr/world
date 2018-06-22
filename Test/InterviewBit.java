import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InterviewBit {
    public static void main(String[] args) {

        //int[] A = new int[]{21, 0, 0, 29, 0, 0, 28, 20, 0, 0, 29, 4, 0, 0, 0, 0, 0, 0, 0, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 25, 2, 0, 3, 0, 29, 0, 0, 8, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 30, 0, 30, 19, 0, 0, 19, 16, 18, 0, 0, 29, 0, 0, 0, 23, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 3, 0, 0, 8, 0, 17, 0, 0, 0, 0, 0, 0, 0, 21, 0, 0, 0, 0, 0, 3, 0, 0, 9, 22, 0, 23, 0, 0, 0, 0, 0, 0, 0, 17, 0, 0, 4, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 0, 17, 0, 0, 0, 6, 10, 0, 0, 0, 29, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 1, 0, 20, 0, 0, 0, 0, 20, 6, 12, 29, 0, 0, 18, 0, 17, 0, 8, 0, 16, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 21, 0, 0, 0, 18, 22, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 26, 22, 5, 0, 0, 0, 17, 24, 0, 0, 0, 0, 0, 21, 24, 21, 0, 0, 0, 0, 0, 0, 0, 25, 14, 12, 0, 0, 0, 0, 0, 7, 0, 9, 0};
        //
        //int N = A.length;
        //int[] D = new int[N];
        //
        //System.out.println("N " + N);
        //D[0] = 1; // initialize
        //for (int i = 0; i<N; i++) {
        //    if (A[i] == 0 || D[i] == 0) continue;
        //    for (int k = 1; k<=A[i]; k++) {
        //        if (i+k < N) D[i+k] = 1;
        //    }
        //}
        //
        // System.out.println(Arrays.toString(D));
        ////return D[N-1] > 0? 1:0;
        System.out.println(intToRoman(1134));

        System.out.println(734%26);
    }

    static public String intToRoman(int A) {
        TreeMap<Integer, String> map = new TreeMap<>();

         map.put(1, "I");
         map.put(4, "IV");
         map.put(5, "V");
         map.put(9, "IX");
         map.put(10, "X");
         map.put(40, "XL");
         map.put(50, "L");
         map.put(90, "XC");
         map.put(100, "C");
         map.put(400, "CD");
         map.put(500, "D");
         map.put(900, "CM");
         map.put(1000, "M");

        //for(Integer key : map.keySet()){
        //    System.out.println(key+ " " + map.get(key));
        //}

        for(Map.Entry<Integer, String> entry : map.entrySet()){
            int key = entry.getKey();
            String val = entry.getValue();
            System.out.println("entry " + entry);
            System.out.println("key " + key + " val " + val);
        }

        System.out.println(map.keySet());
        System.out.println(map.values());

        return toConvert(map, A);
    }

    static String toConvert(TreeMap<Integer, String> map, int num) {
        if (num == 0) return "";
        int key = map.floorKey(num); // less or equal key
        return map.get(key) + String.valueOf(toConvert(map, num - key));
    }

    public String getPermutation(int n, int k) {
        List<Integer> num = new LinkedList<>();
        for (int i = 1; i <= n; i++) num.add(i);
        int[] fact = new int[n];  // factorial
        fact[0] = 1;
        for (int i = 1; i < n; i++) fact[i] = i*fact[i-1];
        k = k-1;
        StringBuilder sb = new StringBuilder();
        for (int i = n; i > 0; i--){
            int ind = k/fact[i-1];
            k = k%fact[i-1];
            sb.append(num.get(ind));
            num.remove(ind);
        }
        return sb.toString();
    }
}
