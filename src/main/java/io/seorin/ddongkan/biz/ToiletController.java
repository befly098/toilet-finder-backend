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

import io.seorin.ddongkan.dto.Review;
import io.seorin.ddongkan.dto.ToiletResponse;

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

		var result = this.toiletService.selectAllPointsWithinRadius(lat, lng, radius);
		var responses = result.stream().map(ToiletResponse::from).toList();
		return new ResponseEntity<>(responses, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<ToiletResponse> getToiletById(@PathVariable("id") Long id) {
		var toilet = this.toiletService.getToiletById(id);
		var response = ToiletResponse.from(toilet);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}")
	public ResponseEntity<Void> postToiletReview(
		@PathVariable("id") Long id,
		@RequestBody Review review) {
		this.toiletService.addReview(id, review);
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
}
