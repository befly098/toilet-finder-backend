package io.seorin.ddongkan.dto;

public class RaitingResponse {

	private Double avgStars;
	private  Double avgClineliness;
	private Double avgToiletPaper;
	private Double avgWaterPressure;
	private Double avgHotWater;
	private Double avgSafety;

	// TODO: avg 계산은 redis에서 처리

	private RaitingResponse(Double avgStars, Double avgClineliness, Double avgToiletPaper, Double avgWaterPressure,
		Double avgHotWater, Double avgSafety) {
		this.avgStars = avgStars;
		this.avgClineliness = avgClineliness;
		this.avgToiletPaper = avgToiletPaper;
		this.avgWaterPressure = avgWaterPressure;
		this.avgHotWater = avgHotWater;
		this.avgSafety = avgSafety;
	}

	public static RaitingResponse of(Double avgStars, Double avgClineliness, Double avgToiletPaper,
		Double avgWaterPressure, Double avgHotWater, Double avgSafety) {
		return new RaitingResponse(
			setDeimicalPointToOne(avgStars),
			setDeimicalPointToOne(avgClineliness),
			setDeimicalPointToOne(avgToiletPaper),
			setDeimicalPointToOne(avgWaterPressure),
			setDeimicalPointToOne(avgHotWater),
			setDeimicalPointToOne(avgSafety)
		);
	}

	private static Double setDeimicalPointToOne(Double value) {
		if (value == null) return null;
		return Math.round(value * 10) / 10.0;
	}
}
