import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 8. 20..
 */
public class P5_Large {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201708/P5/Set3.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int M = sc.nextInt();

            int k = 0;//문명
            ArrayList<Probe> probes = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                int dx = sc.nextInt();
                int dy = sc.nextInt();

                probes.add(new Probe(x, y, k));
                for (int j = 1; j <= M; j++) {
                    probes.add(new Probe(x + (dx * j), y + (dy * j), k));
                }
                k++;
            }

            if (probes.size() != N * (M + 1))
                System.out.println("error");

            //System.out.println(probes);
            Collections.sort(probes, (o1, o2) -> {
                long a = o1.x - o2.x;
                if (a == 0) return (int) (o1.y - o2.y);
                else return (int) a;

            });
            //System.out.println(probes);

            int P = probes.size();
            LinkedHashSet<Integer> set = new LinkedHashSet<>();
            LinkedHashMap<Integer, Probe> mark = new LinkedHashMap<>();
            int preX = Integer.MAX_VALUE;
            int x = 0;
            for (int i = 0; i < P; i++) {
                Probe p = probes.get(i);
                p.id = i;
                set.add(p.x);

                if (preX != p.x)
                    mark.put(p.x, p);

                preX = p.x;
            }

            Object[] AllX = set.toArray();
            //System.out.println(Arrays.toString(AllX));
            //System.out.println(probes);

            //ArrayList[] al = new ArrayList[AllX.length];
            //for (int i = 0; i < al.length; i++) {
            //    al[i] = new ArrayList();
            //}
            //
            //for (int i = 0; i < P; i++) {
            //    Probe p = probes.get(i);
            //    int index = Arrays.binarySearch(AllX, p.x);
            //    al[index].add((p.y));
            //}
            //
            //for (int i = 0; i < al.length; i++) {
            //    Collections.sort(al[i]);
            //    System.out.println(i + " " + al[i]);
            //}
            //
            //long found = 0;
            //for (int i = 0; i < P; i++) {
            //    Probe ip = probes.get(i);
            //
            //    int index = Arrays.binarySearch(AllX, ip.x);
            //    System.out.println(index + " " + ip);
            //    found += findCap(al, index - 2, ip, false);
            //    found += findCap(al, index - 1, ip, false);
            //    found += findCap(al, index, ip, true);
            //    found += findCap(al, index + 1, ip, false);
            //    found += findCap(al, index + 2, ip, false);
            //}

            long capsule = 0;
            for (int i = 0; i < P; i++) {
                Probe ip = probes.get(i);
                //System.out.println("ip " + ip);

                for (int j = i + 1; j < P; j++) {
                    Probe jp = probes.get(j);
                    //System.out.println("jp " + jp);

                    int xdiff = Math.abs(ip.x - jp.x);
                    if (xdiff > 2)
                        break;

                    int ydiff = Math.abs(ip.y - jp.y);
                    if (Math.max(xdiff, ydiff) <= 2) {
                        if (ip.type != jp.type)
                            capsule += 2;
                        //System.out.println("capsule " + capsule + " "+ ip + " -> " + jp);
                    } else {
                        if (ip.x == jp.x && ydiff > 2) {
                            int from = jp.id + 1;
                            int to = P - 1;
                            int index = Arrays.binarySearch(AllX, jp.x);
                            if (index + 1 < AllX.length) {
                                int nextx = (int) AllX[index + 1];
                                Probe next = mark.get(nextx);
                                to = next.id - 1;
                                int mid = searchYDiff(probes, from, to, ip.y); // 5가지 y를 찾아봐서 없으면 다음으로 j 전진
                                if (mid < 0) // not found
                                    j = to;
                                else // found
                                    j = mid-1;
                            }
                        } /*else if (xdiff == 1 && ydiff > 2) {
                            int from = jp.id + 1;
                            int to = P - 1;
                            int index = Arrays.binarySearch(AllX, jp.x);
                            if (index + 1 < AllX.length) {
                                int nextx = (int) AllX[index + 1];
                                Probe next = mark.get(nextx);
                                to = next.id - 1;
                                int mid = searchYDiff(probes, from, to, ip.y); // 5가지 y를 찾아봐서 없으면 다음으로 j 전진
                                if (mid < 0) // not found
                                    j = to;
                                else // found
                                    j = mid-1;
                            }
                        }*/
                    }
                }
            }

            long result = capsule;

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static class Probe {
        int x, y, type;
        int id;

        Probe(int x, int y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        @Override
        public String toString() {
            return id + "[" + x + ", " + y + "]";
        }
    }

    //static long findCap(ArrayList[] al, int index, Probe ip, boolean myX) {
    //    if (index < 0 || index >= al.length) return 0;
    //    int found = 0;
    //    boolean itsme = false;
    //    for (int j = 0; j < al[index].size(); j++) {
    //        int y = (int) al[index].get(j);
    //        if (myX && y == ip.y) itsme = true;
    //
    //        if (Math.abs(y - ip.y) <= 2) {
    //            found++;
    //        }
    //    }
    //
    //    if (found > 0 && itsme) found--;
    //    return found;
    //}

    static int searchYDiff(ArrayList<Probe> probes, int lo, int hi, int y) {
        if (hi < lo) return -1; // ** recursive func always needs base condition to exit
        if (hi >= probes.size()) return -1;

        int target = y;
        int mid = binarySearch(probes, lo, hi, target);
        if (mid < 0)
            mid = binarySearch(probes, lo, hi, target - 2);
        else return mid;
        if (mid < 0)
            mid = binarySearch(probes, lo, hi, target - 1);
        else return mid;
        if (mid < 0)
            mid = binarySearch(probes, lo, hi, target + 1);
        else return mid;
        if (mid < 0)
            mid = binarySearch(probes, lo, hi, target + 2);
        return mid;
    }

    static int binarySearch(ArrayList<Probe> al, int lo, int hi, int target) {
        if (hi < lo) return -1; // ** recursive func always needs base condition to exit

        int mid = lo + (hi - lo) / 2;

        if (al.get(mid).y > target) return binarySearch(al, lo, mid - 1, target);
        else if (al.get(mid).y < target) return binarySearch(al, mid + 1, hi, target);
        else if (al.get(mid).y == target) return mid;

        return -1; // not found
    }
}
