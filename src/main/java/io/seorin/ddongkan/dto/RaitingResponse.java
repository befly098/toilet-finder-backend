package io.seorin.ddongkan.dto;

import io.seorin.ddongkan.entity.Rating;

public class RaitingResponse {

	private Double avgStars;
	private  Double avgCleanliness;
	private Double avgToiletPaper;
	private Double avgWaterPressure;
	private Double avgHotWater;
	private Double avgSafety;


	private RaitingResponse(Double avgStars, Double avgCleanliness, Double avgToiletPaper, Double avgWaterPressure,
		Double avgHotWater, Double avgSafety) {
		this.avgStars = avgStars;
		this.avgCleanliness = avgCleanliness;
		this.avgToiletPaper = avgToiletPaper;
		this.avgWaterPressure = avgWaterPressure;
		this.avgHotWater = avgHotWater;
		this.avgSafety = avgSafety;
	}

	public static RaitingResponse from(Rating rating) {
		return new RaitingResponse(
			setDeimicalPointToOne(rating.getAvgStars()),
			setDeimicalPointToOne(rating.getAvgCleanliness()),
			setDeimicalPointToOne(rating.getAvgToiletPaper()),
			setDeimicalPointToOne(rating.getAvgWaterPressure()),
			setDeimicalPointToOne(rating.getAvgHotWater()),
			setDeimicalPointToOne(rating.getAvgSafety())
		);
	}

	private static Double setDeimicalPointToOne(Double value) {
		if (value == null) {
			return null;
		}
		return Math.round(value * 10) / 10.0;
	}

	public Double getAvgStars() {
		return this.avgStars;
	}

	public Double getAvgCleanliness() {
		return this.avgCleanliness;
	}

	public Double getAvgToiletPaper() {
		return this.avgToiletPaper;
	}

	public Double getAvgWaterPressure() {
		return this.avgWaterPressure;
	}

	public Double getAvgHotWater() {
		return this.avgHotWater;
	}

	public Double getAvgSafety() {
		return this.avgSafety;
	}
}
