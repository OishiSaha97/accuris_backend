package com.datasoft.bkash.ea.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:mail.properties"})
public class ApplicationPropertiesConfig {

}
