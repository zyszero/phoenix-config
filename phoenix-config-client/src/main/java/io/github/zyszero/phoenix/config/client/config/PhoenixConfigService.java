package io.github.zyszero.phoenix.config.client.config;

import io.github.zyszero.phoenix.config.client.repository.PhoenixRepository;
import io.github.zyszero.phoenix.config.client.repository.PhoenixRepositoryChangeListener;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * phoenix config service
 *
 * @Author: zyszero
 * @Date: 2024/5/22 2:52
 */
public interface PhoenixConfigService extends PhoenixRepositoryChangeListener {

    static PhoenixConfigService getDefault(ApplicationContext applicationContext, ConfigMeta meta) {
        PhoenixRepository repository = PhoenixRepository.getDefault(meta);
        Map<String, String> config = repository.getConfig();
        PhoenixConfigService phoenixConfigService = new PhoenixConfigServiceImpl(applicationContext, config);
        repository.addListener(phoenixConfigService);
        return phoenixConfigService;
    }

    String[] getPropertyNames();

    String getProperty(String name);
}
