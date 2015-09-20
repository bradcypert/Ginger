---
sectionid: section-types
sectionclass: h3
parent-id: content
number: 3110
title: Property Map
---
Currently, the Property Map class is a minimal extension on Java's HashMap, that simply implementes a custom constructor.

##### public PropertyMap(spark.Request request, List mappings)

The constructor simply takes in a spark.Request and a List of strings. These strings are then iterated over, and the associated params are pulled out and stored on the underlying HashMap object. **Odds are, you probably won't NEED this class, and can simply treat it like a HashMap whenever you see it being used**.

{% highlight ruby %}
request     =>    spark.Request object
mappings    =>    List of Strings that you'd like to pull from the map.
{% endhighlight %}
