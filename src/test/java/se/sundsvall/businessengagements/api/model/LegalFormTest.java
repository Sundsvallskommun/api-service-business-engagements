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

class LegalFormTest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(LegalForm.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var legalFormCode = "AB";
		final var legalFormDescription = "Aktiebolag";

		final var legalForm = LegalForm.builder()
			.withLegalFormCode(legalFormCode)
			.withLegalFormDescription(legalFormDescription)
			.build();

		assertThat(legalForm).hasNoNullFieldsOrProperties();
		assertThat(legalForm.getLegalFormCode()).isEqualTo(legalFormCode);
		assertThat(legalForm.getLegalFormDescription()).isEqualTo(legalFormDescription);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(LegalForm.builder().build()).hasAllNullFieldsOrProperties();
	}

}
