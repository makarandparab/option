package com.nse.option.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;

@Configuration
public class AppConfig
{
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder)
    {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .disableRedirectHandling() // This is the key method
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        return builder.requestFactory(() -> requestFactory)
                .build();
    }
}
