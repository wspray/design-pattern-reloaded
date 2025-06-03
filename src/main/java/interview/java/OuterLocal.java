package interview.java;


/**
 *	外部类、局部内部类
 *
 * 类前不能有访问修饰符。
 * <p>1.仅在此方法内使用。
 * <p>2.无法创造静态信息。
 * <p>3.可以直接访问方法内的局部变量和参数（有限制，下面详谈），但是不能更改。
 * <p>4.可以随意的访问外部类的任何信息。
 * ---------------------------------------------------------------------------------------------------
 * <p>局部内部类访问局部变量的限制
 * Variable ‘xxx’ is accessed from within inner class, needs to be final or effectively final
 *---------------------------------------------------------------------------------------------------
 * <p>下面来分析下这个问题:
 * <p>追究其根本原因就是作用域中变量的生命周期导致的;
 *
 * <p>首先需要知道的一点是: 内部类和外部类是处于同一个级别的，内部类不会因为定义在方法中就会随着方法的执行完毕就被销毁。
 *
 * <p>这里就会产生问题：当外部类的方法结束时，局部变量就会被销毁了，但是内部类对象可能还存在(只有没有人再引用它时，才会死亡)。
 * <p>这里就出现了一个矛盾：内部类对象访问了一个不存在的变量。
 * <p>为了解决这个问题，就将局部变量复制了一份作为内部类的成员变量，这样当局部变量死亡后，内部类仍可以访问它，实际访问的是局部变量的"copy"。
 *
 * <p>可见方法中的局部变量实际上确实会复制为内部类的成员变量使用。
 *
 * <p>问题又出现了：将局部变量复制为内部类的成员变量时，必须保证这两个变量是一样的，
 * <p>也就是如果我们在内部类中修改了成员变量，方法中的局部变量也得跟着改变，怎么解决问题呢？
 * <p>就将局部变量设置为final，对它初始化后，我就不让你再去修改这个变量，就保证了内部类的成员变量和方法的局部变量的一致性。这实际上也是一种妥协。
 * <p>若变量是final时：
 *  <p>若是基本类型，其值是不能改变的，就保证了copy与原始的局部变量的值是一样的；
 *  <p>若是引用类型，其引用是不能改变的，保证了copy与原始的变量引用的是同一个对象。
 */
public class OuterLocal {
    /**
     * 属性和方法
     */
    private int outerVariable = 1;
    /**
     * 外部类定义的属性
     */
    private int commonVariable = 2;
    /**
     * 静态的信息
     */
    private static int outerStaticVariable = 3;

    /**
     * 成员外部方法
     */
    public void outerMethod() {
        System.out.println("我是外部类的outerMethod方法");
    }

    /**
     * 静态外部方法
     */
    public static void outerStaticMethod() {
        System.out.println("我是外部类的outerStaticMethod静态方法");
    }

    /**
     * 程序的入口
     */
    public static void main(String[] args) {
        OuterLocal outer = new OuterLocal();
        outer.outerCreatMethod(100);
    }

    /**
     * 成员方法，内部定义局部内部类
     */
    public void outerCreatMethod(int value) {
        /**
         * 女性
         */
        boolean sex = false;

        /**
         * 局部内部类，类前不能有访问修饰符
         */
        class Inner {

            private int innerVariable = 10;
            private int commonVariable = 20;
			/**
			*	局部内部类方法
			*/
            public void innerShow() {
                System.out.println("innerVariable:" + innerVariable);
                //局部变量
                System.out.println("是否男性:" + sex);
//                sex=false; 报错
                System.out.println("参数value:" + value);
//                value=120; 报错
                //调用外部类的信息
                System.out.println("outerVariable:" + outerVariable);
                System.out.println("内部的commonVariable:" + commonVariable);
                System.out.println("外部的commonVariable:" + OuterLocal.this.commonVariable);
                OuterLocal.this.commonVariable=3;
                System.out.println("外部的commonVariable2:" + OuterLocal.this.commonVariable);
                System.out.println("outerStaticVariable:" + outerStaticVariable);
                outerMethod();
                outerStaticMethod();
            }
        }
        //局部内部类只能在方法内使用
        Inner inner = new Inner();
        inner.innerShow();
    }
}
