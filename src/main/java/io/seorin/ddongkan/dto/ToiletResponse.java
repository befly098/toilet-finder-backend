package io.seorin.ddongkan.dto;

import org.locationtech.jts.geom.Point;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.redis.connection.RedisGeoCommands;

import io.seorin.ddongkan.entity.Toilet;
import io.seorin.ddongkan.utility.ToiletGeoMember;

public class ToiletResponse {
	private final Long id;
	private final String name;
	private final Coord coord;

	protected ToiletResponse(Long id, String name, Coord coord) {
		this.id = id;
		this.name = name;
		this.coord = coord;
	}

	public static ToiletResponse from(Toilet toilet) {
		return new ToiletResponse(
			toilet.getId(),
			toilet.getName(),
			Coord.from(toilet.getPosition())
		);
	}

	public static ToiletResponse fromCache(GeoResult<RedisGeoCommands.GeoLocation<String>> location) {
		var resolved = ToiletGeoMember.decode(location.getContent().getName());
		return new ToiletResponse(
			resolved.id(),
			resolved.name(),
			Coord.fromGeoPoint(location.getContent().getPoint())
		);
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Coord getCoord() {
		return this.coord;
	}

	@Override
	public String toString() {
		// @checkstyle:off
		return "ToiletResponse{" +
			"id=" + this.id +
			", name='" + this.name + '\'' +
			", coord=" + this.coord +
			'}';
		// @checkstyle:on
	}

	public static class Coord {
		private final Double lat;
		private final Double lng;

		private Coord(Double lat, Double lng) {
			this.lat = lat;
			this.lng = lng;
		}

		public static Coord from(Point point) {
			return new Coord(
				point != null ? point.getY() : null,
				point != null ? point.getX() : null
			);
		}

		public static Coord fromGeoPoint(org.springframework.data.geo.Point point) {
			return new Coord(
				point != null ? point.getY() : null,
				point != null ? point.getX() : null
			);
		}

		public Double getLat() {
			return this.lat;
		}

		public Double getLng() {
			return this.lng;
		}

		@Override
		public String toString() {
			// @checkstyle:off
			return "Coord{" +
				"lat=" + this.lat +
				", lng=" + this.lng +
				'}';
			// @checkstyle:on
		}
	}
}
