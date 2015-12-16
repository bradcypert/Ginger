package com.bradcypert.ginger;

public interface Model {
    default String save(PropertyMap map) { return "{\"Save is set up\": false}"; }
    default String fetch(String id) { return "{\"Fetch is set up\": false}"; }
    default String fetchAll() { return "{\"FetchAll is set up\": false}"; }
    default String remove(String id) { return "{\"Remove is set up\": false}"; }
    default String update(String id, PropertyMap map) { return "{\"Remove is set up\": false}"; }
    default String replace(String id, PropertyMap map) { return "{\"Remove is set up\": false}"; }
}
