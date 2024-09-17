package se.sundsvall.businessengagements.integration.party;

import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import generated.se.sundsvall.party.PartyType;

@FeignClient(
	name = "party",
	url = "${integration.party.url}",
	configuration = PartyConfiguration.class,
	dismiss404 = true)
public interface PartyIntegration {

	String LEGAL_ID_CACHE = "legalIds";

	String PARTY_ID_CACHE = "partyIds";

	/**
	 * Get legalID by partyId
	 *
	 * @param municipalityId the municipalityId
	 * @param partyType "ENTERPRISE" or "PRIVATE"
	 * @param partyId uuid of the person or organization
	 * @return legalId of the person or organization, Optional.empty() if not found.
	 */
	@Cacheable(LEGAL_ID_CACHE)
	@GetMapping(path = "/{municipalityId}/{type}/{partyId}/legalId", produces = {TEXT_PLAIN_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
	Optional<String> getLegalId(@PathVariable("municipalityId") String municipalityId, @PathVariable("type") PartyType partyType, @PathVariable("partyId") String partyId);

	/**
	 * Get partyId for a legalId

	 * @param municipalityId the municipalityId
	 * @param partyType "ENTERPRISE" or "PRIVATE"
	 * @param legalId legalid of the person or organization
	 * @return partyId of the person or organization, Optional.empty() if not found.
	 */
	@Cacheable(PARTY_ID_CACHE)
	@GetMapping(path = "/{municipalityId}/{type}/{legalId}/partyId", produces = {TEXT_PLAIN_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
	Optional<String> getPartyId(@PathVariable("municipalityId") String municipalityId, @PathVariable("type") PartyType partyType, @PathVariable("legalId") String legalId);

}
