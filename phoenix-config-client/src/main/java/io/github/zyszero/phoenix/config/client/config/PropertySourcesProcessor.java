package io.github.zyszero.phoenix.config.client.config;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * Phoenix property sources processor.
 *
 * @Author: zyszero
 * @Date: 2024/5/22 3:01
 */
@Data
public class PropertySourcesProcessor implements BeanFactoryPostProcessor, EnvironmentAware, ApplicationContextAware, PriorityOrdered {

    private final static String PHOENIX_PROPERTY_SOURCES = "PhoenixPropertySources";

    private final static String PHOENIX_PROPERTY_SOURCE = "PhoenixPropertySource";

    private Environment environment;

    private ApplicationContext applicationContext;

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;

        if (configurableEnvironment.getPropertySources().contains(PHOENIX_PROPERTY_SOURCES)) {
            return;
        }

        String app = configurableEnvironment.getProperty("phoenix.config.app", "app1");
        String env = configurableEnvironment.getProperty("phoenix.config.env", "dev");
        String ns = configurableEnvironment.getProperty("phoenix.config.ns", "public");
        String configServer = configurableEnvironment.getProperty("phoenix.config.configServer", "http://localhost:9129");

        ConfigMeta configMeta = new ConfigMeta(app, env, ns, configServer);
        PhoenixConfigService phoenixConfigService = PhoenixConfigService.getDefault(applicationContext, configMeta);

        PhoenixPropertySource propertySource = new PhoenixPropertySource(PHOENIX_PROPERTY_SOURCE, phoenixConfigService);

        CompositePropertySource composite = new CompositePropertySource(PHOENIX_PROPERTY_SOURCES);
        composite.addPropertySource(propertySource);
        configurableEnvironment.getPropertySources().addFirst(composite);
    }

    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
