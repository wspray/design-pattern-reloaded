package handWrite.proxy;

import java.lang.reflect.Field;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class Main {

    public static void main(String[] args) throws Exception {
        MyInterface proxyObject = MyInterfaceFactory.createProxyObject(new PrintFunctionName());
        proxyObject.func1();
        proxyObject.func2();
        proxyObject.func3();
        System.out.println("---------");
        proxyObject = MyInterfaceFactory.createProxyObject(new PrintFunctionName1());
        proxyObject.func1();
        proxyObject.func2();
        proxyObject.func3();
        System.out.println("---------");

        proxyObject = MyInterfaceFactory.createProxyObject(new LogHandler(proxyObject));
        proxyObject.func1();
        proxyObject.func2();
        proxyObject.func3();
    }

    static class PrintFunctionName implements MyHandler {

        @Override
        public String functionBody(String methodName) {
            return " System.out.println(\"" + methodName + "\");";
        }
    }

    static class PrintFunctionName1 implements MyHandler {

        @Override
        public String functionBody(String methodName) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" System.out.println(1);")
                    .append(" System.out.println(\"" + methodName + "\");");
            return stringBuilder.toString();
        }
    }

    static class LogHandler implements MyHandler {

        MyInterface myInterface;//已经存在的MyInterface，用构造函数传入

        public LogHandler(MyInterface myInterface) {
            this.myInterface = myInterface;
        }

        @Override
        public void setProxy(MyInterface proxy) {
            Class<? extends MyInterface> aClass = proxy.getClass();
            Field field = null;
            try {
                field = aClass.getDeclaredField("myInterface");
                field.setAccessible(true);
                field.set(proxy, myInterface);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public String functionBody(String methodName) {
            String context = "  System.out.println(\"before\");\n" +
                    "        myInterface." + methodName + "();\n" +
                    "        System.out.println(\"after\");";
            return context;
        }
    }
}
