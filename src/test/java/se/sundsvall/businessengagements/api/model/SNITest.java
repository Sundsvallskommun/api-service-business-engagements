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

class SNITest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(SNI.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var sniCode = "someSniCode";
		final var sniDescription = "someSniDescription";

		final var sni = SNI.builder()
			.withSniCode(sniCode)
			.withSniDescription(sniDescription)
			.build();

		assertThat(sni).hasNoNullFieldsOrProperties();
		assertThat(sni.getSniCode()).isEqualTo(sniCode);
		assertThat(sni.getSniDescription()).isEqualTo(sniDescription);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(SNI.builder().build()).hasAllNullFieldsOrProperties();
	}

}
