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

class CompanyFormTest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(CompanyForm.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var companyFormCode = "AB";
		final var companyFormDescription = "Aktiebolag";

		final var companyForm = CompanyForm.builder()
			.withCompanyFormCode(companyFormCode)
			.withCompanyFormDescription(companyFormDescription)
			.build();

		assertThat(companyForm).hasNoNullFieldsOrProperties();
		assertThat(companyForm.getCompanyFormCode()).isEqualTo(companyFormCode);
		assertThat(companyForm.getCompanyFormDescription()).isEqualTo(companyFormDescription);

	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(CompanyForm.builder().build()).hasAllNullFieldsOrProperties();
	}

}
