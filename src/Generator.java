import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.RecursiveTask;

public class Generator extends RecursiveTask<> {

    private static BufferedWriter writer = new BufferedWriter(new FileWriter("res/numbers.txt"));
    private static char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
    private BufferedWriter writer;
    private int regionCode;

    public Generator(BufferedWriter writer, int regionCode) {
        this.writer = writer;
        this.regionCode = regionCode;
    }

    @Override
    protected void compute() {
        for (int number = 1; number < 1000; number++) {
            for (char firstLetter : letters) {
                for (char secondLetter : letters) {
                    for (char thirdLetter : letters) {
                        try {
                            writer.write(firstLetter);
                            writer.write(padNumber(number, 3));
                            writer.write(secondLetter);
                            writer.write(thirdLetter);
                            writer.write(padNumber(regionCode, 2));
                            writer.write("\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return buffer.toString();
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
