import java.util.*;

public class LeetCode {

    public static void main(String[] args) {
       String s = mostCommonWord("Bob hit a ball, it the hit BALL flew far after it was hit. it",new String[]{"hit"});
        System.out.println(s);
    }

    static public String mostCommonWord(String paragraph, String[] banned) {
        HashMap<String, Integer> map = new HashMap();
        HashSet<String> set = new HashSet();

        for(String s: banned) {
            set.add(s);
        }

        String para = paragraph.toLowerCase()+".";
        for (int i=0,j=0; j<paragraph.length(); j++) {
            char ch = para.charAt(j);
            if('a' <= ch && ch <= 'z') ;
            else {
                char c = para.charAt(i);
                while ('a' > c || c > 'z') {
                    i++;
                    if (i<=j) break;
                }
                if (i==j) continue;

                String s = para.substring(i, j);
                System.out.println(s);
                if (!set.contains(s)) {
                    int f = map.getOrDefault(s, 0);
                    map.put(s, f+1);
                }
                i = j+1;
                j++;
            }
        }


        int max = 0;
        String result = "";
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry);
            if (entry.getValue() > max) {
                result = entry.getKey();
                max = entry.getValue();
            }
        }

        return result;
    }
}
