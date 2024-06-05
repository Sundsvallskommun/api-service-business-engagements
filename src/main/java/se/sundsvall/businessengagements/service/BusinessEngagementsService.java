package se.sundsvall.businessengagements.service;

import static org.zalando.problem.Status.NOT_FOUND;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;

import se.sundsvall.businessengagements.api.model.BusinessEngagementsResponse;
import se.sundsvall.businessengagements.api.model.BusinessInformation;
import se.sundsvall.businessengagements.domain.dto.BusinessEngagementsRequestDto;
import se.sundsvall.businessengagements.integration.party.PartyClient;

@Service
public class BusinessEngagementsService {

	public static final String SUNDSVALLS_KOMMUN = "Sundsvalls Kommun";

	public static final String SUNDSVALL_MUNICIPALITY_ORGANIZATION_NUMBER = "2120002411";

	public static final String SERVICE_NAME = "BUSENGBV";  //This is communicated between Bolagsverket and Sundsvalls kommun, must be set in each request.

	public static final String BUSINESS_INFORMATION_CACHE = "businessInformation";
	public static final String BUSINESS_ENGAGEMENTS_CACHE = "businessEngagements";

	private static final Logger LOG = LoggerFactory.getLogger(BusinessEngagementsService.class);
	
	private final PartyClient partyClient;

	private final SsbtenService ssbtenService;

	private final SsbtguService ssbtguService;

	public BusinessEngagementsService(final PartyClient partyClient, SsbtenService ssbtenService, SsbtguService ssbtguService) {
		this.partyClient = partyClient;
		this.ssbtenService = ssbtenService;
		this.ssbtguService = ssbtguService;
	}

	@Cacheable(value = BUSINESS_INFORMATION_CACHE)
	public BusinessInformation getBusinessInformation(String partyId, String organizationName) {
		return partyClient.getOrganizationNumberFromPartyId(partyId)
			.map(orgNo -> ssbtguService.fetchBusinessInformation(orgNo, organizationName))
			.orElseThrow(() -> Problem.builder()
				.withTitle("Couldn't fetch business information")
				.withDetail("Couldn't find organizationNumber for partyId: " + partyId)
				.withStatus(NOT_FOUND)
				.build());
	}

	/**
	 * Handles fetching of engagements, storing to database cache and updating the cache.
	 *
	 * @param requestDto The request DTO
	 * @return The response DTO
	 */
	@Cacheable(value = BUSINESS_ENGAGEMENTS_CACHE, key = "#requestDto.partyId")
	public BusinessEngagementsResponse getBusinessEngagements(BusinessEngagementsRequestDto requestDto) {

		requestDto.setLegalId(partyClient.getPersonalNumberFromPartyId(requestDto.getPartyId()));

		var engagements = ssbtenService.getBusinessEngagements(requestDto);

		if (engagements.getEngagements() != null && !engagements.getEngagements().isEmpty()) {
			fetchAndPopulateGuidForOrganizations(engagements);
		}

		return engagements;
	}

	void fetchAndPopulateGuidForOrganizations(BusinessEngagementsResponse businessEngagementsResponse) {
		LOG.info("Starting to fetch guid/uuid for all organizations.");

		businessEngagementsResponse.getEngagements()
			.forEach(engagement -> {
				Optional<String> guidForOrganization = Optional.empty();

				try {
					guidForOrganization = partyClient.getPartyIdFromOrganizationNumber(engagement.getOrganizationNumber());
				} catch (Exception e) {
					LOG.warn("Couldn't fetch guid for orgno: {}", engagement.getOrganizationNumber());
					//Don't fail since the only thing we couldn't get is the partyId.
				}

				if (guidForOrganization.isPresent()) {
					//If we have a guid, set it.
					engagement.setOrganizationId(guidForOrganization.get());
				} else {
					//If no guid, indicate this with a new status description for each.
					businessEngagementsResponse.addStatusDescription(engagement.getOrganizationNumber(), "Couldn't fetch guid for organization number");
				}
			});
	}
}
