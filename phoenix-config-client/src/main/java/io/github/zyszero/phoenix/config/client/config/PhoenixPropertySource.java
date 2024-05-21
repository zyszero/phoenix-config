package io.github.zyszero.phoenix.config.client.config;

import io.github.zyszero.phoenix.config.client.config.PhoenixConfigService;
import org.springframework.core.env.EnumerablePropertySource;

/**
 * Phoenix property source.
 *
 * @Author: zyszero
 * @Date: 2024/5/20 23:39
 */
public class PhoenixPropertySource extends EnumerablePropertySource<PhoenixConfigService> {
    public PhoenixPropertySource(String name, PhoenixConfigService source) {
        super(name, source);
    }

    public String[] getPropertyNames() {
        return source.getPropertyNames();
    }

    public Object getProperty(String name) {
        return source.getProperty(name);
    }
}
