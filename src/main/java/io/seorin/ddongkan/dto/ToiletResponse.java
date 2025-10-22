package io.seorin.ddongkan.dto;

public record ToiletResponse(
	Integer id,
	String name,
	String address,
	Double lat,
	Double lng,
	String openHours,
	Boolean accessible,
	Boolean diaper,
	Boolean unisex,
	Double avgRating,
	Double avgCleanliness
) {
}
