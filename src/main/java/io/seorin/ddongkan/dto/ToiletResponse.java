package io.seorin.ddongkan.dto;

import io.seorin.ddongkan.entity.Toilet;

// TODO: 불필요한 필드 제거
// id, name, lat, long 빼고 없애자
public record ToiletResponse(
	Long id,
	String name,
	Double lat,
	Double lng
) {
	public static ToiletResponse from(Toilet toilet) {
		return ToiletResponse.builder()
			.id(toilet.getId())
			.name(toilet.getName())
			.lat(toilet.getPosition() != null ? toilet.getPosition().getY() : null)
			.lng(toilet.getPosition() != null ? toilet.getPosition().getX() : null)
			.build();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private String name;
		private Double lat;
		private Double lng;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
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

		public ToiletResponse build() {
			return new ToiletResponse(
				id,
				name,
				lat,
				lng
			);
		}
	}
}
