package io.seorin.ddongkan.dto;

import java.time.ZonedDateTime;

import io.seorin.ddongkan.entity.Review;

public class ReviewResponse {
	private Integer stars;

	private String comment;

	private boolean cleanliness;

	private boolean toiletPaper;

	private boolean waterPressure;

	private boolean hotWater;

	private boolean safety;

	private ZonedDateTime createdAt;

	public Integer getStars() {
		return this.stars;
	}

	public String getComment() {
		return this.comment;
	}

	public boolean isCleanliness() {
		return this.cleanliness;
	}

	public boolean isToiletPaper() {
		return this.toiletPaper;
	}

	public boolean isWaterPressure() {
		return this.waterPressure;
	}

	public boolean isHotWater() {
		return this.hotWater;
	}

	public boolean isSafety() {
		return this.safety;
	}

	public ZonedDateTime getCreatedAt() {
		return this.createdAt;
	}

	public ReviewResponse(Integer stars, String comment, boolean cleanliness, boolean toiletPaper,
		boolean waterPressure, boolean hotWater, boolean safety, ZonedDateTime createdAt) {
		this.stars = stars;
		this.comment = comment;
		this.cleanliness = cleanliness;
		this.toiletPaper = toiletPaper;
		this.waterPressure = waterPressure;
		this.hotWater = hotWater;
		this.safety = safety;
		this.createdAt = createdAt;
	}

	public static ReviewResponse from(Review review) {
		return new ReviewResponse(
			review.getStars(),
			review.getComment(),
			review.isCleanliness(),
			review.isToiletPaper(),
			review.isWaterPressure(),
			review.isHotWater(),
			review.isSafety(),
			review.getCreatedAt()
		);
	}
}
