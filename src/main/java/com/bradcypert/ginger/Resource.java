package com.bradcypert.ginger;

import com.bradcypert.ginger.helpers.RequestHelpers;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import spark.Request;
import spark.Response;
import static spark.Spark.*;

public class Resource {
    private Class resourceClass;
    private ArrayList<String> exposedProperties;
    private ArrayList<String> methods;
    private String basePath;
    private Object instance;

    public Resource(Class clazz) {
        this.resourceClass = clazz;
        this.exposedProperties = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.basePath = "";

        try {
            this.instance = this.resourceClass.newInstance();
        } catch (Exception e) {
            this.instance = null;
        }


        generateExposedProperties();
        generateResourceMethods();
    }

    public Object getInstance(){
        return this.instance;
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
                    break;
                case "PATCH":
                    patch(path+"/", this::handlePatch);
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
            Arrays.asList(((Methods) annotation).value()).forEach(this.methods::add)
        );
    }

    private String handleGetAll(Request request, Response response) throws Exception {
        Method fetch = this.resourceClass.getMethod("fetchAll");
        response.type("application/json");

        return (String) fetch.invoke(instance);
    }

    private String handleGet(spark.Request request, Response response) throws Exception {
        Method fetch = this.resourceClass.getMethod("fetch", String.class);
        response.type("application/json");

        return (String) fetch.invoke(instance, request.params("id"));
    }

    private String handlePut(spark.Request request, Response response) throws Exception {
        Method put = this.resourceClass.getMethod("replace", String.class);
        response.type("application/json");
        if(!this.exposedProperties.isEmpty() && !RequestHelpers.containsParams(request, this.exposedProperties)){
            response.status(400);
            return "{error: 'missing required parameters'}";
        } else {
            response.status(200);
            return (String) put.invoke(instance, request.params("id"), new PropertyMap(request, this.exposedProperties));
        }
    }

    private String handleDelete(spark.Request request, Response response) throws Exception {
        Method delete = this.resourceClass.getMethod("remove", String.class);
        response.type("application/json");

        return (String) delete.invoke(instance, request.params("id"));
    }

    private String handlePatch(Request request, Response response) throws Exception{
        Method patch = this.resourceClass.getMethod("update", String.class);
        response.type("application/json");
        if(!this.exposedProperties.isEmpty() && !RequestHelpers.containsParams(request, this.exposedProperties)){
            response.status(400);
            return "{error: 'missing requiredf parameters'}";
        } else {
            response.status(200);
            return (String) patch.invoke(instance, request.params("id"), new PropertyMap(request, this.exposedProperties));
        }
    }

    private String handlePost(spark.Request request, Response response) throws Exception {
        Method save = this.resourceClass.getMethod("save", PropertyMap.class);
        response.type("application/json");

        if(!this.exposedProperties.isEmpty() && !RequestHelpers.containsParams(request, this.exposedProperties)){
            response.status(400);
            return "{error: 'missing required parameters'}";
        } else {
            response.status(201);
            return (String) save.invoke(instance, new PropertyMap(request, this.exposedProperties));
        }
    }

}
