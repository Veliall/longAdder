import java.util.Arrays;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by Igor Khristiuk
 * Date: 22.08.2021
 * Time: 12:25
 */
public class VariantWithStatic {

    private static int sum = 0;

    public static void main(String[] args) throws InterruptedException {
        LongAdder stat = new LongAdder();

        int[] market1 = generateArray(20);
        int[] market2 = generateArray(25);
        int[] market3 = generateArray(10);

        Thread t1 = creatThread(market1, stat, "Магазин 1");
        Thread t2 = creatThread(market2, stat, "Магазин 2");
        Thread t3 = creatThread(market3, stat, "Магазин 3");

        Thread[] threads = new Thread[]{
                t1, t2, t3
        };

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Общая выручка: " + sum);
    }

    public static int[] generateArray(int countOfSoldGoods) {
        int[] result = new int[countOfSoldGoods];
        for (int i = 0; i < countOfSoldGoods; i++) {
            result[i] = (int) (Math.random() * 1000);
        }
        return result;
    }

    public static Thread creatThread(int[] market, LongAdder stat, String name) {
        return new Thread(null, () -> {
            Arrays.stream(market).forEach(stat::add);
            sum += (int) stat.sum();
            System.out.printf("Выручка %s: %s\n", Thread.currentThread().getName(), stat.sum());
        }, name);
    }
}
