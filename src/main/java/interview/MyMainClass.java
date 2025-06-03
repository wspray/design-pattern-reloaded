package interview;

class OuterClass {
  int x = 10;

  static class InnerClass {
    int y = 5;

  }
}

public class MyMainClass {
  public static void main(String[] args) {
    OuterClass.InnerClass myInner = new OuterClass.InnerClass();
    System.out.println(myInner.y);
  }
}