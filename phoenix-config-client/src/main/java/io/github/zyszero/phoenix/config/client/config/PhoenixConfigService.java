package io.github.zyszero.phoenix.config.client.config;

/**
 * phoenix config service
 * @Author: zyszero
 * @Date: 2024/5/22 2:52
 */
public interface PhoenixConfigService {

    String[] getPropertyNames();

    String getProperty(String name);
}
