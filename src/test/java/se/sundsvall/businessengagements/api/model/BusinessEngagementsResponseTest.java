package se.sundsvall.businessengagements.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
class BusinessEngagementsResponseTest {

	private final BusinessEngagementsResponse response = new BusinessEngagementsResponse();

	@Test
	void testBean() {
		MatcherAssert.assertThat(BusinessEngagementsResponse.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testAddEngagementWithValues(final SoftAssertions softly) {
		response.addEngagement("A Company", "16321654987");

		softly.assertThat(response.getEngagements().get(0).getOrganizationName()).isEqualTo("A Company");
		softly.assertThat(response.getEngagements().get(0).getOrganizationNumber()).isEqualTo("16321654987");
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(BusinessEngagementsResponse.builder().build()).hasAllNullFieldsOrProperties();
	}

}
