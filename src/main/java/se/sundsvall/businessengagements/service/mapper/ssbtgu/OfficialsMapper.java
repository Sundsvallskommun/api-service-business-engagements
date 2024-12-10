package se.sundsvall.businessengagements.service.mapper.ssbtgu;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import se.sundsvall.businessengagements.api.model.Official;

public class OfficialsMapper {

	private OfficialsMapper() {}

	/**
	 * <pre>
	 * Since we get officials as a String from bolagsverket we need to parse the different parts into something more readable.
	 * The string we get looks something like this:
	 *      styrelseledamot,ordförande
	 *      198000000010 Larsson, Kent, SUNDSVALL
	 *      styrelseledamot
	 *      198001011234 Karlsson, Kristina, STOCKHOLM
	 *      198000000011 Larsson, Nils, SUNDSVALL
	 *  The layout is: A description of the role, followed by one or more persons/organizations, repeats.
	 * </pre>
	 *
	 * @param  officialsString to map
	 * @return                 List of {@link Official}
	 */
	public static List<Official> mapOfficials(String officialsString) {
		if (StringUtils.isBlank(officialsString)) {
			return emptyList();
		}
		Map<String, Official> officialsMap = new HashMap<>();   // Store it temporary in a map since to be sure that we don't get duplicates
		String[] split = officialsString.split("\r\n|\n|\r"); // Split on newline

		var lines = new ArrayList<>(Arrays.asList(split));

		int ctr = 0;
		var roleAsString = "";   // Here we store e.g. "styrelseledamot,ordförande", defined here for later use

		while (ctr < lines.size()) {
			var line = lines.get(ctr).trim(); // Get a row/line and trim it
			// Check if it doesn't start with a number, then it's a description of the role, anything else is a person/organization
			if (!Character.isDigit(line.charAt(0))) {

				// Get the current row, which will now only contain roles, e.g. "styrelseledamot,ordförande"
				roleAsString = lines.get(ctr);
			} else {

				// Here we have something along "198000000011 Larsson, Nils, SUNDSVALL"
				var newLine = lines.get(ctr);

				// If it starts with a number, it's the personalNumber / organizationNumber.
				if (Character.isDigit(newLine.charAt(0))) {
					// Extract all parts from the string/line
					var legalId = getLegalId(newLine);
					var name = getName(newLine);
					var positions = getPositions(roleAsString);
					var location = getLocation(newLine).trim();

					// If we get a personalId/orgNo already stored we want to update its roles, not add new ones.
					if (officialsMap.containsKey(legalId)) {
						officialsMap.get(legalId).getRoles().addAll(positions);
					} else {
						// Not present, create a new object.
						officialsMap.put(legalId, Official.builder()
							.withLocation(location)
							.withName(name)
							.withLegalId(legalId)
							.withRoles(positions)
							.build());
					}
				}
			}
			ctr++;
		}

		// Now we know that all officials and their information is "gathered", ditch the map and only return a list of all
		// Officials objects.
		return new ArrayList<>(officialsMap.values());
	}

	private static String getLocation(String location) {
		return location.substring(location.lastIndexOf(' '));
	}

	private static List<String> getPositions(String role) {
		var immutableRoles = Arrays.stream(role.split(","))
			.map(String::trim)
			.toList();
		return new ArrayList<>(immutableRoles);
	}

	private static String getLegalId(String line) {
		return line.substring(0, line.indexOf(" "));
	}

	private static String getName(String line) {
		return line.substring(line.indexOf(" "), line.lastIndexOf(",")).trim();
	}

}
