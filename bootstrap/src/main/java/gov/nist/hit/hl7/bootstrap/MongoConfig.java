package gov.nist.hit.hl7.bootstrap;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages={"gov.nist.hit"})
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Value("${db.host}")
	private String HOST;
	@Value("${db.port}")
	private String PORT;
	@Value("${db.name}")
	private String NAME;
	@Value("${db.username:}")
	private String USERNAME;
	@Value("${db.password:}")
	private String PASSWORD;
	@Value("${db.auth.source:}")
	private String AUTH_SOURCE;

	@Override
	@Nonnull
	public String getDatabaseName() {
		return NAME;
	}

	@Override
	protected void configureClientSettings(MongoClientSettings.Builder builder) {
		if(USERNAME != null && PASSWORD != null && !USERNAME.isEmpty() && !PASSWORD.isEmpty()) {
			MongoCredential credential = MongoCredential.createCredential(
					USERNAME,
					AUTH_SOURCE,
					PASSWORD.toCharArray()
			);
			builder.credential(credential);
		}
		builder.applyToClusterSettings(settings -> {
			settings.hosts(Collections.singletonList(
					new ServerAddress(HOST, Integer.parseInt(PORT))
			));
		});
	}

}