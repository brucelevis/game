package com.nemo.common.config;

import java.util.HashMap;
import java.util.Map;

public class ConfigCacheManager {
    private final Map<Class<?>, IConfigCache> caches = new HashMap<>();
    private static final ConfigCacheManager INSTANCE = new ConfigCacheManager();

    private ConfigCacheManager(){
    }

    public static ConfigCacheManager getInstance() {
        return INSTANCE;
    }
}
