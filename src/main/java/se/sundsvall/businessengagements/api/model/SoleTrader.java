package se.sundsvall.businessengagements.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Sole trader information")
public class SoleTrader {

	@Schema(description = "First names")
	private List<String> firstNames;

	@Schema(description = "Middle name")
	private String middleName;

	@Schema(description = "Last name")
	private String surName;

	@Schema(implementation = Address.class)
	private Address address;

}
