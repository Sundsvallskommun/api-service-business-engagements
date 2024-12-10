package se.sundsvall.businessengagements.api;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import se.sundsvall.businessengagements.api.model.BusinessEngagementsResponse;
import se.sundsvall.businessengagements.api.model.BusinessInformation;
import se.sundsvall.businessengagements.domain.dto.BusinessEngagementsRequestDto;
import se.sundsvall.businessengagements.service.BusinessEngagementsService;

@ExtendWith({
	MockitoExtension.class, SoftAssertionsExtension.class
})
class BusinessEngagementsResourceTest {

	@Mock
	private BusinessEngagementsService mockService;

	private BusinessEngagementsResource resource;

	@BeforeEach
	public void setup() {
		resource = new BusinessEngagementsResource(mockService);
	}

	@Test
	void testGetEngagements_invoicesReturned_shouldReturnOkResponse(final SoftAssertions softly) {
		final BusinessEngagementsResponse response = new BusinessEngagementsResponse();
		response.addEngagement("orgname", "orgnumber");

		when(mockService.getBusinessEngagements(any(BusinessEngagementsRequestDto.class), anyString())).thenReturn(response);

		final ResponseEntity<BusinessEngagementsResponse> engagements = resource.getEngagements("2281", "abc123", "John Doe", "serviceName");
		final BusinessEngagementsResponse businessEngagementsResponse = engagements.getBody();

		softly.assertThat(engagements.getStatusCode()).isEqualTo(OK);
		softly.assertThat(businessEngagementsResponse.getStatus()).isEqualTo(BusinessEngagementsResponse.Status.OK);
		verify(mockService, times(1)).getBusinessEngagements(any(BusinessEngagementsRequestDto.class), anyString());
	}

	@Test
	void testGetEngagements_noInvoices_shouldReturnNoContent(final SoftAssertions softly) {
		when(mockService.getBusinessEngagements(any(BusinessEngagementsRequestDto.class), anyString())).thenReturn(new BusinessEngagementsResponse());

		final ResponseEntity<BusinessEngagementsResponse> engagements = resource.getEngagements("2281", "abc123", "John Doe", "serviceName");

		softly.assertThat(engagements.getStatusCode()).isEqualTo(NO_CONTENT);
		verify(mockService, times(1)).getBusinessEngagements(any(BusinessEngagementsRequestDto.class), anyString());
	}

	@Test
	void testGetEngagements_populatedStatusDescription_shouldReturnOkResponse(final SoftAssertions softly) {
		final BusinessEngagementsResponse response = new BusinessEngagementsResponse();
		response.addEngagement("orgname", "orgnumber");
		response.addStatusDescription("type", "description");

		when(mockService.getBusinessEngagements(any(BusinessEngagementsRequestDto.class), anyString())).thenReturn(response);

		final ResponseEntity<BusinessEngagementsResponse> engagements = resource.getEngagements("2281", "abc123", "John Doe", "serviceName");

		softly.assertThat(engagements.getStatusCode()).isEqualTo(OK);
		verify(mockService, times(1)).getBusinessEngagements(any(BusinessEngagementsRequestDto.class), anyString());
	}

	@Test
	void testGetBusinessInformation_shouldReturnOkResponse(final SoftAssertions softly) {
		when(mockService.getBusinessInformation(anyString(), anyString(), anyString())).thenReturn(new BusinessInformation());

		softly.assertThat(resource.getBusinessInformation("2281", "uuid", "name")).isNotNull();
		verify(mockService, times(1)).getBusinessInformation(anyString(), anyString(), anyString());

	}

}
