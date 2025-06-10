package handWrite.proxy;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public interface MyHandler {

    String functionBody(String methodName);

    default void setProxy(MyInterface proxy) {

    }
}
