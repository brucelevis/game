package com.nemo.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigDataManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigDataManager.class);
    private static final ConfigDataManager INSTANCE = new ConfigDataManager();

    public static ConfigDataManager getInstance() {
        return INSTANCE;
    }

    private ConfigDataManager(){
    }

    public void init(String path) {





    }




}
