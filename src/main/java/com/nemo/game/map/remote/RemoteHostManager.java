package com.nemo.game.map.remote;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RemoteHostManager {

    private static RemoteHostManager INSTANCE = new RemoteHostManager();

    private Map<Integer, RemoteHost> hostMap = new ConcurrentHashMap<>();

    private RemoteHostManager() {

    }

    public static RemoteHostManager getInstance() {
        return INSTANCE;
    }

    public RemoteHost findHost(int id) {
        return hostMap.get(id);
    }

    public Map<Integer, RemoteHost> getHostMap() {
        return hostMap;
    }
}
