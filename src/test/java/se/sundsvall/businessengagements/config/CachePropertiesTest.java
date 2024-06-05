package se.sundsvall.businessengagements.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.businessengagements.BusinessEngagements;

@SpringBootTest(classes = BusinessEngagements.class)
@ActiveProfiles("junit")
class CachePropertiesTest {

	@Autowired
	private CacheProperties cacheProperties;

	@Test
	void test() {
		assertThat(cacheProperties.enabled()).isFalse();
		assertThat(cacheProperties.expireAfterWrite()).isEqualTo(Duration.parse("PT5S"));
		assertThat(cacheProperties.maximumSize()).isEqualTo(10);
	}
}
