package se.sundsvall.businessengagements;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import se.sundsvall.businessengagements.domain.dto.BusinessEngagementsRequestDto;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import se.bolagsverket.schema.ssbten.engagemang.EngagemangSvar;

public final class TestObjectFactory {

	public static BusinessEngagementsRequestDto createDummyRequestDto() {
		var dto = new BusinessEngagementsRequestDto();
		dto.setLegalId("198001011234");
		dto.setPersonalName("John Doe");
		dto.setPartyId("abc123");
		dto.setServiceName("Service");

		return dto;
	}

	public static String getHappyFileAsString() throws IOException {
		return new String(Files.readAllBytes(Paths.get("src/test/resources/bolagsverket-response-happy.xml")));
	}

	public static String getFailFileAsString() throws IOException {
		return new String(Files.readAllBytes(Paths.get("src/test/resources/bolagsverket-response-fail.xml")));
	}

	public static EngagemangSvar getEngagemangSvar(String xml) throws Exception {
		final SOAPMessage message = MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(xml.getBytes()));
		final Unmarshaller unmarshaller = JAXBContext.newInstance(EngagemangSvar.class).createUnmarshaller();

		return (EngagemangSvar) unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument());
	}
}
