package com.bradcypert.ginger.test;

import com.bradcypert.ginger.Exposed;
import com.bradcypert.ginger.Model;
import com.bradcypert.ginger.Methods;
import com.bradcypert.ginger.PropertyMap;

@Methods
public class Todo implements Model {
    @Exposed public String name;
    @Exposed public int id;
    public boolean finished;

    @Exposed private double someNumber;

    @Override
    public String save(PropertyMap map) {
        return "{\"somejazz\": \"jazz\"}";
    }

    @Override
    public String fetch(String id) {
        return "{\"name\": \"Work Out\", \"completed\": false}";
    }

    @Override
    public String fetchAll() {
        return "[{\"name\": \"Work Out\", \"completed\": false}, {\"name\": \"Sleep in\", \"completed\": true}]";
    }

    @Override
    public String remove(String id) {
        return "{\"deleted\": true}";
    }
}
