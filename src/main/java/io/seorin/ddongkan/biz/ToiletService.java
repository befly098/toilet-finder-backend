package io.seorin.ddongkan.biz;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.seorin.ddongkan.entity.Toilet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ToiletService {
	private static final Logger log = LoggerFactory.getLogger(ToiletService.class);
	@PersistenceContext
	private final EntityManager entityManager;

	public ToiletService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Polygon createCircle(double x, double y, double radius) {
		GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
		shapeFactory.setNumPoints(32);
		shapeFactory.setCentre(new Coordinate(x, y));
		shapeFactory.setSize(radius * 2);
		Polygon circle = shapeFactory.createCircle();
		circle.setSRID(4326);
		return circle;
	}

	public List<Toilet> selectAllPointsWithinRadius(Double lat, Double lng) {

		Session session = this.entityManager.unwrap(Session.class);
		Query<Toilet> query = session.createQuery("select t from Toilet t where within(t.position, :circle) = true", Toilet.class);
		query.setParameter("circle", createCircle(lng, lat, 1000));

		List<Toilet> result = query.getResultList();
		log.info("toilet count within circle: {}", result.size());
		return result;
	}
}
