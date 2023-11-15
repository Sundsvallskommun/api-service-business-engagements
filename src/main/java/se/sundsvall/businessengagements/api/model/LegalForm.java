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
@Schema(description = "Legal form information model")
public class LegalForm {

	//UD0002
	@Schema(description = "Legal form", example = "Övriga aktiebolag")
	private String legalFormDescription;

	//UD0002
	@Schema(description = "Legal form code", example = "49")
	private String legalFormCode;

}
