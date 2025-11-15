package io.seorin.ddongkan.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReviewRequest {
	@Min(1)
	@Max(5)
	private Integer stars;

	@Length(max = 1000)
	private String comment;

	@NotNull
	private Likes likes;

	public ReviewRequest() {
		// for fixture-monkey
	}

	public Integer getStars() {
		return this.stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Likes getLikes() {
		return this.likes;
	}

	public void setLikes(Likes likes) {
		this.likes = likes;
	}

	@Override
	public String toString() {
		return "ReviewRequest{" +
			"stars=" + stars +
			", comment='" + comment + '\'' +
			", likes=" + likes.toString() +
			'}';
	}

	public static class Likes {
		private boolean cleanliness;
		private boolean toiletPaper;
		private boolean waterPressure;
		private boolean hotWater;
		private boolean safety;

		public Likes() {
			// for fixture-monkey
		}

		public boolean isCleanliness() {
			return cleanliness;
		}

		public void setCleanliness(boolean cleanliness) {
			this.cleanliness = cleanliness;
		}

		public boolean isToiletPaper() {
			return toiletPaper;
		}

		public void setToiletPaper(boolean toiletPaper) {
			this.toiletPaper = toiletPaper;
		}

		public boolean isWaterPressure() {
			return waterPressure;
		}

		public void setWaterPressure(boolean waterPressure) {
			this.waterPressure = waterPressure;
		}

		public boolean isHotWater() {
			return hotWater;
		}

		public void setHotWater(boolean hotWater) {
			this.hotWater = hotWater;
		}

		public boolean isSafety() {
			return safety;
		}

		public void setSafety(boolean safety) {
			this.safety = safety;
		}

		@Override
		public String toString() {
			return "Likes{" +
				"cleanliness=" + this.cleanliness +
				", toiletPaper=" + this.toiletPaper +
				", waterPressure=" + this.waterPressure +
				", hotWater=" + this.hotWater +
				", safety=" + this.safety +
				'}';
		}
	}
}
