import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 9. 1..
 */
// 2016 09 final 2번 아이디어 회의
// ArrayList는 꼭 명시적으로 소팅해야한다. 예제를 만들어보자 단위 테스트 가능하도록 예제를 나누자
// 이대로도 Large set이 돌아가네.
public class P2_아이디어회의 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201609/P2/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);
        
        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int K = sc.nextInt();
            
            ArrayList<Person>[] personList = new ArrayList[K];
            for (int i = 0; i < K; i++) {
                personList[i] = new ArrayList<>();
            }
            
            for (int i = 0; i < N; i++) {
                int a = sc.nextInt();
                int b = sc.nextInt();
                int c = sc.nextInt();
                personList[c - 1].add(new Person(a, b, c, a)); // open 인구간만 저장
            }
            
            // overlap 있는 팀원끼리 merge (!!!팀원 교체 가능)
            ArrayList<Person> events = mergePerson(personList);
            
            // 미팅가능한 구간을 찾아서 list 넣는데 가장 긴 구간이 0번째에 담긴다(구간이 같으면 x1기준)
            ArrayList<Meeting> meetings = findMeetings(personList, events, K);
            
            String ans;
            if (meetings.size() == 0) ans = "-1"; // 미팅 가능한 구간이 없다면,
            else {
                Meeting m = meetings.get(0);
                ans = m.start + " " + m.end;
            }
            
            String result = ans;
            System.out.println(result);
            wr.write(result + "\n");
        }
        
        sc.close();
        wr.close();
    }
    
    // 미팅이 가능한 구간을 찾는다
    static ArrayList<Meeting> findMeetings(ArrayList<Person>[] personList, ArrayList<Person> events, int K) {
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
        PriorityQueue<Integer> startPq = new PriorityQueue<>();
        ArrayList<Meeting> meetings = new ArrayList<>();
        
        for (int i = 0; i < events.size(); i++) {
            Person p = events.get(i);
            if (p.isOpen) {// 시작
                Integer cnt = map.getOrDefault(p.team, 0);
                map.put(p.team, cnt + 1);
                
                if (map.size() == K)
                    startPq.add(p.time);
                
            } else { // 끝 시간
                if (map.size() == K) {
                    int start = startPq.remove();
                    meetings.add(new Meeting(start, p.time));
                }
                
                Integer cnt = map.getOrDefault(p.team, 0);
                if (cnt == 1) map.remove(p.team); // 0카운트면 map에서 제거한다 map.size를 계속 이용하기 위해
                else map.put(p.team, cnt - 1);
                
            }
        }
        
        Collections.sort(meetings); // pq가 아니면 소팅은 명시적으로 호출해야한다
        //System.out.println("meetings " + meetings);
        
        return meetings;
    }
    
    static ArrayList<Person> mergePerson(ArrayList<Person>[] personList) {
        ArrayList<Person> events = new ArrayList<>();
        
        for (ArrayList<Person> al : personList) {
            Collections.sort(al); // !!! ArrayList 쓸때 꼭 소팅하자 heap으로 착각말자!
            //System.out.println(al);
            
            if (al.size() == 0) continue; // 부서에 참여자가 없는 경우 존재
            
            Person p0 = al.get(0); // 최초 x1기준 가장 긴 구간.
            for (int i = 1; i < al.size(); i++) {
                Person p1 = al.get(i);
                
                if (p0.x2 >= p1.x1) { // overlap
                    p0.x2 = Math.max(p0.x2, p1.x2);
                } else {
                    events.add(p0); // open 점
                    events.add(new Person(p0.x1, p0.x2, p0.team, p0.x2)); // close점을 추가
                    p0 = p1;
                }
            }
            
            // !!! 마지막을 추가해야 한다
            events.add(p0);
            events.add(new Person(p0.x1, p0.x2, p0.team, p0.x2)); // close점을 추가
        }
        
        events.sort((o1, o2) -> o1.time - o2.time); // event는 time순으로 소팅해야한다
        //System.out.println("events " + events);
        
        return events;
    }
    
    static class Meeting implements Comparable<Meeting> {
        int start, end, diff;
        
        Meeting(int start, int end) {
            this.start = start;
            this.end = end;
            this.diff = end - start;
        }
        
        @Override
        public int compareTo(Meeting o) {
            int t = o.diff - this.diff;
            if (t == 0)
                return this.start - o.start;
            return t;
        }
        
        @Override
        public String toString() {
            return start + " " + end;
        }
    }
    
    static class Person implements Comparable<Person> {
        int time;
        int x1, x2;
        int team;
        boolean isOpen;
        
        Person(int x1, int x2, int team, int time) {
            this.x1 = x1;
            this.x2 = x2;
            this.team = team;
            this.time = time;
            isOpen = (x1 == time) ? true : false;
        }
        
        @Override
        public String toString() {
            return "(" + x1 + ", " + x2 + ")" + time + " team " + team;
        }
        
        @Override
        public int compareTo(Person o) {// x1 작은 순으로 -> x2가 큰 순으로 소팅(x1는 작고, x1이 같다면 구간이 가장 긴 순서)
            int x1 = this.x1 - o.x1;
            if (x1 == 0)
                return o.x2 - this.x2;
            return x1;
        }
    }
}
