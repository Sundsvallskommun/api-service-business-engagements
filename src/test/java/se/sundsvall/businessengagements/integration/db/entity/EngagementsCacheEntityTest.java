package se.sundsvall.businessengagements.integration.db.entity;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static com.google.code.beanmatchers.BeanMatchers.registerValueGenerator;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;

import java.time.LocalDateTime;
import java.util.Random;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import se.sundsvall.businessengagements.api.model.BusinessEngagementsResponse;

class EngagementsCacheEntityTest {

	@BeforeAll
	static void setup() {
		registerValueGenerator(() -> now().plusDays(new Random().nextInt()), LocalDateTime.class);
	}

	@Test
	void testBean() {
		MatcherAssert.assertThat(EngagementsCacheEntity.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var id = 1L;
		final var partyId = "somePartyId";
		final var updated = now();
		final var response = BusinessEngagementsResponse.builder().build();

		final var engagementsCacheEntity = EngagementsCacheEntity.builder()
			.withId(id)
			.withPartyId(partyId)
			.withUpdated(updated)
			.withResponse(response)
			.build();

		assertThat(engagementsCacheEntity).hasNoNullFieldsOrProperties();
		assertThat(engagementsCacheEntity.getId()).isEqualTo(id);
		assertThat(engagementsCacheEntity.getPartyId()).isEqualTo(partyId);
		assertThat(engagementsCacheEntity.getUpdated()).isEqualTo(updated);
		assertThat(engagementsCacheEntity.getResponse()).isEqualTo(response);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(EngagementsCacheEntity.builder().build()).hasAllNullFieldsOrProperties();
	}

}
