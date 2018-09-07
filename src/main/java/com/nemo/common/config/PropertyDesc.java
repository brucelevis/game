package com.nemo.common.config;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

@Getter
@Setter
public class PropertyDesc {
    public static final PropertyDesc NULL = new PropertyDesc();
    private String name;
    private Class<?> propertyType;
    private Method writeMethod;
    private Method readMethod;
}
