package com.netease.homework.onlineShopping.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Authorization {
    AuthorityEnum authority();
    QueryTypeEnum queryType();
}