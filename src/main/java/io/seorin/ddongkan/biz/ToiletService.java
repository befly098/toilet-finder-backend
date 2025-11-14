package io.seorin.ddongkan.biz;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.seorin.ddongkan.config.GeomFactory;
import io.seorin.ddongkan.entity.Toilet;
import io.seorin.ddongkan.repository.ToiletRepository;

@Service
public class ToiletService {
	private static final Logger log = LoggerFactory.getLogger(ToiletService.class);
	private final GeomFactory geomFactory;
	private final ToiletRepository toiletRepository;

	public ToiletService(GeomFactory geomFactory, ToiletRepository toiletRepository) {
		this.geomFactory = geomFactory;
		this.toiletRepository = toiletRepository;
	}

	public List<Toilet> selectAllPointsWithinRadius(Double lat, Double lng, Double radius) {
		Point userPoint = geomFactory.getGeometryFactory().createPoint(new Coordinate(lng, lat));
		List<Toilet> result = this.toiletRepository.findToilets(userPoint, radius);
		log.info("toilet count within circle: {}", result.size());
		return result;
	}

	public Toilet getToiletById(Long id) {
		return this.toiletRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Toilet not found with id: " + id));
	}
}
