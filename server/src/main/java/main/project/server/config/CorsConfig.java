package main.project.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // 내 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있도록 함
        config.addAllowedOriginPattern("*"); // 모든 ip 응답을 허용
        config.addAllowedHeader("*"); // 모든 헤더에 응답을 허용
        config.addAllowedMethod("*"); // 모든 메서드 허용
        config.addExposedHeader("Authorization"); // 브라우져에서 응답 http 헤더에서 토큰을 추출할 수 있게 함

        source.registerCorsConfiguration("/**", config); // url에 모든 config 적용
        return new CorsFilter(source);
    }
}
