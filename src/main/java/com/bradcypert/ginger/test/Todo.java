package com.bradcypert.ginger.test;

import com.bradcypert.ginger.Exposed;
import com.bradcypert.ginger.Model;
import com.bradcypert.ginger.Methods;

@Methods
public class Todo implements Model {
    @Exposed public String name;
    @Exposed public int id;
    public boolean finished;

    @Exposed private double someNumber;

    @Override
    public String save() {
        return "";
    }

    @Override
    public String fetch() {
        return "{something: something}";
    }

    @Override
    public String fetchAll() {
        return null;
    }

    @Override
    public String remove() {
        return null;
    }
}
