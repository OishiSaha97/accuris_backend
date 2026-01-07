package com.datasoft.bkash.ea.config.security;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Configuration
@EnableWebSecurity
public class GatewayConfiguration implements SecurityFilterChain {

    @Value("${api.gateway.check-token-endpoint}")
    String apiGatewayCheckTokenEndpoint;
    @Value("${server.servlet.context-path}")
    String contextPath;
    @Value("${auth.header.client-id}")
    String clientId;
    @Value("${auth.header.client-secret}")
    String clientSecret;

    private final RestOperations restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/assessment/download/", "/user/forgetPassword/", "/file/zipDownload/", "/query/zipDownload/", "/query/download/", "/v2/api-docs", "/v3/api-docs", "/configuration/", "/swagger-resources/", "/swagger-ui.html","/swagger-ui/index.html", "/webjars/", "/api-docs/", "/actuator/","/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint())
                        .accessDeniedHandler(customAccessDeniedHandler())
                );

        return http.build();
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
        };
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        List<String> allowedURIs = Arrays.asList(
                contextPath + "/assessment/download/",
                contextPath + "/user/forgetPassword/",
                contextPath + "/file/zipDownload/",
                contextPath + "/query/zipDownload/",
                contextPath + "/query/download/",
                contextPath + "/v2/api-docs",
                contextPath + "/v3/api-docs",
                contextPath + "/configuration/",
                contextPath + "/swagger-resources/",
                contextPath + "/swagger-ui.html",
                contextPath + "/swagger-ui/index.html",
                contextPath + "/webjars/",
                contextPath + "/api-docs/",
                contextPath + "/actuator/"
        );

        String requestURI = request.getRequestURI();

        boolean accissibleUriWithoutScurity = allowedURIs.stream().anyMatch(requestURI::startsWith);

        if (accissibleUriWithoutScurity) {
            return true;
        } else {
            try {
                MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
                formData.add("token", request.getHeader("Authorization").replace("Bearer ", ""));

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8)));

                // Call token validation endpoint
                Map<String, Object> tokenInfo = restTemplate.postForObject(apiGatewayCheckTokenEndpoint + "/validate", new HttpEntity<>(formData, headers), Map.class);
                if (tokenInfo != null && tokenInfo.get("active") != null && (boolean) tokenInfo.get("active")) {
                    // Extract user details (modify based on your token response structure)
                    String username = (String) tokenInfo.get("sub"); // Adjust this key as per API response
                    String role = (String) tokenInfo.get("role"); // Adjust key as needed

                    // Create authentication object
                    Authentication auth = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + (Objects.nonNull(role) ? role.toUpperCase() : ""))) // Assign role
                    );
                    // Set authentication in SecurityContext
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(auth);
                    SecurityContextHolder.setContext(securityContext);

                    return true;
                }
                SecurityContextHolder.clearContext();
                return false;
            } catch (Exception e) {
                try {
                    new CustomAuthenticationEntryPoint().commence(request,
                            new Response(),
                            new BadCredentialsException("Invalid token", e));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } finally {
                    SecurityContextHolder.clearContext();
                    return false;
                }
            }
        }
    }

    @Override
    public List<Filter> getFilters() {
        return List.of();
    }

    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }
}

@Component
class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Unauthorized\"}");
    }
}