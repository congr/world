import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 3. 17..
 */
public class BinarySearch {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        int t = in.nextInt();
        while (t-- > 0) {
            int m = in.nextInt(); // money
            int n = in.nextInt(); // ice cream count
            ArrayList<Pair<Integer, Integer>> al = new ArrayList<>();
            
            for (int i = 0; i < n; i++) {
                int value = in.nextInt();
                al.add(new Pair<>(i, value));
            }
            
            al.sort((o1, o2) -> o1.getValue() - o2.getValue());
            
            for (int i = 0; i < n; i++) {
                int target = m - al.get(i).getValue();
                int iFound = binarySearch(al, i + 1, al.size() - 1, target);
                if (iFound != -1) {// found
                    int iSource = al.get(i).getKey();
                    System.out.println((Math.min(iSource, iFound) + 1) + " " + (Math.max(iSource, iFound) + 1));
                    break;
                }
            }
        }
    }
    
    static int binarySearch(ArrayList<Pair<Integer, Integer>> al, int lo, int hi, int target) {
        if (hi < lo) return -1; // ** recursive func always needs base condition to exit
        
        int mid = lo + (hi - lo) / 2;
        
        if (al.get(mid).getValue() > target) return binarySearch(al, lo, mid - 1, target);
        else if (al.get(mid).getValue() < target) return binarySearch(al, mid + 1, hi, target);
        else if (al.get(mid).getValue() == target) return al.get(mid).getKey();
        
        return -1; // not found
    }
    
}
