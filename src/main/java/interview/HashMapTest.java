package interview;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.computeIfPresent(1, (k, v) -> {
            return v + 3;
        });
        System.out.println(map);
    }
}
