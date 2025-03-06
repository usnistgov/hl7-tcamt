package gov.nist.hit.hl7.bootstrap;

import gov.nist.hit.auth.core.service.CryptoKey;
import gov.nist.hit.auth.core.service.HITAuthenticationService;
import gov.nist.hit.auth.core.service.JWTTokenAuthenticationService;
import gov.nist.hit.auth.core.service.impl.SimpleKeyPair;
import gov.nist.hit.hl7.tcamt.auth.model.User;
import gov.nist.hit.hl7.tcamt.auth.model.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Configuration
public class SecurityConfigurationBeans {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public CryptoKey cryptoKey() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        return new SimpleKeyPair(pair.getPrivate(), pair.getPublic());
    }

    @Bean
    public JWTTokenAuthenticationService<UserInfo, User> jwtTokenAuthenticationService(
            @Value("${hit.auth.cookie.name}") String COOKIE_NAME,
            CryptoKey keys,
            HITAuthenticationService<UserInfo, User> authenticationService) {
        return new JWTTokenAuthenticationService<>(
                COOKIE_NAME,
                keys,
                authenticationService
        );
    }
}
