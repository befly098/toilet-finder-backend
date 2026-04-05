package io.seorin.ddongkan.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import io.seorin.ddongkan.repository.cache.RedisToiletRepository;
import io.seorin.ddongkan.repository.db.ToiletRepository;
import jakarta.annotation.PostConstruct;

@Component
public class ToiletCacheLoader {

	private static final Logger log = LoggerFactory.getLogger(ToiletCacheLoader.class);
	private final ToiletRepository dbToiletRepository;
	private final RedisToiletRepository redisToiletRepository;

	public ToiletCacheLoader(
		ToiletRepository dbToiletRepository,
		RedisToiletRepository redisToiletRepository
	) {
		this.dbToiletRepository = dbToiletRepository;
		this.redisToiletRepository = redisToiletRepository;
	}

	@PostConstruct
	public void loadToiletGeomCache() {
		int index = 0;
		int size = 1000;

		log.debug("Start loading toilet geom cache from DB to Redis");
		while (true) {
			var pageable = PageRequest.of(index, size);
			var toilets = this.dbToiletRepository.findAllByOrderByCreatedAtDesc(pageable);

			log.debug("Loaded {} toilets from DB for cache loading", toilets.getSize());
			if (toilets.isEmpty()) {
				break;
			}

			toilets.forEach(toilet ->
				redisToiletRepository.savePosition(
					toilet.getId(),
					toilet.getName(),
					toilet.getPosition().getY(),  // lat
					toilet.getPosition().getX()   // lng
				)
			);
			index++;
		}
	}
}
