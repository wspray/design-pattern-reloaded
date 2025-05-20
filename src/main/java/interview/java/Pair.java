package interview.java;

import java.util.List;

public class Pair<T> {

    private T name;

    public T getFirst() {
        return name;
    }

    /**
     * <T>表示该方法是一个泛型方法，T是一个类型参数。它定义在方法上，意味着这个方法可以返回任意类型的数据，只要调用者指定这个类型，它就返回相应的类型
     */
    public static <T> T getFirst(List<T> list) {
        return list.get(0);
    }

}