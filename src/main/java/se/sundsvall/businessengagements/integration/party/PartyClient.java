package se.sundsvall.businessengagements.integration.party;

import static org.zalando.problem.Status.NOT_FOUND;

import generated.se.sundsvall.party.PartyType;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;

/**
 * Class to interact with the PartyIntegration.
 */
@Component
public class PartyClient {

	private static final String LEGAL_ID_CACHE = "legalIds";
	private static final String PARTY_ID_CACHE = "partyIds";

	private final PartyIntegration partyIntegration;

	public PartyClient(final PartyIntegration partyIntegration) {
		this.partyIntegration = partyIntegration;
	}

	/**
	 * Get legalIds for the partyId sent in.
	 * Only for private persons.
	 *
	 * @param  municipalityId the municipalityId
	 * @param  partyId        the partyId
	 * @return                a legalIds
	 */
	@Cacheable(LEGAL_ID_CACHE)
	public String getPersonalNumberFromPartyId(final String partyId, final String municipalityId) {
		return partyIntegration.getLegalId(municipalityId, PartyType.PRIVATE, partyId)
			.orElseThrow(() -> Problem.builder()
				.withTitle("Couldn't find legalId for partyId: " + partyId)
				.withStatus(NOT_FOUND)
				.build());
	}

	/**
	 * Get partyId from organizationNumber
	 *
	 * @param  municipalityId     the municipalityId
	 * @param  organizationNumber the organizationNumber
	 * @return                    Optional partyId
	 */
	@Cacheable(PARTY_ID_CACHE)
	public Optional<String> getPartyIdFromOrganizationNumber(final String organizationNumber, final String municipalityId) {
		return partyIntegration.getPartyId(municipalityId, PartyType.ENTERPRISE, organizationNumber);
	}

	/**
	 * GetOrganizationNumber from partyId
	 *
	 * @param  municipalityId the municipalityId
	 * @param  partyId        the partyId
	 * @return                Optional organizationNumber
	 */
	@Cacheable(LEGAL_ID_CACHE)
	public Optional<String> getOrganizationNumberFromPartyId(final String partyId, final String municipalityId) {
		return partyIntegration.getLegalId(municipalityId, PartyType.ENTERPRISE, partyId);
	}

}
