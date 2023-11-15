package se.sundsvall.businessengagements.integration.db.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import se.sundsvall.businessengagements.api.model.BusinessEngagementsResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class EngagementsCacheEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "party_id", nullable = false, unique = true)
	private String partyId;

	@Column(name = "last_updated")
	private LocalDateTime updated;

	@Lob
	@Column(name = "response", nullable = false, length = 10000)
	private BusinessEngagementsResponse response;

}
