package interview;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        System.out.println(map.containsKey(null));
        map.put(null, null);
        System.out.println(map.containsKey(null));
        map.computeIfPresent(1, (k, v) -> {
            return v + 3;
        });
        System.out.println(map);

//        outerLoop:
//        for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < 5; j++) {
//                if (j == 3) {
//                    break outerLoop; // 跳出 outerLoop 标签所在的循环
//                }
//                System.out.println(i + "," + j);
//            }
//        }
    }
}
