package io.github.phoenix.config.demo;

import io.github.zyszero.phoenix.config.client.annotation.EnablePhoenixConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties(PhoenixDemoConfig.class)
@EnablePhoenixConfig
@RestController
public class PhoenixConfigDemoApplication {


    @Value("${phoenix.a}.${phoenix.b}")
    String a;

    @Value("${phoenix.b}")
    String b;

    @Autowired
    private PhoenixDemoConfig demoConfig;

    @Autowired
    Environment environment;


    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(PhoenixConfigDemoApplication.class, args);

    }


    @GetMapping("/demo")
    public String demo() {
        return "phoenix.a = " + a + "\n"
                + "phoenix.b = " + b + "\n"
                + "demo.a = " + demoConfig.getA() + "\n"
                + "demo.b = " + demoConfig.getB() + "\n";
    }


    @Bean
    ApplicationRunner applicationRunner() {

        System.out.println(Arrays.toString(environment.getActiveProfiles()));

        return args -> {
            System.out.println(a);
            System.out.println(demoConfig.getA());
        };
    }

}
