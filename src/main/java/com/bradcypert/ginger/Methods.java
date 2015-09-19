package com.bradcypert.ginger;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Methods {
    String[] value() default {"GET", "PUT", "POST", "DELETE"};
}
