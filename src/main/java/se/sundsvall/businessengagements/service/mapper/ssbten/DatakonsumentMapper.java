package se.sundsvall.businessengagements.service.mapper.ssbten;

import static se.sundsvall.businessengagements.service.BusinessEngagementsService.SERVICE_NAME;
import static se.sundsvall.businessengagements.service.BusinessEngagementsService.SUNDSVALL_MUNICIPALITY_ORGANIZATION_NUMBER;

import org.springframework.stereotype.Component;

import se.bolagsverket.schema.ssbt.metadata.Datakonsument;
import se.bolagsverket.schema.ssbt.metadata.ObjectFactory;
import se.bolagsverket.schema.ssbt.metadata.PartId;
import se.bolagsverket.schema.ssbt.metadata.Service;

/**
 * <pre>
 *     Skapar följande xml:
 *     {@code
 *       <md:Datakonsument>
 *           <md:PartId>
 *               <md:Organisationsnummer>2021001234</md:Organisationsnummer>
 *           </md:PartId>
 *           <md:PartNamn>SundsvallsKommun</md:PartNamn>
 *           <md:Service>
 *               <md:ServiceNamn>BUSENGBV</md:ServiceNamn>
 *           </md:Service>
 *       </md:Datakonsument>
 *     }
 *
 * </pre>
 */
@Component
public class DatakonsumentMapper {

	private final ObjectFactory metaDataFactory = new ObjectFactory();

	/**
	 * <pre>
	 *     From bolagsverkets documentation:
	 *     "SSBTEN kontrollerar
	 *      att datakonsumentens organisationsnummer i begäran överensstämmer med
	 *      organisationsnumret i datakonsumentens organisationscertifikat samt att
	 *      organisationsnummer och servicenamn i begäran finns registrerad hos Bolagsverket"
	 *
	 * i.e make sure that all names correspond whith those registered at Bolagsverket
	 *  - PartId: e.g. SundsvallsKommun?
	 *  - ServiceNamn: BUSENGBV
	 *  - Organisationsnummer: kommunens orgnummer?
	 *
	 *  </pre>
	 *
	 * @return {}
	 */
	Datakonsument createDatakonsument() {
		return metaDataFactory.createDatakonsument()
			.withPartId(createPartId())
			.withPartNamn("SundsvallsKommun")  //I think we can use whatever here.
			.withService(createService());
	}

	private PartId createPartId() {
		return metaDataFactory.createPartId()
			.withOrganisationsnummer(SUNDSVALL_MUNICIPALITY_ORGANIZATION_NUMBER);
	}

	private Service createService() {
		return metaDataFactory.createService()
			.withServiceNamn(SERVICE_NAME);
	}

}
