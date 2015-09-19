package com.bradcypert.ginger;

public interface Model {
    String save(PropertyMap map);
    String fetch(String id);
    String fetchAll();
    String remove(String id);
}
