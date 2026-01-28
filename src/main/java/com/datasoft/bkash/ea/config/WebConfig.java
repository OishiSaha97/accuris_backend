package com.datasoft.bkash.ea.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebMvc
@EnableCaching
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Value("${image.location.question-image}")
    private String questionImgDir;

    @Value("${file.upload-dir:uploads}")  // ✅ ADD THIS - defaults to "uploads" if not set in properties
    private String uploadDir;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        log.info("Image files location - question: {}", questionImgDir);
        log.info("Upload files location: {}", uploadDir);  // ✅ ADD THIS LOG

        registry.addResourceHandler("/allfiles/**").addResourceLocations("file:" + questionImgDir);

        // ✅ ADD THIS - Serve uploaded files from /uploads/** endpoint
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }
}