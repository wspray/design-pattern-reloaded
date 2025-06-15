package interview.java;

import java.util.ArrayList;

public class AssertTest {
    public static void main(String args[]) {
        System.out.println("Hello World"+100+100);
        System.out.println(100+100+"Hello World");

        int a = 1;
        int b = 1;
        assert a == b;
        System.out.println("公众号：Hollis");
        assert a != b : "Hollis";
        System.out.println("博客：www.hollischuang.com");

        ArrayList<Object> objects = new ArrayList<>();
        objects.add("<UNK>");
    }
}