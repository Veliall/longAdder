import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by Igor Khristiuk
 * Date: 22.08.2021
 * Time: 13:40
 */
public class VariantWithoutStaticSum {

    public static void main(String[] args) throws InterruptedException {
        LongAdder stat = new LongAdder();
        AtomicInteger sum = new AtomicInteger();

        int[] market1 = generateArray(20);
        int[] market2 = generateArray(25);
        int[] market3 = generateArray(10);

        Thread t1 = new Thread(null, () -> {
            Arrays.stream(market1).forEach(stat::add);
            sum.addAndGet((int) stat.sum());
            System.out.printf("Выручка %s: %s\n", Thread.currentThread().getName(), stat.sum());
        }, "Магазин 1");

        Thread t2 = new Thread(null, () -> {
            Arrays.stream(market2).forEach(stat::add);
            sum.addAndGet((int) stat.sum());
            System.out.printf("Выручка %s: %s\n", Thread.currentThread().getName(), stat.sum());
        }, "Магазин 2");

        Thread t3 = new Thread(null, () -> {
            Arrays.stream(market3).forEach(stat::add);
            sum.addAndGet((int) stat.sum());
            System.out.printf("Выручка %s: %s\n", Thread.currentThread().getName(), stat.sum());
        }, "Магазин 3");

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
}
