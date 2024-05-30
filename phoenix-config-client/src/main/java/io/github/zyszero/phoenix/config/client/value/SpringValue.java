package io.github.zyszero.phoenix.config.client.value;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * spring value.
 *
 * @Author: zyszero
 * @Date: 2024/5/28 22:55
 */
@Data
@AllArgsConstructor
public class SpringValue {
    private Object bean;

    private String beanName;

    private String key;

    private String placeholder;

    private Field field;

}
