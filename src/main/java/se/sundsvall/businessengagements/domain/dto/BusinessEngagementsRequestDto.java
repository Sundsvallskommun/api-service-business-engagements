package se.sundsvall.businessengagements.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Convenience-DTO for sending request info around.
 */

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class BusinessEngagementsRequestDto {

	private String personalName;

	private String legalId; //Could be both a "sole trader" or organization

	private String partyId;

	private String serviceName;

}
