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
@Schema(description = "Address model")
public class Address {

	@Schema(description = "City", examples = "Sundsvall")
	private String city;

	@Schema(description = "Street address", examples = "Storgatan 10")
	private String street;

	@Schema(description = "Postal code", examples = "85740")
	private String postcode;

	@Schema(description = "Care of", examples = "John Doe")
	private String careOf;

}
