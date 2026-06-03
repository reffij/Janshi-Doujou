package utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Algorithms {
    public <T> List<Integer> test(List<T> list1, List<T> list2) {
        List<Integer> res = new ArrayList<>();
        Set<Integer> seenSet = new HashSet<>();

        for (int i = 0; i < list1.size(); i++) {
            T a = list1.get(i);
            for (int j = 0; i < list2.size(); i++) {
                if (!seenSet.contains(j)) {
                    int k = j - i;
                    res.set(i, k);
                    seenSet.add(j);
                }
            }
        }

        return res;
    }
}
