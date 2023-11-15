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

class FiscalYearTest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(FiscalYear.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var fromDay = 1;
		final var toDay = 3;
		final var fromMonth = 4;
		final var toMonth = 6;

		final var fiscalYear = FiscalYear.builder()
			.withFromDay(fromDay)
			.withToDay(toDay)
			.withFromMonth(fromMonth)
			.withToMonth(toMonth)
			.build();

		assertThat(fiscalYear).hasNoNullFieldsOrProperties();
		assertThat(fiscalYear.getFromDay()).isEqualTo(fromDay);
		assertThat(fiscalYear.getToDay()).isEqualTo(toDay);
		assertThat(fiscalYear.getFromMonth()).isEqualTo(fromMonth);
		assertThat(fiscalYear.getToMonth()).isEqualTo(toMonth);

	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(FiscalYear.builder().build()).satisfies(
			fiscalYear -> {
				assertThat(fiscalYear.getFromDay()).isZero();
				assertThat(fiscalYear.getToDay()).isZero();
				assertThat(fiscalYear.getFromMonth()).isZero();
				assertThat(fiscalYear.getToMonth()).isZero();
			})
		;
	}

}
