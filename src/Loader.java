import java.io.*;
import java.util.concurrent.ForkJoinPool;

public class Loader
{
    public static void main(String[] args) throws Exception
    {
        long start = System.currentTimeMillis();

        //FileOutputStream writer = new FileOutputStream("res/numbers.txt");
        //PrintWriter writer = new PrintWriter("res/numbers.txt");
        File file = new File("res/numbers.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);


        for (int regionCode = 1; regionCode < 10; regionCode++) {
            new ForkJoinPool().invoke(new Generator(writer, regionCode));

        }
        writer.flush();
        writer.close();

        System.out.println((System.currentTimeMillis() - start) + " ms");
    }
}
