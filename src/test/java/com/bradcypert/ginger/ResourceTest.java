package com.bradcypert.ginger;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by brad on 9/15/15.
 */
public class ResourceTest {
    Resource resource = new Resource(SampleResource.class);

    @Test
    public void testGenerateRoutes(){

    }

    @Before
    public void main(String args[]){
        resource.generateRoutes();
    }

}
