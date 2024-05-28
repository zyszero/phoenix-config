package io.github.zyszero.phoenix.config.client.config;

import io.github.zyszero.phoenix.config.client.repository.PhoenixRepository;
import io.github.zyszero.phoenix.config.client.repository.PhoenixRepositoryChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Set;

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
        this.config = event.config();
        if (!config.isEmpty()) {
            Set<String> keySet = config.keySet();
            log.debug("[PHOENIX CONFIG] fire an EnvironmentChangeEvent with keys : {}", keySet);
            this.applicationContext.publishEvent(new EnvironmentChangeEvent(keySet));
        }
    }
}
