package com.heekng.celloct.config.oauth;

import com.heekng.celloct.config.oauth.service.CustomOAuth2UserService;
import com.heekng.celloct.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() //h2-console 화면사용 설정
                .and()
                    .authorizeRequests() //URL별 권한 관리
                        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
//                        .antMatchers("/store/**").hasRole(Role.USER.name())
                        .anyRequest().authenticated() //이외의 요청은 인증된 사용자면 모두 허용한다.
                .and()
                    .logout()
                    .logoutSuccessUrl("/") //로그아웃시 이동 경로
                .and()
                    .oauth2Login() //OAuth2 로그인 기능에 대한 여러 설정 진입점
                    .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보를 가져올 떄의 설정들을 담당한다.
                    .userService(customOAuth2UserService); //소셜 로그인 성공 후 후속 조치를 진행할 UserService 인터페이스의 구현체 등록
    }
}
