package io.seorin.ddongkan.config;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeomFactory {
	private final GeometryFactory geometryFactory;

	public GeomFactory() {
		this.geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
	}

	public GeometryFactory getGeometryFactory() {
		return this.geometryFactory;
	}
}
