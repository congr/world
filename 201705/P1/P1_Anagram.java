import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 11..
 */
// 2017 05 1번 Anagram (strong Anagram) 다시 해결해봄
public class P1_Anagram {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201705/P1/input003.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int D = sc.nextInt();
            String left = sc.next();
            String right = sc.next();

            boolean ans = false;
            if (left.length() != right.length()) ans = false;
            else ans = isStrongAnagram(left, right, D);

            String result = (ans == true) ? "O" : "X";
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static boolean isStrongAnagram(String left, String right, int D) {
        ArrayList<Integer>[] leftAl = new ArrayList[26];
        ArrayList<Integer>[] rightAl = new ArrayList[26];

        for (int i = 0; i < 26; i++) {
            leftAl[i] = new ArrayList<>();
            rightAl[i] = new ArrayList<>();
        }

        for (int i = 0; i < left.length(); i++) {
            // left
            int k = left.charAt(i) - 'a';
            leftAl[k].add(i);

            // right
            k = right.charAt(i) - 'a';
            rightAl[k].add(i);
        }

        for (int i = 0; i < 26; i++) {
            if (leftAl[i].size() != rightAl[i].size()) return false; // 같은 알파벳의 개수가 같아야 한다
            for (int j = 0; j < leftAl[i].size(); j++) {
                int diff = Math.abs(leftAl[i].get(j) - rightAl[i].get(j));
                if (diff > D) return false;
            }
        }

        return true;
    }
}
