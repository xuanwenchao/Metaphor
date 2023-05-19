package com.xuanwenchao.metaphor.annotation;

import androidx.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Xuan Wenchao
 * @Package com.xuanwenchao.metaphor.annotation
 * @Description: It is a annotation whitch named MetaphorRes that is used for container view be predefined
 * @date Dec 02,2022
 */

@Inherited
@Target(ElementType.TYPE) //for class in activity, fragment, dialog ...
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaphorRes {

    /**
    @Description: can predefined one or above container view resource
    @return: the array type to resource ID
    */
    @IdRes
    int[] containerViewIDs() default 0;
}
