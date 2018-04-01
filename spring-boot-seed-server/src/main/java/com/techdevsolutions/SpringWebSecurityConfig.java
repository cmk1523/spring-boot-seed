package com.techdevsolutions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    Environment environment;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
            .authorizeRequests()
                .antMatchers(
//                        "/*",
//                        "/assets/**"
                )
                    .permitAll()
                .antMatchers("/api/v1/app/**")
                    .hasRole("USER")
                .antMatchers("/api/v1/**")
                    .hasRole("API")
                .anyRequest()
                    .authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        String basicUserName = environment.getProperty("basic-user-name");
        String basicUserPassword = environment.getProperty("basic-user-password");
        String basicUserRoles = environment.getProperty("basic-user-roles");
        String[] basicUserRolesList = basicUserRoles.split(",");

        String adminUserName = environment.getProperty("admin-name");
        String adminUserPassword = environment.getProperty("admin-password");
        String adminUserRoles = environment.getProperty("admin-roles");
        String[] adminUserRolesList = adminUserRoles.split(",");

        auth.inMemoryAuthentication()
                .withUser(basicUserName).password(basicUserPassword).roles(basicUserRolesList)
                .and()
                .withUser(adminUserName).password(adminUserPassword).roles(adminUserRolesList);
    }
}