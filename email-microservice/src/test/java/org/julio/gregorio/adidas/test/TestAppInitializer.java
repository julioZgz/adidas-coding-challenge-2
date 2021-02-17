package org.julio.gregorio.adidas.test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("org.julio.gregorio.adidas")
@PropertySource("classpath:test_application.yml")
@EnableAutoConfiguration
public class TestAppInitializer {

}