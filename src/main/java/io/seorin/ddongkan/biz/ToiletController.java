package io.seorin.ddongkan.biz;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.seorin.ddongkan.dto.ToiletResponse;

@RestController
@RequestMapping("/api/v1/toilets")
public class ToiletController {

	@GetMapping
	public ResponseEntity<ToiletResponse> getToilets(
		@RequestParam(value = "lat", required = true) Double lat,
		@RequestParam(value = "lng", required = true) Double lng
	) {
		return new ResponseEntity<>(
			new ToiletResponse(
				1,
				"Sample Toilet",
				"123 Sample St, Sample City",
				lat,
				lng,
				"24/7",
				true,
				true,
				false,
				4.5,
				4.0
			),
			org.springframework.http.HttpStatus.OK
		);
	}
}
