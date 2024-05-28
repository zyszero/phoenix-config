package io.github.zyszero.phoenix.config.client.repository;

import io.github.zyszero.phoenix.config.client.config.ConfigMeta;

import java.util.Map;

/**
 * @Author: zyszero
 * @Date: 2024/5/26 15:54
 */
public interface PhoenixRepositoryChangeListener {

    void onChange(ChangeEvent event);

    record ChangeEvent(ConfigMeta meta, Map<String, String> config) {

    }
}
