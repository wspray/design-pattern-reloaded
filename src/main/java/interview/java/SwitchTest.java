package interview.java;

public class SwitchTest {
        public static void main(String[] args) {
            Object obj = 1;
            switch (obj) {
                case String s:
                    System.out.println("It's a String: " + s);
                    break;
                case Integer i:
                    System.out.println("It's an Integer: " + i);
                    break;
                default:
                    System.out.println("Other type");
            }

        }
}
