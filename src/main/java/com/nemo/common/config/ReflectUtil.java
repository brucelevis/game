package com.nemo.common.config;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectUtil {
    private static final ConcurrentHashMap<Class<?>, PropertyDesc[]> propertyDescCache = new ConcurrentHashMap();
    private static final ConcurrentHashMap<String, PropertyDesc> propertyDescSingleCache = new ConcurrentHashMap();
    private static final ConcurrentHashMap<String, Method> methodCache = new ConcurrentHashMap();

    public ReflectUtil() {
    }

    public static PropertyDesc[] getPropertyDescs(Class<?> clazz) {
        PropertyDesc[] ps = propertyDescCache.get(clazz);
        if(ps == null) {
            PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(clazz);
            ps = new PropertyDesc[pds.length];

            for(int i = 0; i < pds.length; i++) {
                PropertyDescriptor pd = pds[i];
                PropertyDesc p = new PropertyDesc();
                String name = StringUtils.uncapitalize(pd.getName());
                p.setName(name);
                p.setPropertyType(pd.getPropertyType());
                p.setReadMethod(pd.getReadMethod());
                p.setWriteMethod(pd.getWriteMethod());
                ps[i] = p;
            }

            propertyDescCache.putIfAbsent(clazz, ps);
        }
        return ps;
    }

    public static PropertyDesc getPropertyDesc(Class<?> clazz, String name) {
        String nameKey = StringUtils.uncapitalize(name); //确保首位是小写
        String key = clazz.getName() + "." + nameKey;
        PropertyDesc pd = propertyDescSingleCache.get(key);
        if(pd == null) {
            PropertyDesc[] ps = getPropertyDescs(clazz);

            for(int i = 0; i < ps.length; i++) {
                PropertyDesc tmp = ps[i];
                if(nameKey.equals(tmp.getName())) {
                    pd = tmp;
                }
            }

            if(pd != null) {
                propertyDescSingleCache.putIfAbsent(key, pd);
            } else {
                propertyDescSingleCache.putIfAbsent(key, PropertyDesc.NULL);
            }
        } else if(pd == PropertyDesc.NULL) {
            pd = null;
        }

        return pd;
    }


}
