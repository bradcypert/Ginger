# Ginger
[![Build Status](https://travis-ci.org/bradcypert/Ginger.svg)](https://travis-ci.org/bradcypert/Ginger) 
[![] (https://img.shields.io/github/license/mashape/apistatus.svg)] (https://github.com/bradcypert/Ginger/blob/master/LICENSE.md)
[![] (https://img.shields.io/badge/docs-1.1.0--RC1-orange.svg)] (http://bradcypert.github.io/Ginger/)
####Opinionated Resource-Based Framework powered by Spark.

1. Define a Model.
2. Declare a Resource.
3. Run your app.

It's that simple.

####Want a Sample App? [Sample App](http://www.github.com/bradcypert/GingerSampleApp).
####Want Docs? [Docs](http://bradcypert.github.io/Ginger/).

# Mission
```
"If you’re betwixt and between, trust the one with red hair." – O.R. Melling, The Hunter’s Moon
```

Ginger is your go-to framework for Java webservices with a small footprint. It's built ontop of Spark as to keep the footprint as minimal as possible.  Instructions for use and for contributing are below.

####Things to know.
* Requires Java8

# Use
First off, congratulations on the first steps towards simple RESTful routing for your next Java Service. Let's get started. Let's make a simple Todo List service.

#### Todo.java
```java
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
```

It's important to note the following things.

1. @Exposed is put on to a member variable that we want to REQUIRE on POST updates, and accept on put updates.
2. You need to implement the ginger.Model Model. The methods it requires are called reflectively, similar to - dare I say - Callbacks in JavaScript.
3. @Methods annotation is required as well. If you want to support `GET`, `PUT`, `POST`, and `DELETE`, you can simply include `@Methods`, else, you can specify the methods you want to support by passing in `@Methods(value = {"GET", "DELETE"})` where value is a String array containing the verbs you want to support. To support them all, simply use `@Methods`.
4. Lastly, all the methods that are required by ginger.Model need to return a VALID JSON string. If they do not, you'll get an "invalid json" error when you hit that route.

####Route Mappings
When you create a new Resource from your class that implements a Model, some magical goodness happens under the hood. I'll do my best to describe these to you here, by showing you a list of mappings, but you can also check out Resource.java to see it yourself!
```
* GET      /myResource/          =>  fetchAll()
* GET      /myResource/:id/      =>  fetch()
* DELETE   /myResource/:id/      =>  remove()
* POST     /myResource/          =>  save()
```

Note: Currently, the above methods need to be implemented, even if your api isn't supporting a specific verb, such as delete. You HAVE to implement the ginger.Model interface fully.

Great! Now lets setup the actual route! This is generally your main java file.
#### TodoService.java
```java
import com.bradcypert.ginger.Resource;

public class TodoService {
    public static void main(String[] args){
        new Resource(Todo.class).generateRoutes();
    }
}
```

And that's it! By default, the server runs on post :4567, so run your app, fire up localhost:4567, and give it a go!

If you need a more in-depth example, [check out this directory](https://github.com/bradcypert/Ginger/tree/master/src/main/java/com/bradcypert/ginger/test). It's the folder that houses the sample-test app, built on Ginger, and should always be up to date with the latest build.
