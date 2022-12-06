package main.project.server.security.config;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import main.project.server.security.jwt.service.JwtTokenizer;
import main.project.server.security.oauth.handler.MemberAccessDeniedHandler;
import main.project.server.security.oauth.handler.MemberAuthenticationEntryPoint;
import main.project.server.security.filter.AuthExceptionHandlerFilter;
import main.project.server.security.filter.JwtVerificationFilter;
import main.project.server.security.oauth.handler.OauthSuccessHandler;
import main.project.server.security.oauth.service.OauthService;
import main.project.server.exception.auth.handler.AuthExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

//@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {


    private final OauthService oauthService;

    private final OauthSuccessHandler oauthSuccessHandler;

    private final JwtTokenizer jwtTokenizer;

    private final RedisTemplate redisTemplate;

    private final Gson gson;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers().frameOptions().sameOrigin() // for H2
                .and()
                .csrf().disable()
                .cors(withDefaults())
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new AuthFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                .oauth2Login()
                .successHandler(oauthSuccessHandler)
                .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정을 저장
                .userService(oauthService); // OAuth2 로그인 성공 시, 후작업을 진행할 UserService 인터페이스 구현체 등록


        return httpSecurity.build();
    }

    public class AuthFilterConfigurer extends AbstractHttpConfigurer<AuthFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {

            AuthExceptionHandlerFilter authExceptionHandlerFilter = new AuthExceptionHandlerFilter(new AuthExceptionResolver(gson));
            builder.addFilterBefore(authExceptionHandlerFilter, LogoutFilter.class);
            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, redisTemplate);
            builder.addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }
    }


    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
