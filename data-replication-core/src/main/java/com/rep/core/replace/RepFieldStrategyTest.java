package com.rep.core.replace;

import cn.hutool.core.convert.Convert;
import org.springframework.stereotype.Component;

/**
 * @author wangye
 * @classname RepFieldTest
 * @date 2020/10/9 11:04
 **/
@Component
public class RepFieldStrategyTest implements RepFieldStrategy {
    @Override
    public Object get() {
        int max=10000000,min=1;
        int ran2 = (int) (Math.random()*(max-min)+min);
        return Convert.toLong(ran2);
    }
}
