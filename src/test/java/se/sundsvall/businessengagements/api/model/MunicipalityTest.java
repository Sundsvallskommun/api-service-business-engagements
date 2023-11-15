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

class MunicipalityTest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(Municipality.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var municipalityCode = "2281";
		final var municipalityName = "Sundsvalls Kommun";

		final var municipality = Municipality.builder()
			.withMunicipalityCode(municipalityCode)
			.withMunicipalityName(municipalityName)
			.build();

		assertThat(municipality).hasNoNullFieldsOrProperties();
		assertThat(municipality.getMunicipalityCode()).isEqualTo(municipalityCode);
		assertThat(municipality.getMunicipalityName()).isEqualTo(municipalityName);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(Municipality.builder().build()).hasAllNullFieldsOrProperties();
	}

}
