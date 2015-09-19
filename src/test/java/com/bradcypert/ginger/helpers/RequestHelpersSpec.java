package com.bradcypert.ginger.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.Test;

public class RequestHelpersSpec {

    spark.Request request = mock(spark.Request.class);

    @Test
    public void containsParamShouldReturnTrueWhenRequestContainsQueryParam(){
        when(request.params("something")).thenReturn("value");

        assertTrue(RequestHelpers.containsParam(request, "something"));
    }

    @Test
    public void containsParamShouldReturnTrueWhenRequestContainsBodyParam(){
        when(request.params("something")).thenReturn("value");

        assertEquals(RequestHelpers.getParam(request, "something"), "value");
    }

    @Test
    public void getParamShouldReturnTheParamWhenRequestContainsQueryParam(){
        when(request.body()).thenReturn("{key: value}");

        assertEquals(RequestHelpers.getParam(request, "key"), "value");
    }

    @Test
    public void getParamShouldReturnTheParamWhenRequestContainsBodyParam(){
        when(request.body()).thenReturn("{key: value}");

        assertEquals(RequestHelpers.getParam(request, "key"), "value");
    }

}
