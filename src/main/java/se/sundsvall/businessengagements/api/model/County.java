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
@Schema(description = "County information model")
public class County {

	@Schema(description = "County", examples = "Västernorrland")
	private String countyName;

	@Schema(description = "County code", examples = "22")
	private String countyCode;

}
