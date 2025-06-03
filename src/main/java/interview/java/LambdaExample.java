package interview.java;

public class LambdaExample {
    public static void main(String[] args) {
        int finalVar = 10; // final
        int effFinalVar = 20; // effectively final
        int nonFinalVar = 30; // 非 final，且会被修改

        Runnable r = () -> {
            System.out.println(finalVar); // 合法
            System.out.println(effFinalVar); // 合法
            // System.out.println(nonFinalVar); // 编译错误：Variable used in lambda expression should be final or effectively final
        };

        nonFinalVar = 35; // 修改了 nonFinalVar
        r.run();
    }
}

//Java 编译器在编译 Lambda 表达式时，会将其转换为一个匿名内部类（实际上叫做“实现了某个函数接口的类”），这个内部类会作为一个类存在，并且会实现 Runnable 接口。
//
//在你的例子中，编译后大致的样子相当于类似这样的代码（省略了一些细节）：
//public static void main(String[] args) {
//    int finalVar = 10;
//    int effFinalVar = 20;
//    int nonFinalVar = 30;
//
//    Runnable r = new Runnable() {
//        @Override
//        public void run() {
//            System.out.println(finalVar);
//            System.out.println(effFinalVar);
//            // System.out.println(nonFinalVar); // 编译错误，不会出现
//        }
//    };
//
//    nonFinalVar = 35; // 改变 original nonFinalVar
//    r.run();
//}

//这个匿名内部类会捕获 finalVar 和 effFinalVar（只要它们没有被修改，编译器就允许捕获“有效最终变量”）。
//它不会捕获 nonFinalVar，因为它不是 final 或 effectively final（其实这会在编译阶段直接报错）。
//你会发现，lambda 表达式底层在字节码层面就是这样的一个匿名类实现。
