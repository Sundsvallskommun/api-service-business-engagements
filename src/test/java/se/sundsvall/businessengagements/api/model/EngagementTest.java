package se.sundsvall.businessengagements.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

class EngagementTest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(Engagement.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var organizationName = "someOrganizationName";
		final var organizationNumber = "someOrganizationNumber";
		final var organizationId = "someOrganizationId";

		final var engagement = Engagement.builder()
			.withOrganizationName(organizationName)
			.withOrganizationNumber(organizationNumber)
			.withOrganizationId(organizationId)
			.build();

		assertThat(engagement).hasNoNullFieldsOrProperties();
		assertThat(engagement.getOrganizationName()).isEqualTo(organizationName);
		assertThat(engagement.getOrganizationNumber()).isEqualTo(organizationNumber);
		assertThat(engagement.getOrganizationId()).isEqualTo(organizationId);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(Engagement.builder().build()).hasAllNullFieldsOrProperties();
	}

}
