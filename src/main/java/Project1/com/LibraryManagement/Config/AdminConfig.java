package Project1.com.LibraryManagement.Config;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(1)
public class AdminConfig {
    @Bean
    public FilterRegistrationBean<Filter> librarianLoggingFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter((request, response, chain) -> {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession(false);
            System.out.println("PATH: " + req.getRequestURI());
            System.out.println("Session ID: " + (session != null ? session.getId() : "NO SESSION"));
            chain.doFilter(request, response);
        });
        return registrationBean;
    }

    @Bean
    public SecurityFilterChain librarianSecurityFilterChain(HttpSecurity http) throws Exception{
        return http
                .securityMatcher("/librarian/**")
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().hasRole("ADMIN")
                )
                .formLogin(form -> form
                        .loginPage("/librarian/login")
                        .loginProcessingUrl("/librarian/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/librarian/dashboard",true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/librarian/login")
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/librarian/login")
                        .defaultSuccessUrl("/librarian/home",true)
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
                .rememberMe(remember -> remember
                        .key("someSecretKey")
                        .tokenValiditySeconds(7*24*60*60)
                )
                .build();
    }





}
