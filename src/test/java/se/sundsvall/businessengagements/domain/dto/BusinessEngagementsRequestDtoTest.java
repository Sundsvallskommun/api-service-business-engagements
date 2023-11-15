package se.sundsvall.businessengagements.domain.dto;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

class BusinessEngagementsRequestDtoTest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(BusinessEngagementsRequestDto.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var personalName = "somePersonalName";
		final var legalId = "someLegalId";
		final var partyId = "somePartyId";
		final var serviceName = "someServiceName";

		final var businessEngagementsRequestDto = BusinessEngagementsRequestDto.builder()
			.withPersonalName(personalName)
			.withLegalId(legalId)
			.withPartyId(partyId)
			.withServiceName(serviceName)
			.build();

		assertThat(businessEngagementsRequestDto).hasNoNullFieldsOrProperties();
		assertThat(businessEngagementsRequestDto.getPersonalName()).isEqualTo(personalName);
		assertThat(businessEngagementsRequestDto.getLegalId()).isEqualTo(legalId);
		assertThat(businessEngagementsRequestDto.getPartyId()).isEqualTo(partyId);
		assertThat(businessEngagementsRequestDto.getServiceName()).isEqualTo(serviceName);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(BusinessEngagementsRequestDto.builder().build()).hasAllNullFieldsOrProperties();
	}

}
