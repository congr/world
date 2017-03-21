/**
 * Created by cutececil on 2017. 2. 7..
 */

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class BalancedBrackets {

    public static boolean isBalanced(String expression, Hashtable<Character, Character> brackets) {
        Stack<Character> st = new Stack<>();
        int length = expression.length();
        for (int i = 0; i < length; ++i) {
            char theChar = expression.charAt(i);
            boolean isOpenBracket = brackets.containsKey(theChar);  // true if the current char is open bracket

            if (isOpenBracket) st.push(theChar);
            else {                                                  // if theChar is close bracket, pop
                if (st.empty()) return false;
                char openBracket = st.pop();                        // top open bracket in stack
                char closeBracket = brackets.get(openBracket);      // get matched close bracket from hashtable
                if (closeBracket != theChar) return false;          // theChar is close bracket
            }
        }

        if (!st.empty()) return false;                              // ** stack should be empty **

        return true;
    }

    public static void main(String[] args) {
        Hashtable<Character, Character> brackets = new Hashtable<>(3);
        brackets.put('{', '}');
        brackets.put('[', ']');
        brackets.put('(', ')');

        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int a0 = 0; a0 < t; a0++) {
            String expression = in.next();
            System.out.println((isBalanced(expression, brackets)) ? "YES" : "NO");
        }
    }

    public static boolean isBalanced(String expression) {
        Stack<Character> brackets = new Stack<Character>();

        for(int i=0; i<expression.length(); i++){
            char next = expression.charAt(i);
            if(next == '{')      brackets.push('}');
            else if(next == '[') brackets.push(']');
            else if(next == '(') brackets.push(')');
            else if(brackets.empty() || brackets.pop() != next) {
                return false;
            }
        }
        return brackets.empty();
    }
}
