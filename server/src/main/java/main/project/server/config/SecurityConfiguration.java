package main.project.server.config;

import lombok.RequiredArgsConstructor;
import main.project.server.jwt.JwtTokenizer;
import main.project.server.jwt.JwtVerificationFilter;
import main.project.server.oauth.handler.OauthSuccessHandler;
//import main.project.server.oauth.service.OauthService;
import main.project.server.oauth.service.OauthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {


    private final OauthService oauthService;

    private final OauthSuccessHandler oauthSuccessHandler;

    private final JwtTokenizer jwtTokenizer;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers().frameOptions().sameOrigin() // for H2
                .and()

                .csrf().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
//                .authenticationEntryPoint()
//                .accessDeniedHandler()
                .and()
                .apply(new AuthFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
//                        .antMatchers(HttpMethod.POST, "/*/auth/**").hasAnyRole("USER","HOST")
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
            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer);

            builder.addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }
    }





}
