package io.github.zyszero.phoenix.config.client.annotation;

import io.github.zyszero.phoenix.config.client.config.PhoenixConfigRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Phoenix config client entrypoint
 * @Author: zyszero
 * @Date: 2024/5/20 21:39
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Import({PhoenixConfigRegistrar.class})
public @interface EnablePhoenixConfig {
}
