package com.nemo.common.config;

import com.nemo.commons.util.CSVData;
import com.nemo.commons.util.CSVUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ConfigDataContainter<T extends IConfigData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigDataContainter.class);
    public static final String DEFAULT_CACHE = "default";
    private Map<String, Map<Object, T>> mapCaches;
    private List<T> list;
    public static Set<String> errKey = new HashSet<>();
    private Class<T> clazz;
    private String fileName;
    private String key;
    private List<String> extraKeyList;
    private Map<String, IConverter> converterMap;
    private IConverter globalConverter;


    public ConfigDataContainter(Class<T> clazz, String fileName, String key, Map<String, IConverter> converterMap, List<String> extraKeyList, IConverter globalConverter) {
        this.clazz = clazz;
        this.fileName = fileName;
        this.key = key;
        this.extraKeyList = extraKeyList;
        this.converterMap = converterMap;
        this.globalConverter = globalConverter;
    }

    public void load(String filePath) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String file = filePath + this.fileName + ".csv";
        CSVData data = CSVUtil.read(file, 3);
        List<T> list = new ArrayList<>();
        //遍历每一行的数据
        Iterator<Map<String, String>> var5 = data.tableRows.iterator();

        Map defaultCache;
        while (var5.hasNext()) {
            defaultCache = var5.next();
            T config = (T)converterObject(defaultCache);
            list.add(config);
        }



        this.list = list;
    }

    private T converterObject(Map<String, String> data) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if(this.globalConverter != null) {
            return (T)this.globalConverter.convert(data); //每一个字段都用转换器转换
        } else {
            T config = this.clazz.newInstance();
            boolean isNull = true;
            Iterator<String> var4 = data.values().iterator();

            String key;
            while (var4.hasNext()) {
                key = var4.next();
                if(key != null && !key.equals("")) {
                    isNull = false;
                    break;
                }
            }

            if (isNull) {
                LOGGER.error("配置表：" + this.fileName + "存在空数据，用EM检查一下，防止getList时候出BUG！");
            }

            var4 = data.keySet().iterator();
            while (var4.hasNext()) {
                key = var4.next(); //每一个字段名
                String value = data.get(key); //每一个字段值
                PropertyDesc desc = ReflectUtil.getPropertyDesc(this.clazz, key);




            }


            return config;
        }
    }



}
