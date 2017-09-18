import java.util.Scanner;

/**
 * Created by cutececil on 2017. 3. 21..
 */
public class AProblem06 {
    public static void main(String[] args) { // class Solution
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
       

        for (int i = 0; i < N; i++) {
            int n = (int) (Math.random() * 10) + 1 ;
            
            System.out.println(n%3);
        }
        
    }
   
    
}
