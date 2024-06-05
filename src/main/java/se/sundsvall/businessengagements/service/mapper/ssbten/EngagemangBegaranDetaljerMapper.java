package se.sundsvall.businessengagements.service.mapper.ssbten;

import java.util.List;

import org.springframework.stereotype.Component;

import se.sundsvall.businessengagements.domain.dto.BusinessEngagementsRequestDto;

import se.bolagsverket.schema.ssbt.foretag.PersonIdentitetsbeteckning;
import se.bolagsverket.schema.ssbten.engagemang.EngagemangBegaranDetaljer;
import se.bolagsverket.schema.ssbten.engagemang.Foretagsformer;
import se.bolagsverket.schema.ssbten.engagemang.ObjectFactory;
import se.bolagsverket.schema.ssbten.engagemang.PersonId;

/**
 * <pre>
 *     Creates the following xml:
 *       {@code
 *       <EngagemangBegaranDetaljer>
 *           <PersonId>
 *               <iumf:PersonIdentitetsbeteckning>
 *                   <iumf:Personnummer>198001011234</iumf:Personnummer>
 *               </iumf:PersonIdentitetsbeteckning>
 *           </PersonId>
 *           <Foretagsformer>
 *               <iumf:ForetagsformKod>AB</iumf:ForetagsformKod>
 *               <iumf:ForetagsformKod>BRF</iumf:ForetagsformKod>
 *               <iumf:ForetagsformKod>E</iumf:ForetagsformKod>
 *               <iumf:ForetagsformKod>EK</iumf:ForetagsformKod>
 *               <iumf:ForetagsformKod>HB</iumf:ForetagsformKod>
 *               <iumf:ForetagsformKod>KB</iumf:ForetagsformKod>
 *           </Foretagsformer>
 *       </EngagemangBegaranDetaljer>
 *       }
 *
 * </pre>
 */
@Component
public class EngagemangBegaranDetaljerMapper {

	//These are the different Business/Corporate forms we want to see, i.e. all of them
	private static final List<String> FORETAGSFORM_KODER = List.of("AB", "BRF", "E", "EK", "HB", "KB");

	private final ObjectFactory objectFactory = new ObjectFactory();

	private final se.bolagsverket.schema.ssbt.foretag.ObjectFactory foretagObjectFactory = new se.bolagsverket.schema.ssbt.foretag.ObjectFactory();

	EngagemangBegaranDetaljer createEngagemangBegaranDetaljer(final BusinessEngagementsRequestDto requestDto) {
		return objectFactory.createEngagemangBegaranDetaljer()
			.withPersonId(createPersonId(requestDto.getLegalId()))
			.withForetagsformer(createForetagsformer());
	}

	private PersonId createPersonId(final String personalNumber) {
		return objectFactory.createPersonId()
			.withPersonIdentitetsbeteckning(createPersonIdentitetsbeteckning(personalNumber));
	}

	private PersonIdentitetsbeteckning createPersonIdentitetsbeteckning(final String personalNumber) {
		return foretagObjectFactory.createPersonIdentitetsbeteckning()
			.withPersonnummer(personalNumber);
	}

	private Foretagsformer createForetagsformer() {
		return objectFactory.createForetagsformer()
			//Attribute "allaEnskilda" is not allowed when usecasearea == "indirect".
			.withAllaEnskilda(false)
			.withForetagsformKods(FORETAGSFORM_KODER);
	}
}
