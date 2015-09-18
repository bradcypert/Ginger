# Ginger
Ginger - Opinionated Resource-Based Framework powered by Spark.

1. Define a Model.
2. Declare a Resource.
3. Run your app.

It's that simple.

# Mission
```
"If you’re betwixt and between, trust the one with red hair." – O.R. Melling, The Hunter’s Moon
```

Ginger is your go-to framework for Java webservices with a small footprint. It's built ontop of Spark as to keep the footprint as minimal as possible.  Instructions for use and for contributing are below.

# Use
First off, congratulations on the first steps towards simple RESTful routing for your next Java Service. Let's get started. Let's make a simple Todo List service.

#### Todo.java
```java
import com.bradcypert.ginger.Methods;
import com.bradcypert.ginger.Model;

@Methods
public class Todo implements Model{
    @Exposed public String name;
    @Exposed public int id;
    public boolean finished;
    @Exposed private double someNumber;
    
    //called on Post
    public String save(){}

    //called on Get /todo/:id
    public String fetch(){
        return "{something: something}";
    }
    
    //called on Get /todo/
    public String fetchAll(){
        return "";
    }
    
    //called on Delete
    public String remove(){}
    
    //called on Put
    public String update(){}
}
```

This defines the RESTful route for /todo/.
@Methods annotation signifies "GET", "PUT", "POST", and "DELETE" routes should be created. You can change the value of this annotation to choose which verbs you'd like to support.

Note, the above methods only need to be implemented if you support those HTTP verbs, for example, if you're not allowing people to delete from your API, you don't NEED to implement remove.

Great! Now lets setup the actual route!
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

##Note This is a work in progress. PLEASE do not try to use this in production yet.
