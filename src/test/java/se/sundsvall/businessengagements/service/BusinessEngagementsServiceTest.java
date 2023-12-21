package se.sundsvall.businessengagements.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

import se.sundsvall.businessengagements.TestObjectFactory;
import se.sundsvall.businessengagements.api.model.BusinessEngagementsResponse;
import se.sundsvall.businessengagements.api.model.BusinessInformation;
import se.sundsvall.businessengagements.api.model.Engagement;
import se.sundsvall.businessengagements.domain.dto.BusinessEngagementsRequestDto;
import se.sundsvall.businessengagements.integration.db.EngagementsCacheRepository;
import se.sundsvall.businessengagements.integration.db.entity.EngagementsCacheEntity;
import se.sundsvall.businessengagements.integration.party.PartyClient;
import se.sundsvall.businessengagements.service.mapper.ssbten.EngagemangBegaranRequestMapper;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BusinessEngagementsServiceTest {

	@Mock
	private EngagemangBegaranRequestMapper mockEngagemangBegaranRequestMapper;

	@Mock
	private EngagementsCacheRepository mockRepository;

	@Mock
	private PartyClient mockPartyClient;

	@Mock
	private SsbtenService mockSsbtenService;

	@Mock
	private SsbtguService mockSsbtguService;

	@InjectMocks
	private BusinessEngagementsService service;

	@Test
	void testGetBusinessInformation_shouldReturnInfo() {
		when(mockPartyClient.getOrganizationNumberFromPartyId("abc123")).thenReturn(Optional.of("orgno"));
		when(mockSsbtguService.fetchBusinessInformation(eq("orgno"), anyString())).thenReturn(new BusinessInformation());

		assertThat(service.getBusinessInformation("abc123", "some name")).isNotNull();
		verify(mockPartyClient, times(1)).getOrganizationNumberFromPartyId("abc123");
		verify(mockSsbtguService, times(1)).fetchBusinessInformation("orgno", "some name");
	}

	@Test
	void testGetBusinessInformation_shouldThrowException_whenOrgNoNotFound() {
		when(mockPartyClient.getOrganizationNumberFromPartyId("abc123")).thenReturn(Optional.empty());

		assertThatExceptionOfType(ThrowableProblem.class).isThrownBy(() -> service.getBusinessInformation("abc123", "some name"))
			.withMessage("Couldn't find organizationNumber for partyId: abc123");
	}

	@Test
	void testGetBusinessEngagements_happyPath() {
		when(mockPartyClient.getPartyIdFromOrganizationNumber("5591628136")).thenReturn(Optional.of("uuid1"));
		when(mockSsbtenService.getBusinessEngagements(any(BusinessEngagementsRequestDto.class))).thenReturn(BusinessEngagementsResponse.builder()
			.withEngagements(List.of(Engagement.builder()
				.withOrganizationNumber("5591628136")
				.withOrganizationName("Something IT AB")
				.build()))
			.build());

		final BusinessEngagementsResponse response = service.getBusinessEngagements(TestObjectFactory.createDummyRequestDto());

		verify(mockRepository, times(1)).findByPartyId("abc123");
		verify(mockPartyClient, times(1)).getPersonalNumberFromPartyId(anyString());
		verify(mockSsbtenService, times(1)).getBusinessEngagements(any(BusinessEngagementsRequestDto.class));
		verify(mockRepository, times(1)).save(any(EngagementsCacheEntity.class));

		assertThat(response.getEngagements().stream().anyMatch(engagement -> "5591628136".equals(engagement.getOrganizationNumber()) &&
			"uuid1".equals(engagement.getOrganizationId()) &&
			"Something IT AB".equals(engagement.getOrganizationName()))).isTrue();
	}

	@Test
	void testGetBusinessEngagements_getCitizenMappingThrowsException_shouldThrowException() {

		final var requestDto = TestObjectFactory.createDummyRequestDto();
		when(mockPartyClient.getPersonalNumberFromPartyId(anyString())).thenThrow(Problem.builder().withDetail("Something wrong").build());

		assertThatExceptionOfType(ThrowableProblem.class).isThrownBy(() -> service.getBusinessEngagements(requestDto))
			.withMessage("Something wrong");

		verify(mockPartyClient, times(1)).getPersonalNumberFromPartyId(anyString());
		verify(mockEngagemangBegaranRequestMapper, times(0)).createEngagemangBegaranRequest((any(BusinessEngagementsRequestDto.class)));
	}

	@Test
	void testGetBusinessEngagements_bolagsverketThrowsException_shouldThrowException() {

		final var requestDto = TestObjectFactory.createDummyRequestDto();
		when(mockPartyClient.getPersonalNumberFromPartyId(anyString())).thenReturn("personalNumber");
		when(mockSsbtenService.getBusinessEngagements(any(BusinessEngagementsRequestDto.class))).thenThrow(Problem.builder().withTitle("Something wrong").build());

		assertThatExceptionOfType(ThrowableProblem.class).isThrownBy(() -> service.getBusinessEngagements(requestDto))
			.withMessage("Something wrong");

		verify(mockPartyClient, times(1)).getPersonalNumberFromPartyId(anyString());
		verify(mockSsbtenService, times(1)).getBusinessEngagements(any(BusinessEngagementsRequestDto.class));
	}

	@Test
	void testGetResponseIfCached_shouldReturnValue_whenFound() {
		when(mockRepository.findByPartyId("abc123")).thenReturn(Optional.of(EngagementsCacheEntity.builder()
			.withResponse(new BusinessEngagementsResponse())
			.build()));
		final Optional<BusinessEngagementsResponse> responseIfCached = service.getResponseIfCached("abc123");

		assertThat(responseIfCached).isPresent();
	}

	@Test
	void testGetResponseIfCached_shouldReturnOptionalEmpty_whenNotFound() {
		final String partyId = "abc123";
		when(mockRepository.findByPartyId(anyString())).thenReturn(Optional.empty());
		final Optional<BusinessEngagementsResponse> responseIfCached = service.getResponseIfCached(partyId);

		assertThat(responseIfCached).isNotPresent();
	}

	@Test
	void testFetchAndPopulateGuid() {
		final BusinessEngagementsResponse response = new BusinessEngagementsResponse();
		response.addEngagement(Engagement.builder().withOrganizationNumber("123456").build());
		response.addEngagement(Engagement.builder().withOrganizationNumber("654321").build());
		response.addEngagement(Engagement.builder().withOrganizationNumber("987654").build());

		when(mockPartyClient.getPartyIdFromOrganizationNumber("123456")).thenReturn(Optional.of("uuid1"));
		when(mockPartyClient.getPartyIdFromOrganizationNumber("654321")).thenReturn(Optional.of("uuid2"));
		// Test that we got not uuid
		when(mockPartyClient.getPartyIdFromOrganizationNumber("987654")).thenReturn(Optional.empty());

		service.fetchAndPopulateGuidForOrganizations(response);

		assertThat(response.getEngagements().stream().anyMatch(engagement -> "123456".equalsIgnoreCase(engagement.getOrganizationNumber()) && "uuid1".equals(engagement.getOrganizationId()))).isTrue();
		assertThat(response.getEngagements().stream().anyMatch(engagement -> "654321".equalsIgnoreCase(engagement.getOrganizationNumber()) && "uuid2".equals(engagement.getOrganizationId()))).isTrue();
		assertThat(response.getEngagements().stream().anyMatch(engagement -> "987654".equalsIgnoreCase(engagement.getOrganizationNumber()) && (engagement.getOrganizationId() == null))).isTrue();    // No uuid
		assertThat(response.getStatusDescriptions()).containsValue("Couldn't fetch guid for organization number"); // Make sure we have a status description.
	}

	@Test
	void testHandleNewCacheEntity_shouldStore_whenNoFaultyContent() {
		final BusinessEngagementsResponse response = BusinessEngagementsResponse.builder().build();

		service.handleNewCacheEntity(response, "partyId");

		verify(mockRepository, times(1)).deleteByPartyId("partyId");
		verify(mockRepository, times(1)).save(any(EngagementsCacheEntity.class));

	}

	@Test
	void testHandleNewCacheEntity_shouldNotStore_whenFaultyContent() {
		final BusinessEngagementsResponse response = BusinessEngagementsResponse.builder()
			.withStatusDescriptions(Map.of("NOK", "Something wrong"))
			.build();
		service.handleNewCacheEntity(response, "partyId");

		verify(mockRepository, times(0)).deleteByPartyId(anyString());
		verify(mockRepository, times(0)).save(any(EngagementsCacheEntity.class));
	}

}
