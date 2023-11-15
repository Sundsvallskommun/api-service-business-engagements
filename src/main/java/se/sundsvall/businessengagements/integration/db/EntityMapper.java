package se.sundsvall.businessengagements.integration.db;

import java.time.LocalDateTime;

import se.sundsvall.businessengagements.api.model.BusinessEngagementsResponse;
import se.sundsvall.businessengagements.integration.db.entity.EngagementsCacheEntity;

public class EntityMapper {

	private EntityMapper() {}

	public static EngagementsCacheEntity mapResponseToEntity(BusinessEngagementsResponse response, String partyId) {
		return EngagementsCacheEntity.builder()
			.withPartyId(partyId)
			.withResponse(response)
			.withUpdated(LocalDateTime.now())
			.build();
	}

}
