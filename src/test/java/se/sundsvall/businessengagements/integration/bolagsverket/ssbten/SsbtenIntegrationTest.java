package se.sundsvall.businessengagements.integration.bolagsverket.ssbten;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.zalando.problem.ThrowableProblem;

import se.bolagsverket.schema.ssbten.engagemang.EngagemangBegaran;
import se.bolagsverket.schema.ssbten.engagemang.EngagemangSvar;

@ExtendWith({
	MockitoExtension.class, SoftAssertionsExtension.class
})
class SsbtenIntegrationTest {

	@Mock
	private WebServiceTemplate mockWebserviceTemplate;

	private SsbtenIntegration integration;

	@BeforeEach
	public void setup() {
		integration = new SsbtenIntegration(mockWebserviceTemplate);
	}

	@Test
	void testCallBolagsverket_shouldReturnOkAnswer(final SoftAssertions softly) {
		when(mockWebserviceTemplate.marshalSendAndReceive(any(EngagemangBegaran.class))).thenReturn(new EngagemangSvar());

		final EngagemangSvar response = integration.callBolagsverket(new EngagemangBegaran());

		softly.assertThat(response).isNotNull();
	}

	@Test
	void testBolagsverket_throwsServiceFelException_shouldThrowException() {

		var request = new EngagemangBegaran();
		when(mockWebserviceTemplate.marshalSendAndReceive(any(EngagemangBegaran.class))).thenThrow(new RuntimeException());

		assertThatExceptionOfType(ThrowableProblem.class).isThrownBy(() -> integration.callBolagsverket(request))
			.withMessage("Error while getting digital engagements from bolagsverket/ssbten");
	}

}
