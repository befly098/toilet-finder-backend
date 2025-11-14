package io.seorin.ddongkan.dto;

import jakarta.validation.constraints.NotNull;

public class Review {
	@NotNull
	private final int stars;
	private final String comment;
	private final Likes likes;

	public Review(Integer stars, String comment, Likes likes) {
		this.stars = stars;
		this.comment = comment;
		this.likes = likes;
	}

	public int getStars() {
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
