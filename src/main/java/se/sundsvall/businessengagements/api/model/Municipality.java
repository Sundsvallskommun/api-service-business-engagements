package se.sundsvall.businessengagements.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Municipality information model")
public class Municipality {

	// UD0011
	@Schema(description = "Municipality", examples = "Sundsvalls Kommun")
	private String municipalityName;

	// UD0011
	@Schema(description = "Municipality code", examples = "2281")
	private String municipalityCode;

}
