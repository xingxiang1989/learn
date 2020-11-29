package com.some.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiangxing
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface BuryPoint {

    String name() default "";
}
