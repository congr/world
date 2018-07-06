import java.io.*;

public class FileTest {
    static final String filename = "/Users/cutececil/git/world/Test/sample.in";

    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("sample.out")));

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            String[] str = br.readLine().split(" ");
            System.out.println(str[0]);
        }

        //bw.write(String.valueOf(123));
        //bw.newLine();

        if (br != null) br.close();
        if (isr != null) isr.close();
        if (fis != null) fis.close();
    }
}
