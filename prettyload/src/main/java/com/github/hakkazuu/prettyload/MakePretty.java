package com.github.hakkazuu.prettyload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hakkazuu on 2019-07-14 at 19:31.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MakePretty {

    boolean isPretty() default true;

    boolean ignoreChilds() default false;

}