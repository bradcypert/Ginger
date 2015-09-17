package com.bradcypert.ginger;

import java.lang.annotation.*;

/**
 * Created by brad on 9/16/15.
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Exposed {

}
