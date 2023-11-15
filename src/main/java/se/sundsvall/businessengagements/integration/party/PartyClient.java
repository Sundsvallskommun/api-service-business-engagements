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

	public PartyClient(PartyIntegration partyIntegration) {
		this.partyIntegration = partyIntegration;
	}

	/**
	 * Get legalIds for the partyId sent in.
	 * Only for private persons.
	 *
	 * @param partyId
	 * @return a legalIds
	 */
	public String getPersonalNumberFromPartyId(String partyId) {
		return partyIntegration.getLegalId(PartyType.PRIVATE, partyId)
			.orElseThrow(() -> Problem.builder()
				.withTitle("Couldn't find legalId for partyId: " + partyId)
				.withStatus(NOT_FOUND)
				.build());
	}

	/**
	 * Get partyId from organizationNumber
	 *
	 * @param organizationNumber
	 * @return Optional partyId
	 */
	public Optional<String> getPartyIdFromOrganizationNumber(String organizationNumber) {
		return partyIntegration.getPartyId(PartyType.ENTERPRISE, organizationNumber);
	}

	/**
	 * GetOrganizationNumber from partyId
	 *
	 * @param partyId
	 * @return Optional organizationNumber
	 */
	public Optional<String> getOrganizationNumberFromPartyId(String partyId) {
		return partyIntegration.getLegalId(PartyType.ENTERPRISE, partyId);
	}

}
