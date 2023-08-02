import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    public static String[] texts = new String[100_000];
    private static AtomicInteger countThree = new AtomicInteger();
    private static AtomicInteger countFour = new AtomicInteger();
    private static AtomicInteger countFive = new AtomicInteger();

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void generationShirtStr() {
        Random random = new Random();
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
    }

    public static boolean isAscendingOrder(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {

        generationShirtStr();

        Thread oneChart = new Thread(() -> {

            for (int i = 0; i < texts.length; i++) {
                if (texts[i].length() == 3 &
                        texts[i].charAt(0) == texts[i].charAt(2) &
                        texts[i].charAt(1) == texts[i].charAt(2)) {
                    countThree.getAndIncrement();
                }
            }
        });

        oneChart.start();

        Thread polindrom = new Thread(() -> {

            for (int i = 0; i < texts.length; i++) {
                if (texts[i].length() == 4) {
                    if (texts[i].charAt(0) == texts[i].charAt(3) &
                            texts[i].charAt(1) == texts[i].charAt(2)) {
                        countFour.getAndIncrement();
                    }
                }
            }
        });

        polindrom.start();

        Thread order = new Thread(() -> {

            for (String text : texts) {
                if (text.length() == 5) {

                    if (isAscendingOrder(text)) {
                       countFive.getAndIncrement();
                    }
                }
            }

        });
        order.start();

        oneChart.join();
        polindrom.join();
        order.join();

        System.out.println("Красивых слов с длиной 3 " + countThree + " шт.");
        System.out.println("Красивых слов с длиной 4 " + countFour + " шт.");
        System.out.println("Красивых слов с длиной 5 " + countFive + " шт.");
    }


}


