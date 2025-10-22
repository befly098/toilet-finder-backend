package io.seorin.ddongkan.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "rating",
	uniqueConstraints = {
		@UniqueConstraint(name = "uk_rating_toilet_device", columnNames = {"toilet_id", "device_hash"})
	},
	indexes = {
		@Index(name = "idx_rating_toilet", columnList = "toilet_id"),
		@Index(name = "idx_rating_created_at", columnList = "created_at")
	})
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 대상 화장실
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "toilet_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_rating_toilet"))
	private Toilet toilet;

	// 종합 점수 (필수 1~5)
	@Column(nullable = false)
	private int score;

	// 청결 점수 (선택 1~5, null 허용)
	@Column(name = "cleanliness_score")
	private Integer cleanlinessScore;

	// 중복 방지 식별자
	@Column(name = "device_hash", nullable = false, length = 120)
	private String deviceHash;

	@Column(name = "ip_hash", length = 120)
	private String ipHash;

	// 메타
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	public Rating() {
		// JPA 기본 생성자
	}

	public Rating(Toilet toilet, int score, Integer cleanlinessScore, String deviceHash, String ipHash) {
		this.toilet = toilet;
		this.score = score;
		this.cleanlinessScore = cleanlinessScore; // null 가능
		this.deviceHash = deviceHash;
		this.ipHash = ipHash;
	}

	// --- getters / setters ---
	public Long getId() {
		return id;
	}

	public Toilet getToilet() {
		return toilet;
	}

	public void setToilet(Toilet toilet) {
		this.toilet = toilet;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Integer getCleanlinessScore() {
		return cleanlinessScore;
	}

	public void setCleanlinessScore(Integer cleanlinessScore) {
		this.cleanlinessScore = cleanlinessScore;
	}

	public String getDeviceHash() {
		return deviceHash;
	}

	public void setDeviceHash(String deviceHash) {
		this.deviceHash = deviceHash;
	}

	public String getIpHash() {
		return ipHash;
	}

	public void setIpHash(String ipHash) {
		this.ipHash = ipHash;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}
