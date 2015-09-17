package com.bradcypert.ginger.test;

import com.bradcypert.ginger.Resource;

/**
 * Created by brad on 9/15/15.
 * Temporary file for running the server and testing the spark methods.
 */
public class MainTest {
    public static void main(String args[]) {
        new Resource(Todo.class).generateRoutes();
    }
}
