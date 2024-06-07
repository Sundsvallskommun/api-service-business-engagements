package se.sundsvall.businessengagements.api.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Liquidation information model")
public class LiquidationInformation {

	@ArraySchema(schema = @Schema(implementation = LiquidationReason.class))
	private List<LiquidationReason> liquidationReasons;

	private LiquidationReason cancelledLiquidation;

	@Getter
	@Setter
	@Builder(setterPrefix = "with")
	@Schema(description = "Reason for liquidation or cancellation of liquidation")
	public static class LiquidationReason implements Serializable {

		@Serial
		private static final long serialVersionUID = -6283599400246819040L;

		@Schema(description = "Liquidation code", example = "21")
		private String liquidationCode;

		@Schema(description = "Liquidation description", example = "Konkurs avslutad")
		private String liquidationDescription;

		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		@Schema(description = "Liquidation date", example = "2022-09-01")
		private LocalDate liquidationDate;

		@Schema(description = "Type of liquidation", example = "Konkurs")
		private String liquidationType;

	}

}
