package io.seorin.ddongkan.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "review")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "toilet_id", nullable = false)
	private Toilet toilet;

	@Column
	private Integer stars;

	@Column(length = 1000)
	private String comment;

	@Column(nullable = false)
	private boolean cleanliness;

	@Column(nullable = false)
	private boolean toiletPaper;

	@Column(nullable = false)
	private boolean waterPressure;

	@Column(nullable = false)
	private boolean hotWater;

	@Column(nullable = false)
	private boolean safety;

	@Column(updatable = false)
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private ZonedDateTime createdAt;
}
