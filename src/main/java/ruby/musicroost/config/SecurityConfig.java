package ruby.musicroost.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ruby.musicroost.security.CustomLogoutSuccessHandler;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .mvcMatchers("/docs/index.html");   // doc 문서를 제공하기 위해 해당 페이지는 허용
    }

    /**
     * 시큐리티 인증 제외 url 설정
     * @return
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors().and()
                .csrf().disable();


        /** 페이지 권한 설정 */
        http
                .authorizeRequests()
                .mvcMatchers("/signUp", "/login", "/logout").permitAll()
//                .antMatchers("/students/**").hasRole("MANAGER")
//                .antMatchers("/teachers/**").hasRole("MANAGER")
//                .antMatchers("/schedules/**").hasRole("MANAGER")
                .antMatchers("/students/**").authenticated()
                .antMatchers("/teachers/**").authenticated()
                .antMatchers("/schedules/**").authenticated()
                .anyRequest().permitAll();

        // add(get, post), {boardId}/edit (get)

        /** 로그인 처리 */
//        http.formLogin();                                // 스프링 시큐리티가 제공하는 기본 로그인 화면 사용
//                .loginPage("/login").permitAll();      // 가본 로그인 화면 x. 로그인 화면처리 핸들러 지정

        /** 로그아웃 처리 */
        http.logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler);
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/");                 // 로그아웃 처리 후 이동 페이지

        return http.build();
    }

    /**
     * PasswordEncoder Bean 등록
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
