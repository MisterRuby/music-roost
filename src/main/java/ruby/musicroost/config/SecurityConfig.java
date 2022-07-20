package ruby.musicroost.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
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
//                .mvcMatchers("/signUp").permitAll()
//                .antMatchers("/students/**").hasRole("MANAGER")
//                .antMatchers("/teachers/**").hasRole("MANAGER")
//                .antMatchers("/schedules/**").hasRole("MANAGER")
//                .mvcMatchers(HttpMethod.DELETE, "/boards/boardId").authenticated()
//                .mvcMatchers( "/boards/add", "/boards/boardId/edit").authenticated()
//                .mvcMatchers( "/", "/login").permitAll();
//                .mvcMatchers("/", "/query").permitAll()
//                .anyRequest().authenticated();
                .anyRequest().permitAll();

        // add(get, post), {boardId}/edit (get)

        /** 로그인 처리 */
        http.formLogin();                                // 스프링 시큐리티가 제공하는 기본 로그인 화면 사용
//                .loginPage("/login").permitAll();      // 가본 로그인 화면 x. 로그인 화면처리 핸들러 지정
        // TODO - 로그인 페이지 및 등록 페이지. 로그인 처리는 MANAGER 권한 이상일 때만 처리
        // 로그인 페이지는 프론트에서 제공함. 로그인 요청이 성공되면 프론트에서 로그인 관련정보를 가지고 화면을 보여줘야함

        /** 로그아웃 처리 */
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");                 // 로그아웃 처리 후 이동 페이지

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
