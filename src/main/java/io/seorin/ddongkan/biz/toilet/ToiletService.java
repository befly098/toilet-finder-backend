package io.seorin.ddongkan.biz.toilet;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.seorin.ddongkan.dto.ToiletDetailResponse;
import io.seorin.ddongkan.dto.ToiletResponse;
import io.seorin.ddongkan.repository.cache.RedisToiletRepository;
import io.seorin.ddongkan.repository.db.ToiletRepository;

@Service
public class ToiletService {
	private static final Logger log = LoggerFactory.getLogger(ToiletService.class);
	private final ToiletRepository toiletRepository;
	private final RedisToiletRepository redisToiletRepository;

	public ToiletService(ToiletRepository toiletRepository,
		RedisToiletRepository redisToiletRepository) {
		this.toiletRepository = toiletRepository;
		this.redisToiletRepository = redisToiletRepository;
	}

	public List<ToiletResponse> getToilets(Double lat, Double lng, Double radius) {
		var toilets = this.redisToiletRepository.findNearby(lat, lng, radius);
		var result = toilets.getContent().stream().map(ToiletResponse::fromCache)
			.toList();
		log.debug("toilet count within circle: {}", result.size());

		if (result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No toilets found nearby");
		}

		return result;
	}

	public ToiletDetailResponse getToiletDetail(Long id) {
		var toilet = this.toiletRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Toilet not found with id: " + id)
			);
		log.debug("toilet found: {}", toilet.toString());
		return ToiletDetailResponse.from(toilet);
	}

}
