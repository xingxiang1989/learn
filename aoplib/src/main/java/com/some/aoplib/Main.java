package com.some.aoplib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiangxing
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Main {
}
