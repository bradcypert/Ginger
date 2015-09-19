package com.bradcypert.ginger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.Test;
import java.util.ArrayList;

public class PropertyMapSpec {

    spark.Request request = mock(spark.Request.class);

    @Test
    public void constructorShouldMapRequestPropertiesToPropertyMap(){
        ArrayList<String> list = new ArrayList<>();
        when(request.body()).thenReturn("{key: value}");
        list.add("key");

        PropertyMap map = new PropertyMap(request, list);
        assertEquals(map.get("key"), "value");
    }
}
