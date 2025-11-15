package io.seorin.ddongkan.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.CreationTimestamp;

import io.seorin.ddongkan.dto.ReviewRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "review")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "toilet_id", nullable = false)
	private Toilet toilet;

	@Column
	private Integer stars;

	@Column(length = 1000)
	private String comment;

	@Column(nullable = false)
	private boolean cleanliness;

	@Column(nullable = false)
	private boolean toiletPaper;

	@Column(nullable = false)
	private boolean waterPressure;

	@Column(nullable = false)
	private boolean hotWater;

	@Column(nullable = false)
	private boolean safety;

	@Column(updatable = false)
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private ZonedDateTime createdAt;

	public Review() {
	}

	private Review(Long id, Toilet toilet, Integer stars, String comment, boolean cleanliness, boolean toiletPaper,
		boolean waterPressure, boolean hotWater, boolean safety, ZonedDateTime createdAt) {
		this.id = id;
		this.toilet = toilet;
		this.stars = stars;
		this.comment = comment;
		this.cleanliness = cleanliness;
		this.toiletPaper = toiletPaper;
		this.waterPressure = waterPressure;
		this.hotWater = hotWater;
		this.safety = safety;
		this.createdAt = createdAt;
	}

	public static Review toiletReviewOf(Toilet toilet, ReviewRequest reviewRequest) {
		return new Review(
			null,
			toilet,
			reviewRequest.getStars(),
			reviewRequest.getComment(),
			reviewRequest.getLikes().isCleanliness(),
			reviewRequest.getLikes().isToiletPaper(),
			reviewRequest.getLikes().isWaterPressure(),
			reviewRequest.getLikes().isHotWater(),
			reviewRequest.getLikes().isSafety(),
			null
		);
	}
}
