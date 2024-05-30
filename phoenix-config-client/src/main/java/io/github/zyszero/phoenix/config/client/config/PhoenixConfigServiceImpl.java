package io.github.zyszero.phoenix.config.client.config;

import io.github.zyszero.phoenix.config.client.repository.PhoenixRepositoryChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * phoenix config service impl
 *
 * @Author: zyszero
 * @Date: 2024/5/22 2:55
 */
@Slf4j
public class PhoenixConfigServiceImpl implements PhoenixConfigService {

    Map<String, String> config;

    ApplicationContext applicationContext;

    public PhoenixConfigServiceImpl(ApplicationContext applicationContext, Map<String, String> config) {
        this.config = config;
        this.applicationContext = applicationContext;
    }

    public String[] getPropertyNames() {
        return this.config.keySet().toArray(new String[0]);
    }

    public String getProperty(String name) {
        return this.config.get(name);
    }

    @Override
    public void onChange(PhoenixRepositoryChangeListener.ChangeEvent event) {
        Set<String> changedKeys = calcChangedKeys(this.config, event.config());
        if (changedKeys.isEmpty()) {
            log.info("[PHOENIX CONFIG] changedKeys return empty, ignore update.");
            return;
        }
        this.config = event.config();
        if (!config.isEmpty()) {
            log.debug("[PHOENIX CONFIG] fire an EnvironmentChangeEvent with keys : {}", changedKeys);
            this.applicationContext.publishEvent(new EnvironmentChangeEvent(changedKeys));
        }
    }

    private Set<String> calcChangedKeys(Map<String, String> oldConfigs, Map<String, String> newConfigs) {
        if (oldConfigs.isEmpty()) {
            return newConfigs.keySet();
        }
        if (newConfigs.isEmpty()) {
            return oldConfigs.keySet();
        }
        Set<String> changedKeys = newConfigs.keySet()
                .stream()
                .filter(key -> !newConfigs.get(key).equals(oldConfigs.get(key)))
                .collect(Collectors.toSet());
        oldConfigs.keySet().stream().filter(key -> !newConfigs.containsKey(key)).forEach(changedKeys::add);
        return changedKeys;
    }
}
