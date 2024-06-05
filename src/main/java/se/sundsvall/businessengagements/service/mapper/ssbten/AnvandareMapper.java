package se.sundsvall.businessengagements.service.mapper.ssbten;

import org.springframework.stereotype.Component;

import se.sundsvall.businessengagements.domain.dto.BusinessEngagementsRequestDto;

import se.bolagsverket.schema.ssbt.metadata.ObjectFactory;
import se.bolagsverket.schema.ssbt.metadata.Part;

/**
 * <pre>
 * Creates the following xml:
 * {@code
 *   <md:Anvandare>
 *       <md:PartId>
 *           <md:Personnummer>198001011234</md:Personnummer>
 *       </md:PartId>
 *       <md:PartNamn>Jon Doe</md:PartNamn>
 *   </md:Anvandare>
 * }
 * </pre>
 */
@Component
public class AnvandareMapper {

	public Part createAnvandare(final BusinessEngagementsRequestDto requestDto) {
		var objectFactory = new ObjectFactory();

		var partId = objectFactory.createPartId()
			.withPersonnummer(requestDto.getLegalId());

		return objectFactory.createPart()
			.withPartId(partId)
			.withPartNamn(requestDto.getPersonalName());
	}

}
