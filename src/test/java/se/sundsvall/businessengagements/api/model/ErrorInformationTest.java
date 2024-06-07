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
class ErrorInformationTest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(ErrorInformation.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	ErrorInformation errorInformation = new ErrorInformation();

	@Test
	void testAddErrorDescription(final SoftAssertions softly) {
		errorInformation.addErrorDescription("1", "error description");
		softly.assertThat(errorInformation.getErrorDescriptions().get("1")).isEqualTo("error description");
		softly.assertThat(errorInformation.getHasErrors()).isTrue();
	}

	@Test
	void testAddErrorDescription_whenMissingCode_shouldAddGenericCode(final SoftAssertions softly) {
		errorInformation.addErrorDescription(null, "error description");
		softly.assertThat(errorInformation.getErrorDescriptions().get("-1")).isEqualTo("error description");
		softly.assertThat(errorInformation.getHasErrors()).isTrue();
	}

	@Test
	void testDuplicateError_shouldNotBeAdded(final SoftAssertions softly) {
		errorInformation.addErrorDescription("1", "error description");
		errorInformation.addErrorDescription("1", "error description");
		softly.assertThat(errorInformation.getErrorDescriptions().get("1")).isEqualTo("error description");
		softly.assertThat(errorInformation.getErrorDescriptions().size()).isEqualTo(1);
		softly.assertThat(errorInformation.getHasErrors()).isTrue();
	}

	@Test
	void noDirtOnCreatedBean() {
		assertThat(ErrorInformation.builder().build()).satisfies(errorInfo -> {
			assertThat(errorInfo).hasAllNullFieldsOrPropertiesExcept("errorDescriptions");
			assertThat(errorInfo.getErrorDescriptions()).isEmpty();
		});
	}

}
