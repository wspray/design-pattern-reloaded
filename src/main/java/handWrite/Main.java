package handWrite;

public class Main {
    public static void main(String[] args) {
        String menuType = "3"; // 可以是 "1"、"2" 或其他值

        if (menuType != null && menuType.matches("^1|2$")) {
            System.out.println("menuType 是有效的值: " + menuType);
        } else {
            System.out.println("menuType 无效或为空");
        }
    }
}