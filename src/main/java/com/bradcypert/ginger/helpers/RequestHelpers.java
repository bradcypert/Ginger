package com.bradcypert.ginger.helpers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by brad on 9/16/15.
 */
public class RequestHelpers {
    public static boolean containsParams(spark.Request request,List params){
        List filtered = (List) params.stream().filter(param -> containsParam(request, (String) param)).collect(Collectors.toList());
        if(filtered.size() != params.size()){
            return false;
        } else {
            return true;
        }
    }

    public static boolean containsParam(spark.Request request, String name) {
        if(containsParamAsQueryParameter(request, name) || containsParamAsBodyParameter(request, name)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean containsParamAsQueryParameter(spark.Request request, String name) {
        return request.params(name) != null;
    }

    private static boolean containsParamAsBodyParameter(spark.Request request, String name){
        return request.body().contains("name=\""+name+"\"");
    }


    //TODO: This fella is breaking on POST requests.
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
