---
sectionclass: h2
sectionid: collection-entries
parent-id: content
number: 3100
title: Resource
---
The Resource is the main code generator in Ginger. The Methods are as follows.

##### public Resource(Class clazz)
The constructor is called to set up the initial resource. You need to pass in the class of an object that implements the ginger.Model interface.

Code Example:

{% highlight java %}
Resource todos = new Resource(Todo.class);
{% endhighlight %}


##### public void generateRoutes()
Generate routes needs to be called before you can actually use the routes provided. Generally, you'll see something like this.

{% highlight java %}
new Resource(Todo.class).generateRoutes();
{% endhighlight %}

This will generate all the applicable routes based on your `@Methods` declaration in your Model.

##### public void setBasePath(String path)
`setBasePath` will prepend a string onto the routes for the resource. For example:

{% highlight java %}
Resource todos = new Resource(Todo.class);
todos.generateRoutes();
{% endhighlight %}

The above example will generate, for example, `localhost:4567/todo/`.

Perhaps, you want to prepend `/api/v1` to the resource. You can do so with the following.

{% highlight java %}
Resource todos = new Resource(Todo.class);
todos.setBasePath("/api/v1");
todos.generateRoutes();
{% endhighlight %}
