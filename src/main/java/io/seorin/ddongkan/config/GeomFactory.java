package io.seorin.ddongkan.config;

import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeomFactory {
	private final GeometryFactory geometryFactory;

	public GeomFactory() {
		this.geometryFactory = new GeometryFactory();
	}

	public GeometryFactory getGeometryFactory() {
		return geometryFactory;
	}
}
