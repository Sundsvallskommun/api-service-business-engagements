package se.sundsvall.businessengagements.service;

import static org.zalando.problem.Status.NOT_FOUND;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Problem;

import se.sundsvall.businessengagements.api.model.BusinessEngagementsResponse;
import se.sundsvall.businessengagements.api.model.BusinessInformation;
import se.sundsvall.businessengagements.domain.dto.BusinessEngagementsRequestDto;
import se.sundsvall.businessengagements.integration.db.EngagementsCacheRepository;
import se.sundsvall.businessengagements.integration.db.EntityMapper;
import se.sundsvall.businessengagements.integration.db.entity.EngagementsCacheEntity;
import se.sundsvall.businessengagements.integration.party.PartyClient;

@Service
@EnableTransactionManagement
public class BusinessEngagementsService {

	public static final String SUNDSVALLS_KOMMUN = "Sundsvalls Kommun";

	public static final String SUNDSVALL_MUNICIPALITY_ORGANIZATION_NUMBER = "2120002411";

	public static final String SERVICE_NAME = "BUSENGBV";  //This is communicated between Bolagsverket and Sundsvalls kommun, must be set in each request.

	private static final Logger LOG = LoggerFactory.getLogger(BusinessEngagementsService.class);

	private final EngagementsCacheRepository engagementsCacheRepository;

	private final PartyClient partyClient;

	private final SsbtenService ssbtenService;

	private final SsbtguService ssbtguService;

	public BusinessEngagementsService(final EngagementsCacheRepository engagementsCacheRepository,
		final PartyClient partyClient, SsbtenService ssbtenService, SsbtguService ssbtguService) {
		this.engagementsCacheRepository = engagementsCacheRepository;
		this.partyClient = partyClient;
		this.ssbtenService = ssbtenService;
		this.ssbtguService = ssbtguService;
	}

	public BusinessInformation getBusinessInformation(String partyId, String organizationName) {
		return partyClient.getOrganizationNumberFromPartyId(partyId)
			.map(orgNo -> ssbtguService.fetchBusinessInformation(orgNo, organizationName))
			.orElseThrow(() -> Problem.builder()
				.withTitle("Couldn't find organizationNumber for partyId: " + partyId)
				.withStatus(NOT_FOUND)
				.build());
	}

	/**
	 * Handles fetching of engagements, storing to database cache and updating the cache.
	 *
	 * @param requestDto The request DTO
	 * @return The response DTO
	 */
	@Transactional
	public BusinessEngagementsResponse getBusinessEngagements(BusinessEngagementsRequestDto requestDto) {

		//First check if we have a cached entity for the given partyId
		final Optional<BusinessEngagementsResponse> optionalCachedResponse = getResponseIfCached(requestDto.getPartyId());

		if (optionalCachedResponse.isPresent()) {
			return optionalCachedResponse.get();
		} else {

			requestDto.setLegalId(partyClient.getPersonalNumberFromPartyId(requestDto.getPartyId()));

			BusinessEngagementsResponse engagements = ssbtenService.getBusinessEngagements(requestDto);

			if (engagements.getEngagements() != null && !engagements.getEngagements().isEmpty()) {
				fetchAndPopulateGuidForOrganizations(engagements);

				//Here we have an entity that (maybe) needs caching
				handleNewCacheEntity(engagements, requestDto.getPartyId());
			}

			return engagements;
		}
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

	/**
	 * Fetches a cached response
	 *
	 * @param partyId The partyId to fetch for
	 * @return An optional response, empty if no cached entity was found.
	 */
	Optional<BusinessEngagementsResponse> getResponseIfCached(String partyId) {
		final Optional<EngagementsCacheEntity> possibleCachedEntity = engagementsCacheRepository.findByPartyId(partyId);

		Optional<BusinessEngagementsResponse> possibleResponse = Optional.empty();

		if (possibleCachedEntity.isPresent()) {
			final EngagementsCacheEntity entity = possibleCachedEntity.get();
			possibleResponse = Optional.of(entity.getResponse());
		}

		return possibleResponse;
	}

	void handleNewCacheEntity(BusinessEngagementsResponse response, String partyId) {

		//We don't want to persist anything with errors.
		if (response.getStatusDescriptions() == null || response.getStatusDescriptions().isEmpty()) {
			//First we need to remove any possible old entity.
			int entitiesDeleted = engagementsCacheRepository.deleteByPartyId(partyId);

			if (entitiesDeleted > 0) {    //Only print if we actually remove anything
				LOG.info("Removed cached entity");
			}

			engagementsCacheRepository.save(EntityMapper.mapResponseToEntity(response, partyId));
			LOG.info("Persisted a new entity");
		} else {
			LOG.info("Not persisting response entity since it has missing information.");
		}
	}

}
