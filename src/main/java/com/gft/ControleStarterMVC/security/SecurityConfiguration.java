package com.gft.ControleStarterMVC.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserPrincipalDetailsService userPrincipalDetailsService;

    //Configure users access
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        //Authorize with DatabaseAuthentication
        auth.authenticationProvider(authenticationProvider());

        //Authorize with inMemoryAuthentication
//        auth
//                .inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN")
//                .and()
//                .withUser("alex").password(passwordEncoder().encode("alex123")).roles("USER");
    }

    //Configure http requests access
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/logado").authenticated()
                .antMatchers("/admin/**").hasRole("FULL")
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .loginPage("/login")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }

    //Configure password hash coder (Mandatory on this SpringBoot version)
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
