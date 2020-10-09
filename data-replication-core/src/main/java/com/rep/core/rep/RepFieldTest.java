package com.rep.core.rep;

import org.springframework.stereotype.Component;

/**
 * @author wangye
 * @classname RepFieldTest
 * @date 2020/10/9 11:04
 **/
@Component
public class RepFieldTest implements RepFieldStrategy {
    @Override
    public Object get() {
        return 1;
    }
}
