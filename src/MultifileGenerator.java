import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MultifileGenerator {

    private static char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
    private File file;
    private FileWriter fileWriter;
    private BufferedWriter writer;
    private int regionCodeStart;
    private int regionCodeEnd;

    public MultifileGenerator(int regionCodeStart, int regionCodeEnd) throws IOException {
        this.regionCodeStart = regionCodeStart + 1;
        this.regionCodeEnd = regionCodeStart + regionCodeEnd;
        this.file = new File(String.format("%s_%d-%d.txt", "res/numbers_of_regions", this.regionCodeStart, this.regionCodeEnd));
        this.fileWriter = new FileWriter(this.file);
        this.writer = new BufferedWriter(this.fileWriter);
    }

    protected void compute() {
        StringBuilder stringBuilder;
        for (int regionCode = regionCodeStart; regionCode <= regionCodeEnd; regionCode++) {
            for (int number = 1; number < 1000; number++) {
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            try {
                                stringBuilder = new StringBuilder();
                                stringBuilder
                                        .append(firstLetter)
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
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.toString());
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
