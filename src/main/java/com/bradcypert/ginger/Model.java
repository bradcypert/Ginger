package com.bradcypert.ginger;

/**
 * Created by brad on 9/17/15.
 */
public interface Model {
    String save(PropertyMap map);
    String fetch(String id);
    String fetchAll();
    String remove(String id);
}
