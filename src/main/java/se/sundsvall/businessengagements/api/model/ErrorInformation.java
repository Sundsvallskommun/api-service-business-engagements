package se.sundsvall.businessengagements.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error information model")
public class ErrorInformation {

	@Schema(description = "Indicates if there was an error while fetching data and that one or more parameters could not be fetched", examples = "true")
	private Boolean hasErrors;

	@Builder.Default
	@Schema(description = "Map with error code (from bolagsverket) as key and the error description as value", examples = "9071006, Ej behörig - ej firmatecknare.")
	private Map<String, String> errorDescriptions = new HashMap<>();

	/**
	 * @param errorCode
	 * @param errorDescription
	 */
	public void addErrorDescription(String errorCode, String errorDescription) {
		if (StringUtils.isNotBlank(errorCode) && StringUtils.isNotBlank(errorDescription)) {
			this.errorDescriptions.putIfAbsent(errorCode, errorDescription);
			this.hasErrors = true;
			// It's quite common that there's no errorCode and only an error description
		} else if (StringUtils.isBlank(errorCode) && StringUtils.isNotBlank(errorDescription)) {
			this.errorDescriptions.putIfAbsent("-1", errorDescription);
			this.hasErrors = true;
		}
	}

}
