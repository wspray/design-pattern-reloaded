package handWrite.mini_spring.sub;

import handWrite.mini_spring.Autowired;
import handWrite.mini_spring.Component;
import handWrite.mini_spring.PostConstruct;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
@Component
public class Cat {

    @Autowired
    private Dog dog;

    @PostConstruct
    public void init() {
        System.out.println("Cat创建了 cat里面有一个属性" + dog);
    }

}
