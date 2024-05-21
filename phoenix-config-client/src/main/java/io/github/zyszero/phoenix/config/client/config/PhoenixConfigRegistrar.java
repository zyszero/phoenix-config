package io.github.zyszero.phoenix.config.client.config;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * register phoenix config bean.
 *
 * @Author: zyszero
 * @Date: 2024/5/22 3:18
 */
public class PhoenixConfigRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public  void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println("register PropertySourcesProcessor");

        if (registry.containsBeanDefinition(PropertySourcesProcessor.class.getName())) {
            System.out.println("PropertySourcesProcessor already exists");
            return;
        }

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(PropertySourcesProcessor.class).getBeanDefinition();
        registry.registerBeanDefinition(PropertySourcesProcessor.class.getName(), beanDefinition);
    }
}
