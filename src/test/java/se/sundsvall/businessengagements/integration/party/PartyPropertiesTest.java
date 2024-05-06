package se.sundsvall.businessengagements.integration.party;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.businessengagements.BusinessEngagements;

@SpringBootTest(classes = BusinessEngagements.class)
@ActiveProfiles("junit")
class PartyPropertiesTest {

	@Autowired
	private PartyProperties properties;

	@Test
	void verifyProperties() {
		assertThat(properties.getConnectTimeout()).isEqualTo(Duration.ofSeconds(3));
		assertThat(properties.getOauth2ClientId()).isEqualTo("fake-id");
		assertThat(properties.getOauth2ClientSecret()).isEqualTo("fake-secret");
		assertThat(properties.getOauth2TokenUrl()).isEqualTo("http://localhost/token");
		assertThat(properties.getReadTimeout()).isEqualTo(Duration.ofSeconds(4));
		assertThat(properties.getUrl()).isEqualTo("http://localhost/party");
	}
}
