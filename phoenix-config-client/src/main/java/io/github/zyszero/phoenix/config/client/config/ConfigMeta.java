package io.github.zyszero.phoenix.config.client.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zyszero
 * @Date: 2024/5/23 21:12
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfigMeta {

    private String app;

    private String env;

    private String ns;

    private String configServer;
}
