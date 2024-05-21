package io.github.phoenix.config.demo;

import io.github.zyszero.phoenix.config.client.annotation.EnablePhoenixConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties(PhoenixDemoConfig.class)
@EnablePhoenixConfig
public class PhoenixConfigDemoApplication {


    @Value("${phoenix.a}")
    String a;

    @Autowired
    private PhoenixDemoConfig phoenixDemoConfig;

    @Autowired
    Environment environment;


    public static void main(String[] args) {
        SpringApplication.run(PhoenixConfigDemoApplication.class, args);
    }


    @Bean
    ApplicationRunner applicationRunner() {

        System.out.println(Arrays.toString(environment.getActiveProfiles()));

        return args -> {
            System.out.println(a);
            System.out.println(phoenixDemoConfig.getA());
        };
    }

}
