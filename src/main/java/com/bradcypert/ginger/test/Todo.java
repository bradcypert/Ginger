package com.bradcypert.ginger.test;

import com.bradcypert.ginger.Exposed;
import com.bradcypert.ginger.Methods;

@Methods
public class Todo {
    @Exposed public String name;
    @Exposed public int id;
    public boolean finished;

    @Exposed private double someNumber;

    public void save(){}

    public String fetch(){
        return "{something: something}";
    }
}
