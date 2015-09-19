package com.bradcypert.ginger;

import com.bradcypert.ginger.helpers.RequestHelpers;

import java.util.HashMap;
import java.util.List;

public class PropertyMap extends HashMap {
    protected PropertyMap(spark.Request r, List keys) {
        keys.forEach(key -> {
            this.put(key, RequestHelpers.getParam(r, key.toString()));
        });
    }
}
