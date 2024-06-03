package io.github.zyszero.phoenix.config.server.dal;

import org.apache.ibatis.annotations.Select;

/**
 * mapper for dist locks.
 * @Author: zyszero
 * @Date: 2024/6/4 0:18
 */
public interface LocksMapper {

    @Select("select app form locks where id = 1 for update")
    String selectForUpdate();
}
