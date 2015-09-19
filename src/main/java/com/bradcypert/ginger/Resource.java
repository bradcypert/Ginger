package com.bradcypert.ginger;

import com.bradcypert.ginger.helpers.RequestHelpers;
import spark.Request;
import spark.Response;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static spark.Spark.*;

public class Resource {
    private Class resourceClass;
    private ArrayList<String> exposedProperties;
    private ArrayList<String> methods;
    private String basePath;

    public Resource(Class clazz) {
        this.resourceClass = clazz;
        this.exposedProperties = new ArrayList<String>();
        this.methods = new ArrayList<String>();
        this.basePath = "";

        generateExposedProperties();
        generateResourceMethods();
    }

    public void setBasePath(String path){
        this.basePath = path;
    }

    public void generateRoutes() {
        String name = this.resourceClass.getSimpleName().toLowerCase();
        String path = this.basePath + "/"+name;
        for(String method: this.methods){
            switch(method){
                case "GET":
                    get(path+"/", this::handleGetAll);
                    get(path+"/:id", this::handleGet);
                    break;
                case "PUT":
                    put(path+"/", this::handlePut);
                    break;
                case "DELETE":
                    delete(path+"/:id", this::handleDelete);
                    break;
                case "POST":
                    post(path+"/", this::handlePost);
            }
        }
    }

    private void generateExposedProperties() {
        Field[] fields = this.resourceClass.getDeclaredFields();
        Arrays.asList(fields).forEach(field ->
                        Arrays.asList(field.getDeclaredAnnotations()).forEach(annotation -> {
                            if (annotation.toString().endsWith("ginger.Exposed()")) {
                                this.exposedProperties.add(field.getName());
                            }
                        })
        );
    }

    private void generateResourceMethods() {
        Annotation[] annotations = this.resourceClass.getAnnotations();
        Arrays.asList(annotations).forEach(annotation ->
                        Arrays.asList(((Methods) annotation).value()).forEach(value -> this.methods.add(value))
        );
    }

    private String handleGetAll(Request request, Response response) throws Exception {
        Method fetch = this.resourceClass.getMethod("fetchAll");
        response.type("application/json");

        return (String) fetch.invoke(this.resourceClass.newInstance());
    }

    private String handleGet(spark.Request request, Response response) throws Exception {
        Method fetch = this.resourceClass.getMethod("fetch", String.class);
        response.type("application/json");

        return (String) fetch.invoke(this.resourceClass.newInstance(), request.params("id"));
    }

    private String handlePut(spark.Request request, Response response) {
        response.type("application/json");
        return "";
    }

    private String handleDelete(spark.Request request, Response response) throws Exception {
        Method delete = this.resourceClass.getMethod("remove", String.class);
        response.type("application/json");

        return (String) delete.invoke(this.resourceClass.newInstance(), request.params("id"));
    }

    private String handlePost(spark.Request request, Response response) throws Exception {
        Method save = this.resourceClass.getMethod("save", PropertyMap.class);
        response.type("application/json");

        if(!this.exposedProperties.isEmpty() && !RequestHelpers.containsParams(request, this.exposedProperties)){
            response.status(400);
            return "{error: 'missing required parameters'}";
        } else {
            response.status(201);
            return (String) save.invoke(this.resourceClass.newInstance(), new PropertyMap(request, this.exposedProperties));
        }

    }

}
