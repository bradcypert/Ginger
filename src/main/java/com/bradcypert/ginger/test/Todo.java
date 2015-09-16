package com.bradcypert.ginger.test;

import com.bradcypert.ginger.Methods;

@Methods
public class Todo {
    public String name;
    public int id;
    public boolean finished;
    private double someNumber;

    public void save(){}

    public String fetch(){
        return "{something: something}";
    }
}
