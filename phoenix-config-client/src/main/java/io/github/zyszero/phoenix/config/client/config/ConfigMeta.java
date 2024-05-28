package io.github.zyszero.phoenix.config.client.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * config meta info.
 *
 * @Author: zyszero
 * @Date: 2024/5/23 21:12
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfigMeta {

    private String app;

    private String env;

    private String ns;

    private String configServer;

    public String genKey() {
        return this.app + "_" + this.env + "_" + this.ns;
    }


    public String listPath() {
        return path("list");
    }


    public String versionPath() {
        return path("version");
    }


    private String path(String context) {
        return this.configServer + "/" + context + "?app=" + this.app
                + "&env=" + this.env + "&ns=" + this.ns;
    }
}
