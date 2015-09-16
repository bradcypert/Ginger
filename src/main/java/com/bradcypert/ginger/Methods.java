package com.bradcypert.ginger;

import java.lang.annotation.*;

/**
 * Created by brad on 9/15/15.
 */

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Methods {
    String[] value() default {"GET","PUT", "POST", "DELETE"};
}
