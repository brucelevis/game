package com.nemo.common.config;


import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectUtil {
    private static final ConcurrentHashMap<Class<?>, PropertyDesc[]> propertyDescCache = new ConcurrentHashMap();
    private static final ConcurrentHashMap<String, PropertyDesc> propertyDescSingleCache = new ConcurrentHashMap();
    private static final ConcurrentHashMap<String, Method> methodCache = new ConcurrentHashMap();

    public ReflectUtil() {
    }

    public static PropertyDesc getPropertyDesc(Class<?> clazz, String name) {
        String nameKey = StringUtils.uncapitalize(name); //确保首位是小写
        String key = clazz.getName() + "." + nameKey;




    }


}
