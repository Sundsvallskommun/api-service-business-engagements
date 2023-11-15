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

class CompanyLocationTest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(CompanyLocation.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var address = Address.builder().build();

		final var companyLocation = CompanyLocation.builder()
			.withAddress(address)
			.build();

		assertThat(companyLocation).hasNoNullFieldsOrProperties();
		assertThat(companyLocation.getAddress()).isEqualTo(address);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(CompanyLocation.builder().build()).hasAllNullFieldsOrProperties();
	}

}
