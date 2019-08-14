package com.github.hakkazuu.prettyload;

import java.lang.reflect.Field;

/**
 * Created by hakkazuu on 2019-07-14 at 19:43.
 */
class AnnotationAnalyzer {

    protected static boolean isPretty(Class<?> component) {
        for(Field field : component.getFields()) {
            MakePretty makePretty = field.getAnnotation(MakePretty.class);
            return makePretty.isPretty();
        }
        return false;
    }

    protected static boolean isIgnoreChilds(Class<?> component) {
        for(Field field : component.getFields()) {
            MakePretty makePretty = field.getAnnotation(MakePretty.class);
            return makePretty.ignoreChilds();
        }
        return false;
    }

}