package interview.java;

/**
*	<p>外部内、匿名内部类 </p>
 *
 * <p>1.匿名内部类是没有访问修饰符的。
 * <p>2.使用匿名内部类时，这个new之后的类首先是要存在的，其次我们要重写new后的类的某个或某些方法。
 * <p>3.匿名内部类访问方法参数时也有和局部内部类同样的限制。
 * <p>4.匿名内部类没有构造方法。
*/
public class OuterAnonymous {
    /**
     *	接口中方法默认为public
     */
    public interface IAnimal{
        void speak();
    }

    public static IAnimal getInnerInstance(String speak){
        return new IAnimal(){
            @Override
            public void speak(){
                System.out.println(speak);
            }};
        	//注意上一行的分号必须有
    }
    
    public static void main(String[] args){
    	//调用的speak()是重写后的speak方法。
        OuterAnonymous.getInnerInstance("小狗汪汪汪！").speak();
    }
}
