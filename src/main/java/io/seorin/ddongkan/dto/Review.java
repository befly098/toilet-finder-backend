package io.seorin.ddongkan.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;

public class Review {
	@Range(min = 1, max = 5)
	private final Integer stars;

	@Length(max = 1000)
	private final String comment;
	private final Likes likes;

	public Review(Integer stars, String comment, Likes likes) {
		this.stars = stars;
		this.comment = comment;
		this.likes = likes;
	}

	public Integer getStars() {
		return this.stars;
	}

	public String getComment() {
		return this.comment;
	}

	public Likes getLikes() {
		return this.likes;
	}

	public static class Likes {
		@NotNull
		private final boolean cleanliness;
		@NotNull
		private final boolean toiletPaper;
		@NotNull
		private final boolean waterPressure;
		@NotNull
		private final boolean hotWater;
		@NotNull
		private final boolean safety;

		public Likes(boolean cleanliness, boolean toiletPaper, boolean waterPressure, boolean hotWater,
			boolean safety) {
			this.cleanliness = cleanliness;
			this.toiletPaper = toiletPaper;
			this.waterPressure = waterPressure;
			this.hotWater = hotWater;
			this.safety = safety;
		}
	}
}
