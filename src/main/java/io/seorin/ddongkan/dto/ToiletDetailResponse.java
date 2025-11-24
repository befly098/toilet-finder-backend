package io.seorin.ddongkan.dto;

import java.time.ZonedDateTime;

import io.seorin.ddongkan.entity.Toilet;

public class ToiletDetailResponse extends ToiletResponse {

	private String address;
	private ZonedDateTime openAt;
	private ZonedDateTime closeAt;
	private Boolean accessible;
	private Boolean diaper;
	private Boolean unisex;

	private ToiletDetailResponse(Long id, String name, Coord coord, String address, ZonedDateTime openAt,
		ZonedDateTime closeAt, Boolean accessible, Boolean diaper, Boolean unisex) {
		super(id, name, coord);
		this.address = address;
		this.openAt = openAt;
		this.closeAt = closeAt;
		this.accessible = accessible;
		this.diaper = diaper;
		this.unisex = unisex;
	}

	public static ToiletDetailResponse from(Toilet toilet) {
		var coord = Coord.from(toilet.getPosition());
		return new ToiletDetailResponse(
			toilet.getId(),
			toilet.getName(),
			coord,
			toilet.getAddress(),
			toilet.getOpenAt(),
			toilet.getCloseAt(),
			toilet.isAccessible(),
			toilet.isDiaper(),
			toilet.isUnisex()
		);
	}

	public String getAddress() {
		return this.address;
	}

	public ZonedDateTime getOpenAt() {
		return this.openAt;
	}

	public ZonedDateTime getCloseAt() {
		return this.closeAt;
	}

	public Boolean getAccessible() {
		return this.accessible;
	}

	public Boolean getDiaper() {
		return this.diaper;
	}

	public Boolean getUnisex() {
		return this.unisex;
	}

	@Override
	public String toString() {
		// @checkstyle:off
		return super.toString() +
			"ToiletDetailResponse{" +
			"address='" + address + '\'' +
			", openAt=" + openAt +
			", closeAt=" + closeAt +
			", accessible=" + accessible +
			", diaper=" + diaper +
			", unisex=" + unisex +
			'}';
		// @checkstyle:on
	}
}
