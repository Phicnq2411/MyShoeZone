package com.philconnal.shoezone.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableJpaAuditing(auditorAwareRef = "aware")
public class ApplicationConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**", "/api/**").permitAll()
                .anyRequest()
                .authenticated();
    }

    @Bean
    public AuditorAware<String> aware() {
        return () -> Optional.of("Administrator");
    }
}
