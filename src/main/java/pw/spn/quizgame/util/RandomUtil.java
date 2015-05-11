package pw.spn.quizgame.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class RandomUtil {
    private RandomUtil() {
    }

    public static <T> List<T> getRandomItemsFromList(List<T> list, int numOfItems) {
        if (numOfItems > list.size()) {
            throw new IllegalArgumentException("List size must be greater than sub list size.");
        }
        if (list.size() == numOfItems) {
            return list;
        }

        Set<Integer> alreadyChoosen = new HashSet<>();
        Random random = new Random();

        List<T> result = new ArrayList<>(numOfItems);

        for (int i = 0; i < numOfItems; i++) {
            int index = random.nextInt(list.size());
            while (alreadyChoosen.contains(index)) {
                index = random.nextInt(list.size());
            }
            result.add(list.get(index));
            alreadyChoosen.add(index);
        }

        return result;
    }

    public static <T> T getRandomItemFromList(List<T> list) {
        return getRandomItemsFromList(list, 1).get(0);
    }
}
