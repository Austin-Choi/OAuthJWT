package com.loginstudy.OAuthJWT.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        //jwt를 발급해서 stateless 상태이기 때문에
        //csrf 공격을 방어할 필요 없음
        http.csrf((auth)->auth.disable());

        //jwt와 OAuth 방식의 로그인을 진행할 것이기 때문에
        //httpbasic, form 형태의 로그인은 꺼 줌
        http.formLogin((auth)->auth.disable());

        http.httpBasic((auth)->auth.disable());

        //oauth2
        http.oauth2Login(Customizer.withDefaults());

        //경로별 인가 작업
        http.authorizeHttpRequests((auth)-> auth
                // 루트 경로만 permitall
                .requestMatchers("/").permitAll()
                // 나머지는 권한 필요
                .anyRequest().authenticated());

        //jwt로 인증 인가를 할 것이기 때문에 stateless로 설정
        //세션 설정 : STATELESS
        http.sessionManagement((session)->session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
