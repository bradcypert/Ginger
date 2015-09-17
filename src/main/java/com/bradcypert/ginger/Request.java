package com.bradcypert.ginger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by brad on 9/16/15.
 */
public class Request extends spark.Request {
    public boolean containsParams(List params){
        List filtered = (List) params.stream().filter(param -> containsParam((String) param)).collect(Collectors.toList());
        if(filtered.size() != params.size()){
            return false;
        } else {
            return true;
        }
    }

    public boolean containsParam(String name) {
        if(this.params(name) != null || this.body().contains(name)) {
            return true;
        } else {
            return false;
        }
    }
}
