package com.bradcypert.ginger.helpers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.stream.Collectors;

public class RequestHelpers {
    public static boolean containsParams(spark.Request request,List params){
        List filtered = (List) params.stream().filter(param -> containsParam(request, (String) param)).collect(Collectors.toList());
        return filtered.size() == params.size();
    }

    public static boolean containsParam(spark.Request request, String name) {
        return containsParamAsQueryParameter(request, name) || containsParamAsBodyParameter(request, name);
    }

    private static boolean containsParamAsQueryParameter(spark.Request request, String name) {
        return request.params(name) != null;
    }

    //Only supports JSON parameters currently. {name: 'something'}
    private static boolean containsParamAsBodyParameter(spark.Request request, String name) {
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(request.body(), JsonObject.class);
        return request.body() != null && json.get(name).getAsString() != null;
    }

    public static String getParam(spark.Request request, String name){
        if(containsParamAsQueryParameter(request, name)){
            return request.params(name);
        } else {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(request.body(), JsonObject.class);
            return json.get(name).getAsString();
        }
    }
}
