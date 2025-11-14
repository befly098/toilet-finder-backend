package io.seorin.ddongkan.config;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeomFactory {
	private final GeometryFactory geometryFactory;

	public GeomFactory() {
		//@checkstyle:off
		final int SRID = 4326;
		//@checkstyle:on
		this.geometryFactory = new GeometryFactory(new PrecisionModel(), SRID);
	}

	public GeometryFactory getGeometryFactory() {
		return this.geometryFactory;
	}
}
