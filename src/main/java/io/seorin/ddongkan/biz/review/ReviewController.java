package io.seorin.ddongkan.biz.review;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.seorin.ddongkan.dto.RaitingResponse;
import io.seorin.ddongkan.dto.ReviewRequest;
import io.seorin.ddongkan.dto.ReviewResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@GetMapping(value = "/{toiletId}/rating", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RaitingResponse> getRating(@PathVariable("toiletId") Long toiletId) {
		var result = this.reviewService.getRating(toiletId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/{toiletId}")
	public ResponseEntity<Void> addReview(
		@PathVariable("toiletId") Long toiletId,
		@Valid @RequestBody final ReviewRequest reviewRequest) {
		this.reviewService.addReview(toiletId, reviewRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/{toiletId}")
	public ResponseEntity<List<ReviewResponse>> getReviews(
		@PathVariable("toiletId") Long toiletId,
		@RequestParam(name = "size", defaultValue = "10") Integer size,
		@RequestParam(name = "index", defaultValue = "0") Integer index) {

		var result = this.reviewService.getReviews(toiletId, size, index);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/{toiletId}/latest")
	public ResponseEntity<List<ReviewResponse>> getLatestReviews(
		@PathVariable("toiletId") Long toiletId) {

		var result = this.reviewService.getTop100Reviews(toiletId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
