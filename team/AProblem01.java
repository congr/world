import java.util.Scanner;

/**
 * Created by cutececil on 2017. 3. 20..
 */
public class AProblem01 {
    public static void main(String[] args) { // class Solution
        Scanner in = new Scanner(System.in);
        
        int N = in.nextInt();
        for (int i = 1; i <= N; i++) {
            if (i % 3 == 0 && i % 5 == 0)
                System.out.println("FizzBuzz");
            else if (i % 3 == 0)
                System.out.println("Fizz");
            
            else if (i % 5 == 0)
                System.out.println("Buzz");
            else
                System.out.println(i);
        }
        
        
    }
    
    
}
