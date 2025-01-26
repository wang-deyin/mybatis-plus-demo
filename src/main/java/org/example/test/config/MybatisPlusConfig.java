package org.example.test.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wdyin
 * @date 2024/11/1
 **/
@Configuration
@MapperScan("org.example.test.**.mapper")
public class MybatisPlusConfig {

    /**
     * 方法注入
     */
    @Bean
    public CustomizedSqlInjector customizedSqlInjector() {
        return new CustomizedSqlInjector();
    }
}
