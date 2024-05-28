package io.github.zyszero.phoenix.config.client.repository;

import com.alibaba.fastjson.TypeReference;
import io.github.zyszero.phoenix.config.client.config.ConfigMeta;
import io.github.zyszero.utils.HttpUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * default impl for Phoenix Repository
 *
 * @Author: zyszero
 * @Date: 2024/5/23 21:00
 */
@Data
@Slf4j
public class PhoenixRepositoryImpl implements PhoenixRepository {

    private ConfigMeta meta;

    private Map<String, Long> versionMap = new HashMap<>();

    private Map<String, Map<String, String>> configMap = new HashMap<>();


    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private List<PhoenixRepositoryChangeListener> listeners = new ArrayList<>();

    public PhoenixRepositoryImpl(ConfigMeta meta) {
        this.meta = meta;
        executor.scheduleWithFixedDelay(this::heartbeat, 1, 5, TimeUnit.SECONDS);
    }


    public void addListener(PhoenixRepositoryChangeListener listener) {
        listeners.add(listener);
    }


    @Override
    public Map<String, String> getConfig() {
        String key = meta.genKey();
        if (configMap.containsKey(key)) {
            return configMap.get(key);
        }
        return findAll();
    }

    @NotNull
    private Map<String, String> findAll() {
        String listPath = meta.listPath();
        log.debug("[PHOENIX CONFIG] list all configs from Phoenix Config Server.");
        List<Configs> configs = HttpUtils.httpGet(listPath, new TypeReference<>() {
        });
        Map<String, String> resultMap = new HashMap<>();
        configs.forEach(config -> resultMap.put(config.getPkey(), config.getPval()));
        return resultMap;
    }


    private void heartbeat() {
        String versionPath = meta.versionPath();
        Long version = HttpUtils.httpGet(versionPath, new TypeReference<>() {
        });

        String key = meta.genKey();
        Long oldVersion = versionMap.getOrDefault(key, -1L);
        if (version > oldVersion) { // 配置发生了变化
            log.debug("[PHOENIX CONFIG] current={}, old={}", version, oldVersion);
            log.debug("[PHOENIX CONFIG] need update new configs");
            versionMap.put(key, version);
            Map<String, String> newConfigs = findAll();
            configMap.put(key, newConfigs);
            listeners.forEach(listener -> listener.onChange(new PhoenixRepositoryChangeListener.ChangeEvent(meta, newConfigs)));
        }
    }
}
