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
@Schema(description = "Company form information model")
public class CompanyForm {

	// UD0025
	@Schema(description = "Company form", example = "AB")
	private String companyFormCode;

	// UD0025
	@Schema(description = "Company form description", example = "Aktiebolag")
	private String companyFormDescription;

}
