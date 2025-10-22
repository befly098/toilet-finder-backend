package io.seorin.ddongkan.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "report",
	indexes = {
		@Index(name = "idx_report_toilet", columnList = "toilet_id"),
		@Index(name = "idx_report_type_created", columnList = "type,created_at")
	})
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 대상 화장실
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "toilet_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_report_toilet"))
	private Toilet toilet;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 24)
	private ReportType type;

	// 자유형 JSON/TEXT
	@Lob
	@Column(columnDefinition = "TEXT")
	private String payload;

	// UA/OS 등 힌트
	@Column(length = 255)
	private String clientHint;

	// 메타
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	public Report() {
		// JPA 기본 생성자
	}

	public Report(Toilet toilet, ReportType type, String payload, String clientHint) {
		this.toilet = toilet;
		this.type = type;
		this.payload = payload;
		this.clientHint = clientHint;
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

	public ReportType getType() {
		return type;
	}

	public void setType(ReportType type) {
		this.type = type;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getClientHint() {
		return clientHint;
	}

	public void setClientHint(String clientHint) {
		this.clientHint = clientHint;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}
