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

class SoleTraderTest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(SoleTrader.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var firstNames = List.of("someFirstName", "someOtherFirstName");
		final var middleName = "someMiddleName";
		final var surName = "someSurName";
		final var address = Address.builder().build();

		final var soleTrader = SoleTrader.builder()
			.withFirstNames(firstNames)
			.withMiddleName(middleName)
			.withSurName(surName)
			.withAddress(address)
			.build();

		assertThat(soleTrader).hasNoNullFieldsOrProperties();
		assertThat(soleTrader.getFirstNames()).isEqualTo(firstNames);
		assertThat(soleTrader.getMiddleName()).isEqualTo(middleName);
		assertThat(soleTrader.getSurName()).isEqualTo(surName);
		assertThat(soleTrader.getAddress()).isEqualTo(address);

	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(SoleTrader.builder().build()).hasAllNullFieldsOrProperties();
	}

}
