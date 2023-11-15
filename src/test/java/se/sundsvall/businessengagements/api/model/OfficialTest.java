package se.sundsvall.businessengagements.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

class OfficialTest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(Official.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var name = "someName";
		final var legalId = "someLegalId";
		final var location = "someLocation";
		final var roles = List.of("someRole", "someOtherRole");

		final var official = Official.builder()
			.withName(name)
			.withLegalId(legalId)
			.withLocation(location)
			.withRoles(roles)
			.build();

		assertThat(official).hasNoNullFieldsOrProperties();
		assertThat(official.getName()).isEqualTo(name);
		assertThat(official.getLegalId()).isEqualTo(legalId);
		assertThat(official.getLocation()).isEqualTo(location);
		assertThat(official.getRoles()).isEqualTo(roles);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(Official.builder().build()).satisfies(official -> {
			assertThat(official).hasAllNullFieldsOrPropertiesExcept("roles");
			assertThat(official.getRoles()).isEmpty();
		});
	}

}
