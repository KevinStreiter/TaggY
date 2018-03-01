package ch.fhnw.taggy.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = { "classpath:applicationTest.properties" })
public class TestConfig {

}
