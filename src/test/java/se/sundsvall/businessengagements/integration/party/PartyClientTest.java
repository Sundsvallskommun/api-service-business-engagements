package se.sundsvall.businessengagements.integration.party;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zalando.problem.ThrowableProblem;

import generated.se.sundsvall.party.PartyType;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
class PartyClientTest {

	@Mock
	private PartyIntegration mockPartyIntegration;

	@InjectMocks
	private PartyClient partyClient;

	@Test
	void testGetPersonalNumberFromPartyId(final SoftAssertions softly) {
		when(mockPartyIntegration.getLegalId(PartyType.PRIVATE, "abc123")).thenReturn(Optional.of("198001011234"));
		softly.assertThat(partyClient.getPersonalNumberFromPartyId("abc123")).isEqualTo("198001011234");
		verify(mockPartyIntegration, Mockito.times(1)).getLegalId(PartyType.PRIVATE, "abc123");
	}

	@Test
	void testGetPersonalNumberFromPartyId_whenNotFound_shouldThrowException() {
		when(mockPartyIntegration.getLegalId(PartyType.PRIVATE, "abc123")).thenReturn(Optional.empty());
		assertThatExceptionOfType(ThrowableProblem.class).isThrownBy(() ->
				partyClient.getPersonalNumberFromPartyId("abc123"))
			.withMessage("Couldn't find legalId for partyId: abc123");
		verify(mockPartyIntegration, Mockito.times(1)).getLegalId(PartyType.PRIVATE, "abc123");
	}

	@Test
	void testGetPartyIdFromOrganizationNumber(final SoftAssertions softly) {
		when(mockPartyIntegration.getPartyId(PartyType.ENTERPRISE, "organizationNumber")).thenReturn(Optional.of("abc123"));
		Optional<String> organizationNumber = partyClient.getPartyIdFromOrganizationNumber("organizationNumber");
		softly.assertThat(organizationNumber).isPresent();
		softly.assertThat(organizationNumber.get()).isEqualTo("abc123");
		verify(mockPartyIntegration, times(1)).getPartyId(PartyType.ENTERPRISE, "organizationNumber");
	}

	@Test
	void testGetOrganizationNumberFromPartyId(final SoftAssertions softly) {
		when(mockPartyIntegration.getLegalId(PartyType.ENTERPRISE, "abc123")).thenReturn(Optional.of("5591621234"));
		Optional<String> organizationNumber = partyClient.getOrganizationNumberFromPartyId("abc123");
		softly.assertThat(organizationNumber).isPresent();
		softly.assertThat(organizationNumber.get()).isEqualTo("5591621234");
		verify(mockPartyIntegration, times(1)).getLegalId(PartyType.ENTERPRISE, "abc123");
	}

}
