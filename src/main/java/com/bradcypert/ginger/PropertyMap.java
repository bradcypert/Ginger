package com.bradcypert.ginger;

import com.bradcypert.ginger.helpers.RequestHelpers;

import java.util.HashMap;
import java.util.List;

/**
 * Created by brad on 9/17/15.
 */
public class PropertyMap extends HashMap {
    protected PropertyMap(spark.Request r, List keys) {
        keys.forEach(key -> {
            this.put(key, RequestHelpers.getParam(r, key.toString()));
        });
    }
}
