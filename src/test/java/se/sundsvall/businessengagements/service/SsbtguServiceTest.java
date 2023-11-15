package se.sundsvall.businessengagements.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zalando.problem.ThrowableProblem;

import se.sundsvall.businessengagements.api.model.BusinessInformation;
import se.sundsvall.businessengagements.integration.bolagsverket.ssbtgu.SsbtguIntegration;
import se.sundsvall.businessengagements.service.mapper.ssbtgu.SsbtguRequestMapper;
import se.sundsvall.businessengagements.service.mapper.ssbtgu.SsbtguResponseMapper;

import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterBegaran;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterBegaranMetadata;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterSvar;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterSvarMetadata;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
class SsbtguServiceTest {

	@Mock
	private SsbtguRequestMapper mockSsbtguRequestMapper;

	@Mock
	private SsbtguResponseMapper mockSsbtguResponseMapper;

	@Mock
	private SsbtguIntegration mockSsbtguIntegration;

	private SsbtguService service;

	@BeforeEach
	public void setup() {
		this.service = new SsbtguService(mockSsbtguRequestMapper, mockSsbtguResponseMapper, mockSsbtguIntegration, false);
	}

	@Test
	void fetchBusinessInformation(final SoftAssertions softly) {
		GrundlaggandeUppgifterBegaran mockGrundlaggandeUppgifterBegaran = Mockito.mock(GrundlaggandeUppgifterBegaran.class);
		when(mockSsbtguRequestMapper.createGrundlaggandeUppgifterBegaran(anyString(), anyString())).thenReturn(mockGrundlaggandeUppgifterBegaran);

		GrundlaggandeUppgifterSvar mockGrundlaggandeUppgifterSvar = Mockito.mock(GrundlaggandeUppgifterSvar.class);
		when(mockSsbtguIntegration.callBolagsverket(mockGrundlaggandeUppgifterBegaran)).thenReturn(mockGrundlaggandeUppgifterSvar);

		GrundlaggandeUppgifterBegaranMetadata mockGrundlaggandeUppgifterBegaranMetadata = Mockito.mock(GrundlaggandeUppgifterBegaranMetadata.class);
		GrundlaggandeUppgifterSvarMetadata mockGrundlaggandeUppgifterSvarMetadata = Mockito.mock(GrundlaggandeUppgifterSvarMetadata.class);
		when(mockGrundlaggandeUppgifterBegaran.getGrundlaggandeUppgifterBegaranMetadata()).thenReturn(mockGrundlaggandeUppgifterBegaranMetadata);
		when(mockGrundlaggandeUppgifterSvar.getGrundlaggandeUppgifterSvarMetadata()).thenReturn(mockGrundlaggandeUppgifterSvarMetadata);

		when(mockGrundlaggandeUppgifterBegaranMetadata.getTransaktionId()).thenReturn("abc123");
		when(mockGrundlaggandeUppgifterSvarMetadata.getTransaktionId()).thenReturn("abc123");
		when(mockSsbtguResponseMapper.mapGrundlaggandeUppgifterSvar(any(GrundlaggandeUppgifterSvar.class))).thenReturn(new BusinessInformation());

		BusinessInformation businessInformation = service.fetchBusinessInformation("orgNumber", "orgName");

		softly.assertThat(businessInformation).isNotNull();
		verify(mockSsbtguRequestMapper, times(1)).createGrundlaggandeUppgifterBegaran("orgNumber", "orgName");
		verify(mockSsbtguIntegration, times(1)).callBolagsverket(any(GrundlaggandeUppgifterBegaran.class));
		verify(mockSsbtguResponseMapper, times(1)).mapGrundlaggandeUppgifterSvar(any(GrundlaggandeUppgifterSvar.class));
	}

	@Test
	void testNonMatchingTransactionIds_shouldThrowException() {
		//Override with true to test validation
		this.service = new SsbtguService(mockSsbtguRequestMapper, mockSsbtguResponseMapper, mockSsbtguIntegration, true);
		assertThatExceptionOfType(ThrowableProblem.class).isThrownBy(() -> service.validateResponse("uuid1", "notmatching"))
			.withMessage("Response from bolagsverket was inconsistent.");
	}

}
