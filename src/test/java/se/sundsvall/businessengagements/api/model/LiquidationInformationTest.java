package se.sundsvall.businessengagements.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;

import java.time.LocalDate;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

class LiquidationInformationTest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(LiquidationInformation.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var liquidationCode = "someLiquidationCode";
		final var liquidationDescription = "someLiquidationDescription";
		final var liquidationDate = LocalDate.now();
		final var liquidationType = "liquidationType";

		final var liquidationReasons = List.of(
			LiquidationInformation.LiquidationReason
				.builder()
				.withLiquidationCode(liquidationCode)
				.withLiquidationDescription(liquidationDescription)
				.withLiquidationDate(liquidationDate)
				.withLiquidationType(liquidationType)
				.build(),
			LiquidationInformation.LiquidationReason
				.builder()
				.withLiquidationCode(liquidationCode)
				.withLiquidationDescription(liquidationDescription)
				.withLiquidationDate(liquidationDate)
				.withLiquidationType(liquidationType)
				.build());

		final var liquidationReason = LiquidationInformation.LiquidationReason
			.builder()
			.withLiquidationCode(liquidationCode)
			.withLiquidationDescription(liquidationDescription)
			.withLiquidationDate(liquidationDate)
			.withLiquidationType(liquidationType)
			.build();

		final var liquidationInformation = LiquidationInformation.builder()
			.withLiquidationReasons(liquidationReasons)
			.withCancelledLiquidation(liquidationReason)
			.build();

		assertThat(liquidationInformation).hasNoNullFieldsOrProperties();
		assertThat(liquidationInformation.getCancelledLiquidation()).isEqualTo(liquidationReason);
		assertThat(liquidationInformation.getLiquidationReasons()).isEqualTo(liquidationReasons);

	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(LiquidationInformation.builder().build()).hasAllNullFieldsOrProperties();
	}

}
