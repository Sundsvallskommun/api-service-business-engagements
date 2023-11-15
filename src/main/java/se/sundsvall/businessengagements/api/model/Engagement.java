package se.sundsvall.businessengagements.api.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a persons business engagement.")
public class Engagement implements Serializable {

	@Schema(description = "Name of the organization", example = "Styrbjörns båtar")
	private String organizationName;

	@Schema(description = "Organization number, may also be personal number in case of enskild firma", example = "2021005448")
	private String organizationNumber;

	@Schema(description = "Unique id for the organization (UUID)", example = "bab17d8b-af38-4531-967c-083f15ca1571")
	private String organizationId;

}
