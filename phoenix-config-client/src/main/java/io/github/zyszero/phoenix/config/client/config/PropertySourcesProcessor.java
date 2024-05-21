package io.github.zyszero.phoenix.config.client.config;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Phoenix property sources processor.
 *
 * @Author: zyszero
 * @Date: 2024/5/22 3:01
 */
@Data
public class PropertySourcesProcessor implements BeanFactoryPostProcessor, EnvironmentAware, PriorityOrdered {

    private final static String PHOENIX_PROPERTY_SOURCES = "PhoenixPropertySources";

    private final static String PHOENIX_PROPERTY_SOURCE = "PhoenixPropertySource";

    Environment environment;

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        ConfigurableEnvironment env = (ConfigurableEnvironment) environment;

        if (env.getPropertySources().contains(PHOENIX_PROPERTY_SOURCES)) {
            return;
        }

        // TODO 通过 http 请求 phoenix config server 获取配置
        Map<String, String> config = new HashMap<>();
        config.put("phoenix.a", "dev500");
        config.put("phoenix.b", "b600");
        config.put("phoenix.c", "c700");

        PhoenixConfigService phoenixConfigService = new PhoenixConfigServiceImpl(config);
        PhoenixPropertySource propertySource = new PhoenixPropertySource(PHOENIX_PROPERTY_SOURCE, phoenixConfigService);

        CompositePropertySource composite = new CompositePropertySource(PHOENIX_PROPERTY_SOURCES);
        composite.addPropertySource(propertySource);
        env.getPropertySources().addFirst(composite);
    }

    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
