package com.nemo.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConfigDataManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigDataManager.class);
    private static final ConfigDataManager INSTANCE = new ConfigDataManager();
    private final Map<Class<?>, ConfigDataContainer<?>> configContainters = new HashMap<>();

    public static ConfigDataManager getInstance() {
        return INSTANCE;
    }

    private ConfigDataManager(){
    }

    public void init(String path) {
        String xmlPath = "data_config.xml";

        try {
            List<ConfigDataContainer<?>> configDatas = ConfigDataXmlParser.parse(xmlPath);
            LOGGER.info("配置条数：" + configDatas.size());
            Iterator<ConfigDataContainer<?>> var4 = configDatas.iterator();

            while (var4.hasNext()) {
                ConfigDataContainer<?> container = var4.next();
                container.load(path);


            }
        } catch (Exception var6) {
            LOGGER.error("加载配置文件失败...", var6);
            throw new RuntimeException(var6);
        }





    }




}
