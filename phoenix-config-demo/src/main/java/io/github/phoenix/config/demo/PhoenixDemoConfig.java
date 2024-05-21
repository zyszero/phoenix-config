package io.github.phoenix.config.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @Author: zyszero
 * @Date: 2024/5/20 23:27
 */
@Data
@ConfigurationProperties(prefix = "phoenix")
public class PhoenixDemoConfig {

    String a;

}
