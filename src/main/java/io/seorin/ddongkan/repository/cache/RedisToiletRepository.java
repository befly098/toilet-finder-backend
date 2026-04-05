package io.seorin.ddongkan.repository.cache;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import io.seorin.ddongkan.utility.ToiletGeoMember;

@Repository
public class RedisToiletRepository {

	private static final String GEO_KEY = "toilet:position";
	private final GeoOperations<String, String> geoOps;

	public RedisToiletRepository(RedisTemplate<String, String> redisTemplate) {
		this.geoOps = redisTemplate.opsForGeo();
	}

	public void savePosition(Long toiletId, String name, double lat, double lng) {
		geoOps.add(GEO_KEY, new Point(lng, lat), ToiletGeoMember.encode(toiletId, name));
	}

	public GeoResults<RedisGeoCommands.GeoLocation<String>> findNearby(double lat, double lng, double radiusMeters) {
		var circle = new Circle(new Point(lng, lat), new Distance(radiusMeters, RedisGeoCommands.DistanceUnit.METERS));
		var args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeCoordinates();
		return geoOps.radius(GEO_KEY, circle, args);
	}
}
