package io.github.zyszero.phoenix.config.client.repository;

import io.github.zyszero.phoenix.config.client.config.ConfigMeta;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * interface to get config form remote.
 *
 * @Author: zyszero
 * @Date: 2024/5/23 20:59
 */
public interface PhoenixRepository {

    static PhoenixRepository getDefault(ConfigMeta meta) {
        return new PhoenixRepositoryImpl(meta);
    }

    Map<String, String> getConfig();

    void addListener(PhoenixRepositoryChangeListener listener);
}
