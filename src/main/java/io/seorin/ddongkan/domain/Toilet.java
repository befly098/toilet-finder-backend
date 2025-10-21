package io.seorin.ddongkan.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "toilet",
	indexes = {
		@Index(name = "idx_toilet_lat_lng", columnList = "lat,lng"),
		@Index(name = "idx_toilet_name", columnList = "name")
	})
public class Toilet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 기본 정보
	@Column(nullable = false, length = 120)
	private String name;

	@Column(nullable = false, length = 255)
	private String address;

	// 위치 (PostGIS 도입 전까지 double 사용)
	@Column(nullable = false)
	private double lat;

	@Column(nullable = false)
	private double lng;

	// 이용 시간 (예: "24시간", "09:00~22:00")
	@Column(length = 120)
	private String openHours;

	// 편의 플래그
	@Column(nullable = false)
	private boolean accessible;   // 장애인 화장실 여부

	@Column(nullable = false)
	private boolean diaper;       // 기저귀 교환대 여부

	@Column(nullable = false)
	private boolean unisex;       // 남녀 공용 여부

	// 종합 별점 집계
	@Column(nullable = false)
	private long ratingSum;

	@Column(nullable = false)
	private long ratingCount;

	// 청결도 집계 (선택값만 집계)
	@Column(nullable = false)
	private long cleanlinessSum;

	@Column(nullable = false)
	private long cleanlinessCount;

	// 메타
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@UpdateTimestamp
	@Column(nullable = false)
	private Instant updatedAt;

	public Toilet() {
		// JPA 기본 생성자
	}

	public Toilet(String name, String address, double lat, double lng) {
		this.name = name;
		this.address = address;
		this.lat = lat;
		this.lng = lng;
		this.accessible = false;
		this.diaper = false;
		this.unisex = false;
		this.ratingSum = 0L;
		this.ratingCount = 0L;
		this.cleanlinessSum = 0L;
		this.cleanlinessCount = 0L;
	}

	// --- getters / setters ---
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }

	public double getLat() { return lat; }
	public void setLat(double lat) { this.lat = lat; }

	public double getLng() { return lng; }
	public void setLng(double lng) { this.lng = lng; }

	public String getOpenHours() { return openHours; }
	public void setOpenHours(String openHours) { this.openHours = openHours; }

	public boolean isAccessible() { return accessible; }
	public void setAccessible(boolean accessible) { this.accessible = accessible; }

	public boolean isDiaper() { return diaper; }
	public void setDiaper(boolean diaper) { this.diaper = diaper; }

	public boolean isUnisex() { return unisex; }
	public void setUnisex(boolean unisex) { this.unisex = unisex; }

	public long getRatingSum() { return ratingSum; }
	public void setRatingSum(long ratingSum) { this.ratingSum = ratingSum; }

	public long getRatingCount() { return ratingCount; }
	public void setRatingCount(long ratingCount) { this.ratingCount = ratingCount; }

	public long getCleanlinessSum() { return cleanlinessSum; }
	public void setCleanlinessSum(long cleanlinessSum) { this.cleanlinessSum = cleanlinessSum; }

	public long getCleanlinessCount() { return cleanlinessCount; }
	public void setCleanlinessCount(long cleanlinessCount) { this.cleanlinessCount = cleanlinessCount; }

	public Instant getCreatedAt() { return createdAt; }
	public Instant getUpdatedAt() { return updatedAt; }

	// --- helpers ---
	public double getAverageRating() {
		return ratingCount == 0 ? 0.0 : (double) ratingSum / ratingCount;
	}

	// 청결 평균은 데이터 없으면 0.0 반환; 필요 시 null 반환 형태로 바꿔도 됨.
	public double getAverageCleanliness() {
		return cleanlinessCount == 0 ? 0.0 : (double) cleanlinessSum / cleanlinessCount;
	}

	public void addRating(int score) {
		this.ratingSum += score;
		this.ratingCount += 1;
	}

	public void addCleanliness(Integer score) {
		if (score != null) {
			this.cleanlinessSum += score;
			this.cleanlinessCount += 1;
		}
	}
}