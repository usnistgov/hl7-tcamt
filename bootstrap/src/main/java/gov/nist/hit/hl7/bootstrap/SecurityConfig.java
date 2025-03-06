package gov.nist.hit.hl7.bootstrap;

import gov.nist.hit.auth.core.service.HITAuthenticationConfigurer;
import gov.nist.hit.auth.core.service.HITAuthenticationManager;
import gov.nist.hit.hl7.tcamt.auth.config.TokenAuthenticationService;
import gov.nist.hit.hl7.tcamt.auth.model.User;
import gov.nist.hit.hl7.tcamt.auth.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private HITAuthenticationConfigurer<UserInfo, User> hitAuthenticationConfigurer;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers(
                                "/api/login",
                                "/api/register",
                                "/api/password/**",
                                "/api/config/**",
                                "/api/documentations/getAll",
                                "/api/storage/file",
                                "/api/user/**"
                        ).permitAll()
                        .requestMatchers("/api/me").fullyAuthenticated()
                        .requestMatchers("/api/setup-profile").fullyAuthenticated()
                        .requestMatchers("/api/**").access(
                                HITAuthenticationManager.meetsPreRequirements(
                                        AuthenticatedAuthorizationManager.fullyAuthenticated(),
                                        "username"
                                )
                        )
                        .anyRequest().permitAll()
        );

        return hitAuthenticationConfigurer
                .configure(http)
                .addPasswordLogin("/api/login")
                .addOAuth2Login()
                .addDefaultOAuth2FailureHandler()
                .addLogout("/api/logout")
                .configure()
                .build();
    }
}
