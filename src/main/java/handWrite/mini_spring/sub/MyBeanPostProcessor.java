package handWrite.mini_spring.sub;

import handWrite.mini_spring.BeanPostProcessor;
import handWrite.mini_spring.Component;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object afterInitializeBean(Object bean, String beanName) {
        System.out.println(beanName + "初始化完成");
        return bean;
    }
}
