package io.github.zyszero.phoenix.config.client.config;

import java.util.Map;

/**
 * phoenix config service impl
 *
 * @Author: zyszero
 * @Date: 2024/5/22 2:55
 */
public class PhoenixConfigServiceImpl implements PhoenixConfigService {

    Map<String, String> config;

    public PhoenixConfigServiceImpl(Map<String, String> config) {
        this.config = config;
    }

    public String[] getPropertyNames() {
        return this.config.keySet().toArray(new String[0]);
    }

    public String getProperty(String name) {
        return this.config.get(name);
    }
}
