package se.sundsvall.businessengagements.integration.party;

import static org.zalando.problem.Status.NOT_FOUND;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;

import generated.se.sundsvall.party.PartyType;

/**
 * Class to interact with the PartyIntegration.
 */
@Component
public class PartyClient {

	private final PartyIntegration partyIntegration;

	public PartyClient(final PartyIntegration partyIntegration) {
		this.partyIntegration = partyIntegration;
	}

	/**
	 * Get legalIds for the partyId sent in.
	 * Only for private persons.
	 *
	 * @param municipalityId the municipalityId
	 * @param partyId the partyId
	 * @return a legalIds
	 */
	public String getPersonalNumberFromPartyId(final String partyId, final String municipalityId) {
		return partyIntegration.getLegalId(municipalityId,PartyType.PRIVATE, partyId)
			.orElseThrow(() -> Problem.builder()
				.withTitle("Couldn't find legalId for partyId: " + partyId)
				.withStatus(NOT_FOUND)
				.build());
	}

	/**
	 * Get partyId from organizationNumber
	 *
	 * @param municipalityId the municipalityId
	 * @param organizationNumber the organizationNumber
	 * @return Optional partyId
	 */
	public Optional<String> getPartyIdFromOrganizationNumber(final String organizationNumber, final String municipalityId) {
		return partyIntegration.getPartyId(municipalityId,PartyType.ENTERPRISE, organizationNumber);
	}

	/**
	 * GetOrganizationNumber from partyId
	 *
	 * @param municipalityId the municipalityId
	 * @param partyId the partyId
	 * @return Optional organizationNumber
	 */
	public Optional<String> getOrganizationNumberFromPartyId(final String partyId, final String municipalityId) {
		return partyIntegration.getLegalId(municipalityId,PartyType.ENTERPRISE, partyId);
	}

}
