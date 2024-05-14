package io.github.zyszero.phoenix.config.server.dal;

import io.github.zyszero.phoenix.config.server.model.Configs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * mapper for config table.
 *
 * @Author: zyszero
 * @Date: 2024/5/14 1:06
 */
@Repository
@Mapper
public interface ConfigMapper {
    @Select("select * from configs where app=#{app} and env=#{env} and ns=#{ns}")
    List<Configs> list(String app, String env, String ns);


    @Select("select * from configs where app=#{app} and env=#{env} and ns=#{ns} and pkey=#{pkey}")
    Configs select(String app, String env, String ns, String pkey);

    @Insert("insert into configs(app, env, ns, pkey, pval) values(#{app}, #{env}, #{ns}, #{pkey}, #{pval})")
    int insert(Configs configs);


    @Update("update configs set pval=#{pval} where app=#{app} and env=#{env} and ns=#{ns} and pkey=#{pkey}")
    int update(Configs configs);
}
