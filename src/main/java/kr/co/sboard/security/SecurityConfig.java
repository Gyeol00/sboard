package kr.co.sboard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 설정 클래스
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        // 로그인 설정 (폼 로그인)
        http.formLogin(login -> login.loginPage("/user/login") // 로그인 페이지
                .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 주소
                .failureUrl("/user/login?code=100") // 로그인 실패 후 주소
                .usernameParameter("uid") // 아이디 입력 데이터 name html
                .passwordParameter("pass")); // 비밀번호 입력 데이터 name. html

        // 로그아웃 설정
        http.logout(logout -> logout.logoutUrl("/user/logout")
                .invalidateHttpSession(true) // 세션을 인벨리데이션 시킴
                .logoutSuccessUrl("/user/login?code=101"));

        /*
            인가 설정
             - MyUserDetails 권한 목록 생성하는 메서드(getAuthorities)에서 접두어로 ROLE_ 입력해야 hasRole, hasAnyRole 권한 처리됨
             - Spring Security는 기본적으로 인가 페이지에 대해 login 페이지로 redirect 수행, login 후 요청했던 페이지로 이동

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/").permitAll()
                //.requestMatchers("/admin/**").hasRole("ROLE_ADMIN") // MyUserDetails에서 ROLE_ 를 안 붙혔을 경우 "ROLE_ADMIN" 이렇게 하면 됨. 귀찮은 방법.
                .requestMatchers("/admin/**").hasRole("ADMIN") // admin으로 시작하는 모든 페이지에서 ADMIN 사용자만 가능하게
                .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/staff/**").hasAnyRole("ADMIN", "MANAGER", "STAFF")
                .anyRequest().permitAll());
                */
        // 403 Forbidden 에러 (관리자 에러)

        // 기타 보안 설정
        http.csrf(AbstractHttpConfigurer::disable); /* csrf?
                                                        - 토큰을 가지고 요청 응답하는 통신 방식.
                                                        - 서버와 클라이언트와 통신할 때 보안 토큰.
                                                        - 요청, 응답할 때 토큰으로 통신함.
                                                        - disable = 꺼짐. */

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // 비밀번호 암호화 시켜주는 인코더

        // Security 암호화 인코더 설정
        // 같은 암호에 대해서도 서로 다른 암호화문을 만들어줌.
        return new BCryptPasswordEncoder();
    }

}