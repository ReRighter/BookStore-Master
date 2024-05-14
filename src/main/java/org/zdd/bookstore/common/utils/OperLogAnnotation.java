package org.zdd.bookstore.common.utils;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLogAnnotation {
    String operType() default "";
}
