package se.sundsvall.businessengagements.api;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;
import org.zalando.problem.violations.ConstraintViolationProblem;
import se.sundsvall.businessengagements.api.model.BusinessEngagementsResponse;
import se.sundsvall.businessengagements.api.model.BusinessEngagementsResponse.Status;
import se.sundsvall.businessengagements.api.model.BusinessInformation;
import se.sundsvall.businessengagements.domain.dto.BusinessEngagementsRequestDto;
import se.sundsvall.businessengagements.service.BusinessEngagementsService;
import se.sundsvall.dept44.common.validators.annotation.ValidMunicipalityId;
import se.sundsvall.dept44.common.validators.annotation.ValidUuid;

@RestController
@RequestMapping(value = "/{municipalityId}")
@ApiResponses(value = {
	@ApiResponse(responseCode = "204", description = "No Content"),
	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class))),
	@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = {
		Problem.class, ConstraintViolationProblem.class
	}))),
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
})
@Tag(name = "Business Engagements", description = "Show a persons business engagements and company information.")
@Validated
public class BusinessEngagementsResource {

	private final BusinessEngagementsService businessEngagementsService;

	public BusinessEngagementsResource(final BusinessEngagementsService businessEngagementsService) {
		this.businessEngagementsService = businessEngagementsService;
	}

	@Operation(
		summary = "Get business engagements",
		description = "Get a list of business engagements for a person",
		responses = {
			@ApiResponse(responseCode = "200", description = "Successful Operation", useReturnTypeSchema = true)
		})
	@GetMapping(value = "/engagements/{partyId}", produces = APPLICATION_JSON)
	public ResponseEntity<BusinessEngagementsResponse> getEngagements(
		@Parameter(name = "municipalityId", description = "Municipality id", example = "2281") @ValidMunicipalityId @PathVariable final String municipalityId,
		@ValidUuid @PathVariable("partyId") @Schema(description = "Unique identifier for a person", example = "6a5c3d04-412d-11ec-973a-0242ac130003") final String partyId,
		@NotBlank @RequestParam("personalName") @Schema(description = "The first and surname of the person") final String personalName,
		@NotBlank @RequestParam("serviceName") @Schema(description = "Name of the system initiating the request", example = "Mina Sidor") final String serviceName) {

		final var requestDto = new BusinessEngagementsRequestDto(personalName, null, partyId, serviceName);   // We don't know personalNumber yet
		final var response = businessEngagementsService.getBusinessEngagements(requestDto, municipalityId);

		// A little janky but if there's a statusDescription it means something went wrong and we should indicate this with a
		// NOK as status.
		if (response.getStatusDescriptions() != null) {
			response.setStatus(Status.NOK);
		} else {
			response.setStatus(Status.OK);
		}

		if (response.getEngagements() == null && response.getStatusDescriptions() == null) {
			// In this case we have an empty answer and nothing has gone wrong.
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Get business information",
		description = "Get information about a company",
		responses = {
			@ApiResponse(responseCode = "200", description = "Successful Operation", useReturnTypeSchema = true)
		})
	@GetMapping(value = "/information/{partyId}", produces = APPLICATION_JSON)
	public ResponseEntity<BusinessInformation> getBusinessInformation(
		@Parameter(name = "municipalityId", description = "Municipality id", example = "2281") @ValidMunicipalityId @PathVariable final String municipalityId,
		@ValidUuid @PathVariable("partyId") @Schema(description = "Unique identifier for an organization number", example = "fb2f0290-3820-11ed-a261-0242ac120002") final String partyId,
		@RequestParam(value = "organizationName", required = false) @Schema(description = "Name of the organization") final String organizationName) {

		final BusinessInformation businessInformation = businessEngagementsService.getBusinessInformation(partyId, organizationName, municipalityId);
		return ResponseEntity.ok(businessInformation);
	}

}
