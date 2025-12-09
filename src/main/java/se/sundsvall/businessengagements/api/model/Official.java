package se.sundsvall.businessengagements.api.model;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Officials information model")
public class Official {

	@ArraySchema(schema = @Schema(description = "List of roles the official has", examples = {
		"styrelseledamot", "ordförande"
	}))
	@Builder.Default
	private List<String> roles = new ArrayList<>();

	@Schema(description = "Legal Id of the official", examples = "198001011234")
	private String legalId;

	@Schema(description = "Name of the official", examples = "Kalle Kallesson")
	private String name;

	@Schema(description = "Location", examples = "Sundsvall")
	private String location;

}
