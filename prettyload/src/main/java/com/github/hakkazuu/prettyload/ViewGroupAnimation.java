package com.github.hakkazuu.prettyload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewGroupAnimation {

    String tag() default "";

    int placeholderDrawableResId() default 0;

    int placeholderLayoutResId() default 0;

    int[] placeholderLayoutViewIds() default {};

    int placeholderCount() default 0;

    int startColorResId() default 0;

    int endColorResId() default 0;

    int orientation() default PrettyLoad.ORIENTATION_HORIZONTAL;

}