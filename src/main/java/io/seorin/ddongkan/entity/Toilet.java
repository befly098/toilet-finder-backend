package io.seorin.ddongkan.entity;

import java.time.Instant;
import java.time.ZonedDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "toilet",
	indexes = {
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

	@Column(columnDefinition = "geometry(Point, 4326)")
	private Point position;

	@Column
	private ZonedDateTime openAt;

	@Column
	private ZonedDateTime closeAt;

	// 편의 플래그
	@Column(nullable = false)
	private boolean accessible;   // 장애인 화장실 여부

	@Column(nullable = false)
	private boolean diaper;       // 기저귀 교환대 여부

	@Column(nullable = false)
	private boolean unisex;       // 남녀 공용 여부

	// 메타
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Instant createdAt;

	@UpdateTimestamp
	@Column
	private Instant updatedAt;

	public Toilet() {
		// JPA 기본 생성자
	}

	public Toilet(Long id, String name, String address, Point position, ZonedDateTime openAt, ZonedDateTime closeAt,
		boolean accessible, boolean diaper, boolean unisex, Instant createdAt, Instant updatedAt) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.position = position;
		this.openAt = openAt;
		this.closeAt = closeAt;
		this.accessible = accessible;
		this.diaper = diaper;
		this.unisex = unisex;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public Point getPosition() {
		return position;
	}

	public ZonedDateTime getOpenAt() {
		return openAt;
	}

	public ZonedDateTime getCloseAt() {
		return closeAt;
	}

	public boolean isAccessible() {
		return accessible;
	}

	public boolean isDiaper() {
		return diaper;
	}

	public boolean isUnisex() {
		return unisex;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public void setOpenAt(ZonedDateTime openAt) {
		this.openAt = openAt;
	}

	public void setCloseAt(ZonedDateTime closeAt) {
		this.closeAt = closeAt;
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

	public void setDiaper(boolean diaper) {
		this.diaper = diaper;
	}

	public void setUnisex(boolean unisex) {
		this.unisex = unisex;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
}
