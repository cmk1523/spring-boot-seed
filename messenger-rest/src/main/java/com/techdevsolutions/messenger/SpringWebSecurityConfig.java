package com.techdevsolutions.messenger;

import com.techdevsolutions.shared.security.CustomAuthenticationManager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {
    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    protected CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    SpringWebSecurityConfig(CustomAuthenticationManager customAuthenticationManager) {
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/app/**").hasAuthority("ROLE_USER")
                .antMatchers("/api/v1/files/**").hasAuthority("ROLE_USER")
                .antMatchers("/api/v1/message/**").hasAuthority("ROLE_USER")
                .antMatchers("/login*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("USER", "API", "ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .authenticationProvider(this.customAuthenticationManager);
//    }
}