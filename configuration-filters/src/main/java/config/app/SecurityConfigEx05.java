package config.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigEx05 {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web
                    .ignoring()
                    .requestMatchers(new AntPathRequestMatcher("/assets/**"));
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
    	.formLogin((formLogin)->{
    		formLogin.loginPage("/user/login");
    	})
    	.authorizeHttpRequests((authorizeRequests)->{
    		/* Access Control List(ACL) -> 꼭 해야 함. */
    		authorizeRequests
    		.requestMatchers(new RegexRequestMatcher("^/board/?(write|delete|modify|reply).*$",null))
    		//.hasAnyRole("ADMIN","USER")
    		.authenticated()
    		.anyRequest().permitAll(); // 나머지 request는 허용
    	});
    
        return http.build();
    }
}
