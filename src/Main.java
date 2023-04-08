import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static final String LETTERS = "RLRFR";
    public static final int LENGTH = 100;
    public static final int THREADS_SIZE = 1000;
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < THREADS_SIZE; i++){
            new Thread(() -> {
                String route = generateRoute(LETTERS, LENGTH);
                int countR = 0;
                char [] chars = route.toCharArray();
                for (char r : chars){
                    if ( r == 'R'){
                        countR++;
                        }
                    }

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(countR)){
                        sizeToFreq.put(countR, sizeToFreq.get(countR)+1);
                    }else{
                        sizeToFreq.put(countR, 1);
                    }
                }
                }).start();
        }

        int maxValue = Collections.max(sizeToFreq.values());

        List<Integer> max = sizeToFreq.entrySet().stream()
                .filter(entry -> entry.getValue() == maxValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println("Самое частое количество повторений " + max + "(встретилось "
                + sizeToFreq.get(max.get(0)) + " раз)");
        System.out.println("Другие параметры:");
        sizeToFreq.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println("-" + e.getKey() + " (" + e.getValue() + " раз)"));
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}