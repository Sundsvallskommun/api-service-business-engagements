package se.sundsvall.businessengagements.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Business engagements response model")
public class BusinessEngagementsResponse implements Serializable {

	@Serial
	private static final long serialVersionUID = 7202306090787565132L;

	private List<Engagement> engagements;

	@Schema(description = "In case fetching one or more engagement failed, this will show why it failed."
		+ " There may be more than one description if several engagements failed.",
		example = "Timeout")
	private Map<String, String> statusDescriptions;

	@Schema(description = "If fetching all engagements went \"OK\" or \"NOK\". "
		+ "A \"NOK\" may still return engagements but indicates that the information is incomplete.",
		example = "OK",
		requiredMode = Schema.RequiredMode.REQUIRED)
	private Status status;

	public void addEngagement(Engagement engagement) {
		if (this.engagements == null) {
			this.engagements = new ArrayList<>();
		}
		this.engagements.add(engagement);
	}

	/**
	 * Convenience method for adding an engagement to the response.
	 *
	 * @param organizationName   The name of the organization
	 * @param organizationNumber The organization number
	 */
	public void addEngagement(String organizationName, String organizationNumber) {
		addEngagement(Engagement.builder()
			.withOrganizationName(organizationName)
			.withOrganizationNumber(organizationNumber)
			.build());
	}

	/**
	 * Adds a status description to the response, only used when something went wrong from integrations.
	 *
	 * @param type              The type of the status
	 * @param statusDescription The description of the status
	 */
	public void addStatusDescription(String type, String statusDescription) {
		if (this.statusDescriptions == null) {
			this.statusDescriptions = new HashMap<>();
		}
		this.statusDescriptions.put(type, statusDescription);
	}

	@Schema()
	public enum Status implements Serializable {
		OK, NOK
	}

}
