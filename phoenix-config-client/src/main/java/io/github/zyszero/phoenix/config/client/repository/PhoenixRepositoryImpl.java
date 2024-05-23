package io.github.zyszero.phoenix.config.client.repository;

import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import io.github.zyszero.phoenix.config.client.config.ConfigMeta;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * default impl for Phoenix Repository
 *
 * @Author: zyszero
 * @Date: 2024/5/23 21:00
 */
@AllArgsConstructor
@Data
public class PhoenixRepositoryImpl implements PhoenixRepository {

    private ConfigMeta meta;

    @Override
    public Map<String, String> getConfig() {
        String listPath = meta.getConfigServer() + "/list?app=" + meta.getApp()
                + "&env=" + meta.getEnv() + "&ns=" + meta.getNs();
        List<Configs> configs = HttpUtils.httpGet(listPath, new TypeReference<>() {
        });
        Map<String, String> resultMap = new HashMap<>();
        configs.forEach(config -> resultMap.put(config.getPkey(), config.getPval()));
        return resultMap;
    }
}
