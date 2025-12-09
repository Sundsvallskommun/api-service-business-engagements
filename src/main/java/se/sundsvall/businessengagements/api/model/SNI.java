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
@Schema(description = "Swedish Standard Industrial Classification model")
public class SNI {

	@Schema(description = "SNI code", examples = "72110")
	private String sniCode;

	@Schema(description = "SNO description", examples = "Bioteknisk forskning och utveckling")
	private String sniDescription;

}
