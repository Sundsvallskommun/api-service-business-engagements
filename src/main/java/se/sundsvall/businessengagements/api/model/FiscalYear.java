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
@Schema(description = "Fiscal year information model")
public class FiscalYear {

	@Schema(description = "Fiscal year start day", example = "1")
	private int fromDay;

	@Schema(description = "Fiscal year start month", example = "1")
	private int fromMonth;

	@Schema(description = "Fiscal year end day", example = "31")
	private int toDay;

	@Schema(description = "Fiscal year end month", example = "12")
	private int toMonth;

}
