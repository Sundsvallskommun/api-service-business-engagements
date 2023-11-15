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

	@Schema(description = "City", example = "Sundsvall")
	private String city;

	@Schema(description = "Street address", example = "Storgatan 10")
	private String street;

	@Schema(description = "Postal code", example = "85740")
	private String postcode;

	@Schema(description = "Care of", example = "John Doe")
	private String careOf;

}
