---
sectionid: sass
sectionclass: h2
parent-id: set-up
title: Activating a Resource
number: 2200
---
Once you've declared a model, like we did above, the final step is simple.
From your main entry point, you can simply write:

{% highlight java %}
//import com.bradcypert.ginger.Resource;

Resource todos = new Resource(Todo.class);
todos.generateRoutes();
{% endhighlight %}

This will generate all the required routes for your Model, and plug them into Spark (the routing framework that is used under the covers). Once your code runs, you should be able to hit `localhost:4567/todo/` in your browser and get a list of your Todos.
