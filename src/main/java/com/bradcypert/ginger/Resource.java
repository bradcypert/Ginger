package com.bradcypert.ginger;

import spark.Request;
import spark.Response;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import java.util.ArrayList;
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
        for (Field field: this.resourceClass.getFields()) {
            this.exposedProperties.add(field.getName());
        }
    }

    private void generateResourceMethods() {
        for (Annotation annotation: this.resourceClass.getAnnotations()) {
            Methods methods = (Methods) annotation;
            for(String method: methods.value()) {
                this.methods.add(method);
            }
        }
    }


    public void generateRoutes() {
        String name = this.resourceClass.getSimpleName();

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
                case "PATCH":
                    patch("/"+name+"/", this::handlePatch);
            }
        }
    }

    private String handleGet(Request request, Response response) {
        return "";
    }

    private String handlePut(Request request, Response response) {
        return "";
    }

    private String handleDelete(Request request, Response response) {
        return "";
    }

    private String handlePatch(Request request, Response response) {
        return "";
    }

}
