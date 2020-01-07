import java.io.BufferedWriter;
import java.io.IOException;

public class Generator {

    private BufferedWriter writer;
    private static char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
    private int regionCodeStart;
    private int regionCodeEnd;

    public Generator(BufferedWriter writer, int regionCodeStart, int regionCodeEnd) {
        this.writer = writer;
        this.regionCodeStart = regionCodeStart;
        this.regionCodeEnd = regionCodeEnd;
    }

    protected void compute() {
        StringBuilder stringBuilder;
        for (int regionCode = regionCodeStart + 1; regionCode <= regionCodeStart + regionCodeEnd; regionCode++) {
            for (int number = 1; number < 1000; number++) {
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            try {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(firstLetter)
                                        .append(padNumber(number, 3))
                                        .append(secondLetter)
                                        .append(thirdLetter)
                                        .append(padNumber(regionCode, 3))
                                        .append("\n");
                                writer.write(stringBuilder.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
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
