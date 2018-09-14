package com.nemo.common.config;

import com.alibaba.druid.util.StringUtils;
import com.nemo.commons.util.FileLoaderUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class ConfigDataXmlParser {

    public static List<ConfigDataContainer<?>> parse(String path) throws DocumentException, FileNotFoundException,
            ClassNotFoundException, InstantiationException, IllegalAccessException {
        SAXReader saxReader = new SAXReader();
        InputStream inputStream = FileLoaderUtil.findInputStreamByFileName(path);
        //从xml文件获取数据
        Document document = saxReader.read(inputStream);
        //获取根元素 文件中是configs
        Element root = document.getRootElement();
        List<ConfigDataContainer<?>> ret = new ArrayList();
        //遍历根元素的configdata子元素
        Iterator data = root.elementIterator("configdata");

        while (data.hasNext()) {
            Element configdata = (Element)data.next();
            //遍历config元素
            Iterator it = configdata.elementIterator("config");

            while (it.hasNext()) {
                Element config = (Element)it.next();
                //获取属性
                String className = config.attributeValue("class");
                String file = config.attributeValue("file");
                String key = config.attributeValue("key");
                //获取属性定义的转换器
                IConverter globalConverter = parseGlobalConverter(config);
                //获取子元素定义的转换器
                Map<String, IConverter> converterMap = parseConvert(config);
                //获取子元素cahches
                List<String> cacheList = parseCaches(config);
                Class<?> clazz = Class.forName(className);
                ConfigDataContainer<?> container = new ConfigDataContainer(clazz, file, key, converterMap, cacheList, globalConverter);
                ret.add(container);
            }
        }
        return ret;
    }

    //子元素转换器
    private static Map<String, IConverter> parseConvert(Element config) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Map<String, IConverter> converterMap = new HashMap<>();
        Iterator convertIt = config.elementIterator("convert");

        while (convertIt.hasNext()) {
            Element convert = (Element) convertIt.next();
            String field = convert.attributeValue("field");
            String converterClassName = convert.attributeValue("converter");
            Class<?> converterClass = Class.forName(converterClassName);
            IConverter converter = (IConverter) converterClass.newInstance();
            converterMap.put(field, converter);
        }

        return converterMap;
    }

    private static List<String> parseCaches(Element config) {
        List<String> ret = new ArrayList<>();
        Element caches = config.element("caches");
        if(caches == null) {
            return ret;
        } else {
            Iterator<Element> mapIt = caches.elementIterator("map");
            while (mapIt.hasNext()) {
                Element map = mapIt.next();
                String key = map.attributeValue("key");
                ret.add(key);
            }
            return ret;
        }
    }

    //所有属性都用到的转换器
    private static IConverter parseGlobalConverter(Element config) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        String globalConverterClasssName = config.attributeValue("converter");
        IConverter globalConverter = null;
        if(!StringUtils.isEmpty(globalConverterClasssName)) {
            Class<?> globalConverterClasss = Class.forName(globalConverterClasssName);
            globalConverter = (IConverter)globalConverterClasss.newInstance();
        }
        return globalConverter;
    }


}
