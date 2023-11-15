package se.sundsvall.businessengagements.integration.bolagsverket.ssbtgu;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
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

import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterBegaran;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterSvar;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
class SsbtguIntegrationTest {

	@Mock
	private WebServiceTemplate mockWebserviceTemplate;

	private SsbtguIntegration integration;

	@BeforeEach
	public void setup() {
		integration = new SsbtguIntegration(mockWebserviceTemplate);
	}

	@Test
	void testCallBolagsverket_shouldReturnOkAnswer(final SoftAssertions softly) {
		when(mockWebserviceTemplate.marshalSendAndReceive(any(GrundlaggandeUppgifterBegaran.class))).thenReturn(new GrundlaggandeUppgifterSvar());

		final GrundlaggandeUppgifterSvar response = integration.callBolagsverket(new GrundlaggandeUppgifterBegaran());

		softly.assertThat(response).isNotNull();
	}

	@Test
	void testBolagsverket_throwsServiceFelException_shouldThrowException() {
		var request = new GrundlaggandeUppgifterBegaran();
		when(mockWebserviceTemplate.marshalSendAndReceive(any(GrundlaggandeUppgifterBegaran.class))).thenThrow(new RuntimeException());

		assertThatExceptionOfType(ThrowableProblem.class).isThrownBy(() -> integration.callBolagsverket(request))
			.withMessage("Error while getting business information from bolagsverket/ssbtgu");
	}

}
