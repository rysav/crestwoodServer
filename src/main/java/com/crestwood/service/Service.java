package com.crestwood.service;

import java.lang.reflect.Field;

/**
 * Created by ryan on 10/11/17.
 */
public class Service {
    protected Object updateObject(Object o, Object current) throws IllegalAccessException {
        Field[] fields = current.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            if (fields[i].get(o) != null) {
                fields[i].set(current, fields[i].get(o));
            }
        }
        return current;
    }
}
