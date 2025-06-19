package interview.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListFailFastExample {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("Alice");
        list.add("Bob");
        list.add("Charlie");
        System.out.println(list.size());
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            System.out.println(item);
            if ("Bob".equals(item)) {
                list.remove("Bob"); // 正好删除的是倒数第二个元素
                // 尝试修改集合,最后Charlie不会访问到，因为cursor==size (remove后为2了)
            }
        }
//        list3.stream().forEach(System.out::println);

        System.out.println("--------------------");

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("Alice");
        list2.add("Bob");
        list2.add("Charlie");
        list2.add("Dharlie");
        list2.add("Fharlie");
//        for (String s : list2) {
//            System.out.println(s);
//            list2.remove("Bob"); // 尝试修改集合，触发Fail-Fast，报出ConcurrentModificationException
//        }
//        list2.stream().forEach(System.out::println);//正常


//        for (int i = 0; i < list2.size(); i++) {
//            System.out.println(list2.get(i));
//            list2.remove("Charlie");
//        }
//        list2.stream().forEach(System.out::println);//正常

//        for (int i = 0; i < list2.size(); i++) {
//            String s = list2.get(i);
//            if (s.equals("Bob")) {
//                list2.remove(i);
//            }
//        }
//        list2.stream().forEach(System.out::println);//正常


        List<String> list3 = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I"));
        for (int i = 0; i < list3.size(); i++) {
            System.out.println(i + ":" + list3.get(i));
            if ("C".equals(list3.get(i)) || "D".equals(list3.get(i))) {
                list3.remove(i);//remove 调用System.arraycopy导致数组前移，
            }
        }
        System.out.println(list3);
//        [A, B, E, F, G, H, I, J, K]
//        [A, B, D, E, F, G, H, I]

        List<String> list4 = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I"));
//        list4.removeIf(s -> "C".equals(s) || "D".equals(s));
//        System.out.println(list4);
        System.out.println(list4.stream().filter(e -> !("C".equals(e) || "D".equals(e))).collect(Collectors.toList()));
        System.out.println(list4);
    }
}