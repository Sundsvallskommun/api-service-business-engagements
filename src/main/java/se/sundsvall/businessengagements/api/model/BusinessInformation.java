package se.sundsvall.businessengagements.api.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Business information model")
public class BusinessInformation {

	// UD0001
	@Schema(description = "The company's name", example = "Sundsvalls kommun")
	private String companyName;

	// UD0002
	@Schema(implementation = LegalForm.class)
	private LegalForm legalForm;

	// UD0003
	@Schema(implementation = Address.class)
	private Address address;

	// UD0004
	@Schema(description = "The company's contact email", example = "somecompany@noreply.com")
	private String emailAddress;

	// UD0006
	@Schema(description = "The company's contact phone number", example = "070-1234567")
	private String phoneNumber;

	// UD0011
	@Schema(implementation = Municipality.class)
	private Municipality municipality;

	// UD0012
	@Schema(implementation = County.class)
	private County county;

	// UD0014
	@Schema(implementation = FiscalYear.class)
	private FiscalYear fiscalYear;

	// UD0025
	@Schema(implementation = CompanyForm.class)
	private CompanyForm companyForm;

	// UD0026
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Schema(description = "When the company was registered", example = "2022-01-01")
	private LocalDate companyRegistrationTime;

	// UD0027
	@Schema(description = "Liquidation information")
	private LiquidationInformation liquidationInformation;

	// UD0028
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Schema(description = "When and if the company was deregistered", example = "2022-09-01")
	private LocalDate deregistrationDate;

	// UD0040
	@Schema(description = "Company location", implementation = Address.class)
	private CompanyLocation companyLocation;

	// UD0045
	@Schema(description = "Who may sign for the company", example = "Firman tecknas av styrelsen")
	private String businessSignatory;

	// UD0046
	@Schema(description = "Information regarding the company's operations", example = "Psykologisk forskning på bin samt därmed förenlig verksamhet.")
	private String companyDescription;

	// UD0048
	@Schema(implementation = SharesInformation.class)
	private SharesInformation sharesInformation;

	@Schema(implementation = ErrorInformation.class)
	private ErrorInformation errorInformation;

	public void addErrorInformation(String errorCode, String errorDescription) {
		if (this.errorInformation == null) {
			this.errorInformation = new ErrorInformation();
		}
		this.errorInformation.addErrorDescription(errorCode, errorDescription);
	}

}
