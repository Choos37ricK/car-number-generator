import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Loader
{
    static int countOfRegion = 12;
    static int countOfThread = 4;

    public static void main(String[] args) throws Exception
    {
        System.out.println("One file generation time:");
        long avgTime;
        long sum = 0;
        for (int i = 0; i < 3; i++) {
            long returnedTime = oneFileGeneration();
            System.out.println(returnedTime + " ms");
            sum += returnedTime;
        }
        avgTime = sum / 3;
        System.out.println("Average time: " + avgTime + " ms");

        System.out.println("\n\nMulti file generation time:");
        sum = 0;
        for (int i = 0; i < 3; i++) {
            long returnedTime = multiFileGeneration();
            System.out.println(returnedTime + " ms");
            sum += returnedTime;
        }
        avgTime = sum / 3;
        System.out.println("Average time: " + avgTime + " ms");

        System.out.println("\nMulti file generation always is faster");
    }

    private static long oneFileGeneration() throws IOException, ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        File file = new File("res/numbers.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);

        int countOfRegionToThread = countOfRegion / countOfThread;

        /**
         * ExecutorService класс для пула потоков. Количество потоков выполняемых
         * в один момент времени строго фиксировано. (Количество указывается в newFixedThreadPool)
         */
        ExecutorService service = Executors.newFixedThreadPool(countOfThread);

        /**
         * Future класс, который хранит в себе состояние потока.
         * При запуске потока в ExecutorService, он возвращает объект класса Future.
         * Нам он необходим в данном коде для ожидания конца всех потоков.
         *
         * В коллекции содержатся все future для каждого потока.
         */
        List<Future> futures = new ArrayList<>();
        for (int i = 1; i <= countOfThread; i++) {
            int finalCountOfRegionToThread;
            if (i == countOfThread) {
                finalCountOfRegionToThread = countOfRegion - (countOfRegionToThread * (i - 1));
            } else {
                finalCountOfRegionToThread = countOfRegionToThread;
            }
            int finalI = i;
            /**
             * Запускаем поток в executor service
             */
            futures.add(service.submit(() ->
                    new Generator(writer, (finalI - 1) * countOfRegionToThread, finalCountOfRegionToThread).compute()));
        }

        /**
         * Данный метод говорит сервису прекратить
         * ожидание новых потоков после окончания всех запущенных.
         * Необходим для того, чтобы программа, после окончания метода main,
         * не жадала ещё чего-нибудь и прекратила работу.
         */
        service.shutdown();

        /**
         * В данном цикле мы ожидаем, пока все потоки закончат работу.
         * Метод get еобходим для возврата значения из потока.
         * Но так как чтобы вернуть значение, он дожидается его
         * [потока] конца, мы используем его для ожидания.
         */
        for (Future future : futures) {
            future.get();
        }

        writer.flush();
        writer.close();

        long executionTime = System.currentTimeMillis() - start;
        return executionTime;
    }

    private static long multiFileGeneration() throws IOException, ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        int countOfRegionToThread = countOfRegion / countOfThread;

        /**
         * ExecutorService класс для пула потоков. Количество потоков выполняемых
         * в один момент времени строго фиксировано. (Количество указывается в newFixedThreadPool)
         */
        ExecutorService service = Executors.newFixedThreadPool(countOfThread);

        /**
         * Future класс, который хранит в себе состояние потока.
         * При запуске потока в ExecutorService, он возвращает объект класса Future.
         * Нам он необходим в данном коде для ожидания конца всех потоков.
         *
         * В коллекции содержатся все future для каждого потока.
         */
        List<Future> futures = new ArrayList<>();
        for (int i = 1; i <= countOfThread; i++) {
            int finalCountOfRegionToThread;
            if (i == countOfThread) {
                finalCountOfRegionToThread = countOfRegion - (countOfRegionToThread * (i - 1));
            } else {
                finalCountOfRegionToThread = countOfRegionToThread;
            }
            int finalI = i;
            /**
             * Запускаем поток в executor service
             */
            futures.add(service.submit(() ->
            {
                try {
                    new MultifileGenerator((finalI - 1) * countOfRegionToThread, finalCountOfRegionToThread).compute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        }

        /**
         * Данный метод говорит сервису прекратить
         * ожидание новых потоков после окончания всех запущенных.
         * Необходим для того, чтобы программа, после окончания метода main,
         * не жадала ещё чего-нибудь и прекратила работу.
         */
        service.shutdown();

        /**
         * В данном цикле мы ожидаем, пока все потоки закончат работу.
         * Метод get еобходим для возврата значения из потока.
         * Но так как чтобы вернуть значение, он дожидается его
         * [потока] конца, мы используем его для ожидания.
         */
        for (Future future : futures) {
            future.get();
        }

        long executionTime = System.currentTimeMillis() - start;
        return executionTime;
    }
}
