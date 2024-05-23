package io.github.zyszero.phoenix.config.client.config;

import io.github.zyszero.phoenix.config.client.repository.PhoenixRepository;

/**
 * phoenix config service
 * @Author: zyszero
 * @Date: 2024/5/22 2:52
 */
public interface PhoenixConfigService {

    static PhoenixConfigService getDefault(ConfigMeta meta)  {
        PhoenixRepository repository = PhoenixRepository.getDefault(meta);
        return new PhoenixConfigServiceImpl(repository.getConfig());
    }



    String[] getPropertyNames();

    String getProperty(String name);
}
