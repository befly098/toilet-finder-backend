package io.seorin.ddongkan.dto;

import io.seorin.ddongkan.entity.Toilet;

public record ToiletResponse(
	Long id,
	String name,
	String address,
	Double lat,
	Double lng,
	String openHours,
	Boolean accessible,
	Boolean diaper,
	Boolean unisex,
	int avgRating,
	int avgCleanliness
) {
	public static ToiletResponse from(Toilet toilet) {
		return ToiletResponse.builder()
			.id(toilet.getId())
			.name(toilet.getName())
			.address(toilet.getAddress())
			.lat(toilet.getPosition() != null ? toilet.getPosition().getX() : null)
			.lng(toilet.getPosition() != null ? toilet.getPosition().getY() : null)
			.openHours(toilet.getOpenHours())
			.accessible(toilet.getAccessible())
			.diaper(toilet.getDiaper())
			.unisex(toilet.getUnisex())
			.avgRating(toilet.getAvgRating())
			.avgCleanliness(toilet.getAvgCleanliness())
			.build();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private String name;
		private String address;
		private Double lat;
		private Double lng;
		private String openHours;
		private Boolean accessible;
		private Boolean diaper;
		private Boolean unisex;
		private int avgRating;
		private int avgCleanliness;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder address(String address) {
			this.address = address;
			return this;
		}

		public Builder lat(Double lat) {
			this.lat = lat;
			return this;
		}

		public Builder lng(Double lng) {
			this.lng = lng;
			return this;
		}

		public Builder openHours(String openHours) {
			this.openHours = openHours;
			return this;
		}

		public Builder accessible(Boolean accessible) {
			this.accessible = accessible;
			return this;
		}

		public Builder diaper(Boolean diaper) {
			this.diaper = diaper;
			return this;
		}

		public Builder unisex(Boolean unisex) {
			this.unisex = unisex;
			return this;
		}

		public Builder avgRating(int avgRating) {
			this.avgRating = avgRating;
			return this;
		}

		public Builder avgCleanliness(int avgCleanliness) {
			this.avgCleanliness = avgCleanliness;
			return this;
		}

		public ToiletResponse build() {
			return new ToiletResponse(
				id,
				name,
				address,
				lat,
				lng,
				openHours,
				accessible,
				diaper,
				unisex,
				avgRating,
				avgCleanliness
			);
		}
	}
}
