package gov.nist.hit.hl7.bootstrap;

import gov.nist.hit.auth.core.service.CryptoKey;
import gov.nist.hit.auth.core.service.impl.JKSCryptoKey;
import gov.nist.hit.hl7.tcamt.auth.model.UserRole;
import gov.nist.hit.hl7.tcamt.auth.repository.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableMongoRepositories("gov.nist.hit.hl7")
@ComponentScan({"gov.nist.hit", "gov.nist.hit.hl7"})
@PropertySource(value="classpath:/auth.yml")
public class App {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @PostConstruct
    public void init() {
        if(userRoleRepository.count() == 0) {
            UserRole userRole = new UserRole();
            userRole.setRole("REGULAR");
            userRoleRepository.save(userRole);
        }
    }

    @Bean
    public CryptoKey cryptoKey() throws Exception {
        JKSCryptoKey cryptoKey = new JKSCryptoKey(
                "/Users/ena3/Projects/hl7-tcamt/tcamt/auth-key.jks",
                "auth-key",
                "auth-key",
                "auth-key"
        );
        return cryptoKey;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
