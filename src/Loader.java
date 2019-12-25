import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Loader
{
    public static void main(String[] args) throws Exception
    {
        long start = System.currentTimeMillis();

        FileOutputStream writer = new FileOutputStream("res/numbers.txt");
        //PrintWriter writer = new PrintWriter("res/numbers.txt");

        char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
        for (int regionCode = 1; regionCode < 100; regionCode++) {
            for (int number = 1; number < 1000; number++) {
                StringBuilder builder = new StringBuilder();
                StringBuffer buffer = new StringBuffer();
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            builder.append(firstLetter);
                            builder.append(padNumber(number, 3));
                            builder.append(secondLetter);
                            builder.append(thirdLetter);
                            builder.append(padNumber(regionCode, 2));
                            builder.append("\n");
                        }
                    }
                }
                //writer.write(builder.toString());
                writer.write(builder.toString().getBytes());
            }
        }
        writer.flush();
        writer.close();

        System.out.println((System.currentTimeMillis() - start) + " ms");
    }

    private static String padNumber(int number, int numberLength)
    {
        StringBuilder numberStr = new StringBuilder(Integer.toString(number));
        int padSize = numberLength - numberStr.length();
        for(int i = 0; i < padSize; i++) {
            numberStr.insert(0, '0');
        }
        return numberStr.toString();
    }
}
