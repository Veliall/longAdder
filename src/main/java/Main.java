import java.util.Arrays;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by Igor Khristiuk
 * Date: 22.08.2021
 * Time: 12:25
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        LongAdder stat = new LongAdder();

        int[][] markets = new int[][]{
                generateArray(10),
                generateArray(20),
                generateArray(10)
        };

        Thread[] threads = new Thread[3];
        for (int i = 0; i < markets.length; i++) {
            //Джава потребовала объявить как финальную переменную
            //для использования в лямбде
            final int finalI = i;
            threads[i] = new Thread(null, () -> Arrays.stream(markets[finalI]).forEach(stat::add),
                    "Магазин " + i);
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Всего: " + stat.sum());
    }

    public static int[] generateArray(int countOfSoldGoods) {
        int[] result = new int[countOfSoldGoods];
        for (int i = 0; i < countOfSoldGoods; i++) {
            result[i] = (int) (Math.random() * 1000);
        }
        return result;
    }

}
