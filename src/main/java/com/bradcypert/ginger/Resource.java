package com.bradcypert.ginger;

import spark.Response;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static spark.Spark.*;

public class Resource {
    private Class resourceClass;
    private ArrayList<String> exposedProperties;
    private ArrayList<String> methods;

    public Resource(Class clazz) {
        this.resourceClass = clazz;
        this.exposedProperties = new ArrayList<String>();
        this.methods = new ArrayList<String>();

        generateExposedProperties();
        generateResourceMethods();
    }

    private void generateExposedProperties() {
        Field[] fields = this.resourceClass.getDeclaredFields();
        Arrays.asList(fields).forEach(field ->
            Arrays.asList(field.getDeclaredAnnotations()).forEach(annotation -> {
                if(annotation.toString().endsWith("ginger.Exposed")){
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


    public void generateRoutes() {
        String name = this.resourceClass.getSimpleName().toLowerCase();
        for(String method: this.methods){
            switch(method){
                case "GET":
                    get("/"+name+"/:id", this::handleGet);
                    break;
                case "PUT":
                    put("/"+name+"/", this::handlePut);
                    break;
                case "DELETE":
                    delete("/"+name+"/", this::handleDelete);
                    break;
                case "POST":
                    post("/" + name + "/", this::handlePost);
            }
        }
    }

    private String handleGet(spark.Request request, Response response) throws Exception {
        Method fetch = this.resourceClass.getMethod("fetch");
        return (String) fetch.invoke(this.resourceClass.newInstance());
    }

    private String handlePut(spark.Request request, Response response) {
        return "";
    }

    private String handleDelete(spark.Request request, Response response) {
        return "";
    }

    private String handlePost(spark.Request request, Response response) throws Exception {
        Request req = (Request) request;
        if(!this.exposedProperties.isEmpty() || !req.containsParams(this.exposedProperties)){
           response.status(400);
        } else {
            Method save = this.resourceClass.getMethod("save");
            return (String) save.invoke(this.resourceClass.newInstance());
        }
        return "";
    }

}
