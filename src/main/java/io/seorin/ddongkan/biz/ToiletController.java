package io.seorin.ddongkan.biz;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import io.seorin.ddongkan.dto.ToiletDetailResponse;
import io.seorin.ddongkan.dto.ToiletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/toilets")
public class ToiletController {

	private final ToiletService toiletService;

	public ToiletController(ToiletService toiletService) {
		this.toiletService = toiletService;
	}

	@GetMapping
	public ResponseEntity<List<ToiletResponse>> getToilets(
		@RequestParam(value = "lat") Double lat,
		@RequestParam(value = "lng") Double lng,
		@RequestParam(value = "radius", required = false, defaultValue = "5000") Double radius
	) {

		var result = this.toiletService.getToiletWithinRadius(lat, lng, radius);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<ToiletDetailResponse> getToiletDetail(@PathVariable("id") Long id) {
		var result = this.toiletService.getToiletDetailById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/{id}/rates")
	public ResponseEntity<RaitingResponse> getToiletRates(@PathVariable("id") Long id) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	@PostMapping("/{id}")
	public ResponseEntity<Void> postToiletReview(
		@PathVariable("id") Long id,
		@Valid @RequestBody final ReviewRequest reviewRequest) {
		this.toiletService.addReview(id, reviewRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
