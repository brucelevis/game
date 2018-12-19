package com.nemo.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;


public class LogBeanUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(LogBeanUtil.class);

    LogBeanUtil(){
    }

    public static <T> Set<Class<T>> getSubClasses(String packageName, Class<T> parentClass) {
        Set<Class<T>> ret = new LinkedHashSet<>();
        boolean recursive = true;
        String packageDirName = packageName.replace('.', '/');

        try {
            Enumeration dirs = LogBeanUtil.class.getClassLoader().getResources(packageDirName);

            while (dirs.hasMoreElements()) {
                URL url = (URL)dirs.nextElement();





            }





        } catch (Throwable var9) {
            LOGGER.error("读取日志Class文件出错", var9);
        }

        return ret;
    }


}
