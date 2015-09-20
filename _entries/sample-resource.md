---
sectionid: config
sectionclass: h2
parent-id: set-up
title: Setting up a Resource
number: 2100
---
Ginger is broken up into two separate pieces - ginger.Model and ginger.Resource.
The Model defines what the RESTful Resource should look like, and the Resource reflects upon that model, to generate your routes for you.

Let's go ahead and write a simple Model. In the spirit of elementary programming, we'll make a Todo list.

{% highlight java %}
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
{% endhighlight %}

There's quite a few things going on here, so let's cover them in detail. First off, we're importing the parts of the Ginger framework that we need. The next thing you'll notice, is the `@Methods` annotation. `@Methods` tells the model that it's going to support `GET`, `POST`, and `DELETE` HTTP verbs. The ginger.Resource (that'll we'll write in a moment) reads this to determine which routes to generate. If, for example, you only wanted to support `GET`, and `POST`, you could write the following: `@Methods(value={"GET", "POST"})`.

**NOTE: Support for more HTTP verbs coming soon.**

Next, you'll noticed that our class implements `ginger.Model`. This interface is necessary to be implemented as the Resource requires the interfaces methods to exist. Those methods are the ones with the `@Override` annotation tied to them.

Here's a simple mapping of what routes map to which Model Methods.

{% highlight groovy %}
GET     /todo/       => fetchAll()
GET     /todo/:id/   => fetch()
POST    /todo/       => save()
DELETE  /todo/:id/   => remove()
{% endhighlight %}

The last thing to note about the Model is the `@Exposed` tag. Any member variables tagged with `@Exposed` will be **REQUIRED** to be sent in on a POST request. For example, in the Java code above, our `POST` route has to either have query or body params of `name`, `id`, and `someNumber`. If these parameters are not there, the server will return a 400 error code.

It's important to keep in mind that your Model is actually just a template. Long term data shouldn't be stored in the model, as Ginger will destroy and create instances of it, as it sees fit. What this means is simple - The methods `save`, `fetch`, `fetchAll`, and `remove` should be fully state-independent. Here is where you would plug in your ORM or execute SQL Queries or whatever you need to do. 
