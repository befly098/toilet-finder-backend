package io.seorin.ddongkan.biz;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.seorin.ddongkan.config.GeomFactory;
import io.seorin.ddongkan.dto.ReviewRequest;
import io.seorin.ddongkan.dto.ToiletDetailResponse;
import io.seorin.ddongkan.dto.ToiletResponse;
import io.seorin.ddongkan.entity.Review;
import io.seorin.ddongkan.entity.Toilet;
import io.seorin.ddongkan.repository.ReviewRepository;
import io.seorin.ddongkan.repository.ToiletRepository;

@Service
public class ToiletService {
	private static final Logger log = LoggerFactory.getLogger(ToiletService.class);
	private final GeomFactory geomFactory;
	private final ToiletRepository toiletRepository;
	private final ReviewRepository reviewRepository;

	public ToiletService(GeomFactory geomFactory, ToiletRepository toiletRepository,
		ReviewRepository reviewRepository) {
		this.geomFactory = geomFactory;
		this.toiletRepository = toiletRepository;
		this.reviewRepository = reviewRepository;
	}

	public List<ToiletResponse> getToiletWithinRadius(Double lat, Double lng, Double radius) {
		var userPoint = geomFactory.getGeometryFactory().createPoint(new Coordinate(lng, lat));
		List<Toilet> toilets = this.toiletRepository.findToilets(userPoint, radius);
		// TODO: empty list인 경우, 404 에러 리턴
		log.debug("toilet count within circle: {}", toilets.size());
		return toilets.stream().map(ToiletResponse::from).toList();
	}

	public ToiletDetailResponse getToiletDetailById(Long id) {
		var toilet = this.toiletRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Toilet not found with id: " + id)
			);
		log.debug("toilet found: {}", toilet.toString());
		return ToiletDetailResponse.from(toilet);
	}

	public void addReview(Long id, ReviewRequest reviewRequest) {
		var toilet = this.toiletRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Toilet not found with id: " + id)
			);
		;
		var review = Review.toiletReviewOf(toilet, reviewRequest);
		reviewRepository.save(review);
	}
}
