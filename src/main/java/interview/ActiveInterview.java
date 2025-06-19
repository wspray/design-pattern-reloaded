package interview;


import java.lang.reflect.Field;

public class ActiveInterview {


    private String name = "SUPER_TEST";

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ActiveInterview activeInterview = new ActiveInterview();
        B b = activeInterview.new B();
        String subName = b.subName;
        System.out.println(subName);
        System.out.println(b.getOutName());

        long a = (int) 11;
        System.out.println(new String("123").equals("123"));
        System.out.println(new String("123") == "123");

        //1.内部类访问
        // 泛型、泛型擦除  反射塞入
        // 类型转换
        //2.集合类 框架结构
        //3.mybatis延迟加载
        //4.Spring循环依赖
        // 常用注解
    }

    class B {
        private String subName = "TEST";

        public String getOutName() {
            return name;
        }
    }

}
