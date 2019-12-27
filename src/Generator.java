public class Generator {

    private static char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};

    public static String numbersGenerator(int regionCode) {
        StringBuffer buffer = null;
        for (int number = 1; number < 1000; number++) {
            StringBuilder builder = new StringBuilder();
            buffer = new StringBuffer();
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
