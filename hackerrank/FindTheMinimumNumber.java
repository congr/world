import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by cutececil on 2017. 3. 19..
 */
public class FindTheMinimumNumber {
    public static void main(String[] args) { // class solution
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();

        String min = "min(int, int)";
        String format = "min(int, %s)";

        for (int i = 2; i < N; i++)
            min = String.format(format, min);

        System.out.println(min);
    }
}
