package handWrite;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String menuType = "3"; // 可以是 "1"、"2" 或其他值

        if (menuType != null && menuType.matches("^1|2$")) {
            System.out.println("menuType 是有效的值: " + menuType);
        } else {
            System.out.println("menuType 无效或为空");
        }

        Animal myDog = new Dog();
        myDog.makeSoundEntry(); // 输出 "Dog barks"


        String[] s = new String[]{
                "dog", "lazy", "a", "over", "jumps", "fox", "brown", "quick", "A"
        };
        List<String> list = Arrays.asList(s);//使用工具类 Arrays.asList() 把数组转换成集合时，不能使用其修改集合相关的方法
        Collections.reverse(list);
        //没有指定类型的话会报错
        s = list.toArray(String[]::new);
//        s = list.toArray(new String[0]);
        System.out.println(Arrays.toString(s));
    }
}

class Animal {
    void makeSoundEntry() {
        System.out.println("Animal makes a sound");
        afterSound();
    }

    void afterSound() {
        System.out.println("After Parent Animal makes a sound");
    }
}

class Dog extends Animal {
    void afterSound() {
        System.out.println("After Child Dog barks");
    }
}