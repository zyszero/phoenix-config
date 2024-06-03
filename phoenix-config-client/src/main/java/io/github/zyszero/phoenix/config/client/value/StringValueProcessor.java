package io.github.zyszero.phoenix.config.client.value;

import io.github.zyszero.phoenix.config.client.util.PlaceholderHelper;
import io.github.zyszero.utils.FieldUtils;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * process spring value:
 * 1. 扫描所有的 Spring Value，保存起来
 * 2. 在配置更新时，更新所有的 Spring Value
 *
 * @Author: zyszero
 * @Date: 2024/5/28 22:34
 */
@Slf4j
public class StringValueProcessor implements BeanPostProcessor, BeanFactoryAware, ApplicationListener<EnvironmentChangeEvent> {

    static final PlaceholderHelper HELPER = PlaceholderHelper.getInstance();
    static final MultiValueMap<String, SpringValue> VALUE_HOLDER = new LinkedMultiValueMap<>();


    @Setter
    private BeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 第一步：扫描所有的 Spring Value，保存起来
        FieldUtils.findAnnotatedField(bean.getClass(), Value.class).forEach(
                field -> {
                    log.info("[PHOENIX CONFIG] >> find spring value: {}", field);
                    Value value = field.getAnnotation(Value.class);
                    HELPER.extractPlaceholderKeys(value.value()).forEach(key -> {
                        log.info("[PHOENIX CONFIG] >> find spring value: {}", key);
                        SpringValue springValue = new SpringValue(bean, beanName, key, value.value(), field);
                        VALUE_HOLDER.add(key, springValue);
                    });
                });
        return bean;
    }


    /**
     * 监听配置更新事件
     * 等价 @EventListener
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        log.info("[PHOENIX CONFIG] >> update spring values for keys: {}", event.getKeys());
        // 第二步：在配置更新时，更新所有的 Spring Value
        event.getKeys().forEach(key -> {
            log.info("[PHOENIX CONFIG] >> update spring value for key: {}", key);
            List<SpringValue> springValues = VALUE_HOLDER.get(key);
            if (springValues == null || springValues.isEmpty()) {
                return;
            }

            springValues.forEach(springValue -> {
                try {
                    log.info("[PHOENIX CONFIG] >> update spring value: {} for key {}", springValue, key);
                    Object value = HELPER.resolvePropertyValue((ConfigurableBeanFactory) beanFactory,
                            springValue.getBeanName(), springValue.getPlaceholder());
                    log.info("[PHOENIX CONFIG] >> update value: {} for holder {}", value, springValue.getPlaceholder());
                    springValue.getField().setAccessible(true);
                    springValue.getField().set(springValue.getBean(), value);
                } catch (IllegalAccessException e) {
                    log.error("[PHOENIX CONFIG] >> update spring value error", e);
                }
            });

        });
    }
}
