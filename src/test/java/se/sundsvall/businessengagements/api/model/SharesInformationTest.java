package se.sundsvall.businessengagements.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

class SharesInformationTest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(SharesInformation.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var numberOfShares = BigInteger.valueOf(100);
		final var shareCapital = BigDecimal.valueOf(100.69);
		final var shareCurrency = "SEK";

		final var shareTypes = List.of(
			SharesInformation.ShareType
				.builder()
				.withLabel("A")
				.withNumberOfShares(BigInteger.valueOf(50))
				.withVoteValue("1/10")
				.build(),
			SharesInformation.ShareType
				.builder()
				.withLabel("B")
				.withNumberOfShares(BigInteger.valueOf(50))
				.withVoteValue("1/10")
				.build());

		final var sharesInformation = SharesInformation.builder()
			.withNumberOfShares(numberOfShares)
			.withShareTypes(shareTypes)
			.withShareCurrency(shareCurrency)
			.withShareCapital(shareCapital)
			.build();

		assertThat(sharesInformation).hasNoNullFieldsOrProperties();
		assertThat(sharesInformation.getNumberOfShares()).isEqualTo(numberOfShares);
		assertThat(sharesInformation.getShareCapital()).isEqualTo(shareCapital);
		assertThat(sharesInformation.getShareCurrency()).isEqualTo(shareCurrency);

	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(SharesInformation.builder().build()).hasAllNullFieldsOrProperties();
	}

}
