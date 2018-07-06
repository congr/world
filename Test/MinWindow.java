import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class MinWindow {
    static public String minWindow(String s, String t) {
        HashMap<Character, Integer> map = new HashMap();
        int begin = 0, end = 0, head = 0, count = t.length();
        int diff = Integer.MAX_VALUE;

        for (Character c : t.toCharArray())
            map.merge(c, 1, Integer::sum);

        while (end < s.length()) {
            char e = s.charAt(end);

            if (map.containsKey(e) && map.merge(e, -1, Integer::sum) >= 0) { // t string
                count--;
            }

            while (count == 0) {
                if (diff > end - begin) {
                    head = begin;
                    diff = end - head;
                }

                char b = s.charAt(begin);
                if (map.containsKey(b) && map.merge(b, +1, Integer::sum) > 0) {// begin char is t string, then stop.
                    count++;
                }
                begin++;
            }
            end++;
        }

        if (diff == Integer.MAX_VALUE) return "";
        else return s.substring(head, head + diff + 1);
    }


    static public List<Integer> findAnagrams(String s, String p) {
        ArrayList<Integer> list = new ArrayList();
        HashMap<Character, Integer> pat = new HashMap();
        HashMap<Character, Integer> src = new HashMap();

        for (Character c : p.toCharArray()) pat.merge(c, 1, Integer::sum);

        int count = 0;
        for (int start = 0, end = 0; end < s.length(); end++) {
            Character c = s.charAt(end);
            src.merge(c, 1, Integer::sum);

            if (pat.containsKey(c) && src.getOrDefault(c, -1) == pat.getOrDefault(c, -1)) {
                count++;
            }

            if (count == p.length()) {
                list.add(start);
                count--;
                src.merge(s.charAt(start), -1, Integer::sum);
                start++;
            }

            if (end - start + 1 > p.length()) {
                count = 0;
                start = end+1;
                src.clear();
            }

        }

        return list;
    }

    public static void main(String[] args) {
        System.out.println(findAnagrams("cbaebabacd", "abc"));
    }
}