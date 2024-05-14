package io.github.zyszero.phoenix.config.server;

import io.github.zyszero.phoenix.config.server.dal.ConfigMapper;
import io.github.zyszero.phoenix.config.server.model.Configs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * config server endpoint.
 *
 * @Author: zyszero
 * @Date: 2024/5/14 1:04
 */
@RestController
public class PhoenixConfigController {

    @Autowired
    private ConfigMapper configMapper;

    Map<String, Long> VERSIONS = new HashMap<>();


    @GetMapping("/list")
    public List<Configs> list(String app, String env, String ns) {
        return configMapper.list(app, env, ns);
    }


    @PostMapping("/update")
    public List<Configs> update(@RequestParam("app") String app,
                                @RequestParam("env") String env,
                                @RequestParam("ns") String ns,
                                @RequestBody Map<String, String> params) {
        params.forEach((k, v) -> insertOrUpdate(new Configs(app, env, ns, k, v)));
        VERSIONS.put(app + "-" + env + "-" + ns, System.currentTimeMillis());
        return configMapper.list(app, env, ns);
    }

    private void insertOrUpdate(Configs configs) {
        Configs conf = configMapper.select(configs.getApp(), configs.getEnv(),
                configs.getNs(), configs.getPkey());
        if (conf == null) {
            configMapper.insert(configs);
        } else {
            configMapper.update(configs);
        }
    }

    @GetMapping("/version")
    public long version(String app, String env, String ns) {
        return VERSIONS.getOrDefault(app + "-" + env + "-" + ns, -1L);
    }
}
