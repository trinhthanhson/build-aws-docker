package ptithcm.tttn.config;


import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import ptithcm.tttn.service.impl.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    public AppConfig(UserDetailsServiceImpl customerUserDetails) {
        this.userDetailsService = customerUserDetails;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/user/**").permitAll()
                .antMatchers("/api/staff/**").hasAnyAuthority("STAFF","MANAGER","SHIPPER")
                .antMatchers("/api/manager/**").hasAnyAuthority("MANAGER","STAFF")
                .and()
                .addFilterBefore(new JwtTokenValidator(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and().httpBasic()
                .and().formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*")); // Cho phép tất cả các nguồn
        configuration.setAllowedMethods(Collections.singletonList("*")); // Cho phép tất cả các phương thức (GET, POST, PUT, DELETE, v.v.)
        configuration.setAllowedHeaders(Collections.singletonList("*")); // Cho phép tất cả các headers
        configuration.setAllowCredentials(true); // Cho phép gửi thông tin xác thực (credentials)
        configuration.setExposedHeaders(Arrays.asList("Authorization")); // Các header được expose
        configuration.setMaxAge(3600L); // Cấu hình thời gian cache CORS

        return request -> configuration;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
