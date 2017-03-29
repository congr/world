import java.util.Scanner;

/**
 * Created by cutececil on 2017. 3. 20..
 */
public class AProblem02 {
    public static void main(String[] args) { // class Solution
        Scanner in = new Scanner(System.in);
        
        int N = in.nextInt();
        long sum = 0;
        String str = in.next();
        
        char[] arr = str.toCharArray();
        int temp = 0;
        for (int i = 0; i < N; i++) {
            if (arr[i] >= 'a' && arr[i] <= 'z') continue;
            
            if (i + 1 < N && !(arr[i + 1] >= 'a' && arr[i + 1] <= 'z')) {// num
                if (temp == 0)
                    temp = Integer.valueOf(arr[i] - '0');
                else
                    temp = temp * 10 + Integer.valueOf(arr[i] - '0');
                
            } else {
                if (temp ==0)
                    temp = Integer.valueOf(arr[i] - '0');
                else
                    temp = temp * 10 + Integer.valueOf(arr[i] - '0');
    
                sum += temp;
                temp = 0;
            }
        }
        
        System.out.println(sum);
    }
    
    static int solve() {
        int result = 0;
        
        return result;
    }
    
}
