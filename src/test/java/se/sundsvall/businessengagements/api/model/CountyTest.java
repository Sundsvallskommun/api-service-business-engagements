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

class CountyTest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(County.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var countyCode = "AB";
		final var countyName = "Aktiebolag";

		final var county = County.builder()
			.withCountyCode(countyCode)
			.withCountyName(countyName)
			.build();

		assertThat(county).hasNoNullFieldsOrProperties();
		assertThat(county.getCountyCode()).isEqualTo(countyCode);
		assertThat(county.getCountyName()).isEqualTo(countyName);

	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(County.builder().build()).hasAllNullFieldsOrProperties();
	}

}
