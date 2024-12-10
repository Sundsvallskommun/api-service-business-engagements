package se.sundsvall.businessengagements.integration.party;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import generated.se.sundsvall.party.PartyType;
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

@ExtendWith({
	MockitoExtension.class, SoftAssertionsExtension.class
})
class PartyClientTest {

	@Mock
	private PartyIntegration mockPartyIntegration;

	@InjectMocks
	private PartyClient partyClient;

	@Test
	void testGetPersonalNumberFromPartyId(final SoftAssertions softly) {
		when(mockPartyIntegration.getLegalId("2281", PartyType.PRIVATE, "abc123")).thenReturn(Optional.of("198001011234"));
		softly.assertThat(partyClient.getPersonalNumberFromPartyId("abc123", "2281")).isEqualTo("198001011234");
		verify(mockPartyIntegration, Mockito.times(1)).getLegalId("2281", PartyType.PRIVATE, "abc123");
	}

	@Test
	void testGetPersonalNumberFromPartyId_whenNotFound_shouldThrowException() {
		when(mockPartyIntegration.getLegalId("2281", PartyType.PRIVATE, "abc123")).thenReturn(Optional.empty());
		assertThatExceptionOfType(ThrowableProblem.class).isThrownBy(() -> partyClient.getPersonalNumberFromPartyId("abc123", "2281"))
			.withMessage("Couldn't find legalId for partyId: abc123");
		verify(mockPartyIntegration, Mockito.times(1)).getLegalId("2281", PartyType.PRIVATE, "abc123");
	}

	@Test
	void testGetPartyIdFromOrganizationNumber(final SoftAssertions softly) {
		when(mockPartyIntegration.getPartyId("2281", PartyType.ENTERPRISE, "organizationNumber")).thenReturn(Optional.of("abc123"));
		final Optional<String> organizationNumber = partyClient.getPartyIdFromOrganizationNumber("organizationNumber", "2281");
		softly.assertThat(organizationNumber).isPresent();
		softly.assertThat(organizationNumber.get()).isEqualTo("abc123");
		verify(mockPartyIntegration, times(1)).getPartyId("2281", PartyType.ENTERPRISE, "organizationNumber");
	}

	@Test
	void testGetOrganizationNumberFromPartyId(final SoftAssertions softly) {
		when(mockPartyIntegration.getLegalId("2281", PartyType.ENTERPRISE, "abc123")).thenReturn(Optional.of("5591621234"));
		final Optional<String> organizationNumber = partyClient.getOrganizationNumberFromPartyId("abc123", "2281");
		softly.assertThat(organizationNumber).isPresent();
		softly.assertThat(organizationNumber.get()).isEqualTo("5591621234");
		verify(mockPartyIntegration, times(1)).getLegalId("2281", PartyType.ENTERPRISE, "abc123");
	}

}
