package se.sundsvall.businessengagements.api.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

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
@Schema(description = "Shares information model")
public class SharesInformation {

	@ArraySchema(schema = @Schema(implementation = ShareType.class))
	List<ShareType> shareTypes;

	@Schema(description = "Number of total shares", example = "100000")
	private BigInteger numberOfShares;

	@Schema(description = "Shares value", example = "120000.00")
	private BigDecimal shareCapital;

	@Schema(description = "Shares value currency", example = "sek")
	private String shareCurrency;

	@Getter
	@Setter
	@Builder(setterPrefix = "with")
	@Schema(description = "Share information model")
	public static class ShareType {

		@Schema(description = "Label of the shares", example = "B")
		private String label;

		@Schema(description = "Number of shares of this class", example = "25000")
		private BigInteger numberOfShares;

		@Schema(description = "The vote value for one share", example = "1/10")
		private String voteValue;

	}

}
