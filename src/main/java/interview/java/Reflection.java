package interview.java;

import java.lang.reflect.Field;

public class Reflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        //在不改变s引用的前提下，输出abcd
        String s1 = new String("abc");
        Field field = String.class.getDeclaredField("value");
        field.setAccessible(true);
        field.set(s1,"abcd".getBytes());//jdk8可行
        System.out.println(s1);

//        在Java 8及以下版本中，反射可以访问几乎所有内容，包括私有字段。
//        但是在Java 9中引入了模块系统，反射访问私有字段时需要模块开放相应的包。如果未开放，就会抛出InaccessibleObjectException。
//        添加jvm参数 --add-opens java.base/java.lang=design.pattern.reloaded

        String s2="abc";
        System.out.println(s1==s2);
        String s3 = s2.intern();//从字符串常量池中获取，没有就创建一个在字符串常量池并返回常量池中的对象
        System.out.println(s2==s3);

        Integer i1=100;
        Integer i2=100;
        System.out.println(i1==i2);
        Integer i3=128;
        Integer i4=128;
        System.out.println(i3==i4);
//        Java 从 Java 5 开始引入了一个 Integer 缓存机制，用于优化小整数的性能。具体来说，
//        Integer 类维护了一个缓存数组，用于存储 -128 到 127 之间的整数。
//        当通过 Integer.valueOf 或自动装箱创建这些范围内的 Integer 对象时，会直接返回缓存中的对象，而不是创建新的对象。
//        Java 中的其他基本数据类型也都有对应的包装类，并提供了类似的装箱和拆箱方法。
        Float f = Float.valueOf(3.14f); // 装箱
        float floatValue = f.floatValue();   // 拆箱
        // 注意：Integer,Byte,Short,Long缓存-128到127
        //      Character缓存0到127
        //      Boolean总是使用缓存。缓存 true false
        //     Float 和 Double 没有缓存机制。
        //     Float.valueOf 和 Double.valueOf 方法用于装箱操作，但不会缓存对象，每次调用都会创建新的对象。
    }
}
