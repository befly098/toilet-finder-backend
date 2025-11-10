package io.seorin.ddongkan.biz;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
