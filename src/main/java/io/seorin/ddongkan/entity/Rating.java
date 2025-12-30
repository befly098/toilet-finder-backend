package io.seorin.ddongkan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "toilet_rating_view")
public class Rating {

	@Id
	@Column(name = "toilet_id")
	private Long toiletId;

	@Column(name = "avg_stars")
	private Double avgStars;

	@Column(name = "avg_cleanliness")
	private Double avgCleanliness;

	@Column(name = "avg_toilet_paper")
	private Double avgToiletPaper;

	@Column(name = "avg_water_pressure")
	private Double avgWaterPressure;

	@Column(name = "avg_hot_water")
	private Double avgHotWater;

	@Column(name = "avg_safety")
	private Double avgSafety;

	@Column(name = "review_count")
	private Long reviewCount;

	protected Rating() {
		// JPA only
	}

	public Long getToiletId() {
		return this.toiletId;
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

	public Long getReviewCount() {
		return this.reviewCount;
	}
}