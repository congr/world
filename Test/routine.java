import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class routine {
    static public List<String> findItinerary(String[][] tickets) {
        List<String> result = new ArrayList();
        HashMap<String, ArrayList<String>> map = new HashMap();

        for (int i = 0; i < tickets.length; i++) {
            ArrayList<String> list = map.getOrDefault(tickets[i][0], new ArrayList<String>());
            list.add(tickets[i][1]);
            map.put(tickets[i][0], list);
        }

        PriorityQueue<String> q = new PriorityQueue();
        q.add("JFK"); // default departure

        while(!q.isEmpty()) {
            String u = q.remove();
            result.add(u);

            // AAA->BBB, CCC->BBB, BBB doesn't have linked cities
            if (map.get(u) == null || map.get(u).isEmpty()) continue;

            ArrayList<String> visited = new ArrayList();
            for(String v : map.get(u)) {
                visited.add(v);
                q.add(v);
            }

            ArrayList<String> list = map.get(u);
            list.removeAll(visited); // remove already visited
        }

        return result;
    }

    public static void main(String[] args) {
        List<String> list =findItinerary(new String[][] {{"JFK", "ABC"}, {"ABC", "DDD"}, {"DDD", "ABC"}});
        System.out.println(list);
    }
}
