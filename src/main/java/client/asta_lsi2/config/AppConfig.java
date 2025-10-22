package client.asta_lsi2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Value("${api.url}")
    private String apiUrl;

    @Bean
    public WebClient webClient() {
        //pr identifier les requetes en interne
        return WebClient.builder()
                .baseUrl(apiUrl)
                .build();
    }
}