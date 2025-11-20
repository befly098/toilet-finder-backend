package io.seorin.ddongkan.biz;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.*;
import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import net.jqwik.api.Arbitraries;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.seorin.ddongkan.dto.ReviewRequest;

import com.navercorp.fixturemonkey.FixtureMonkey;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ToiletControllerTest {

	private static final Logger log = LoggerFactory.getLogger(ToiletControllerTest.class);
	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void getToilets() throws Exception {
		double lat = 37.4979;
		double lng = 127.0286;
		double radius = 30000.0;

		mockMvc.perform(get("/api/v1/toilets")
				.param("lat", Double.toString(lat))
				.param("lng", Double.toString(lng))
				.param("radius", Double.toString(radius)))
			.andExpect(status().isOk())
			.andDo(
				document(
					"get-toilets",
					resource(
						ResourceSnippetParameters.builder()
							.description("Retrieve a list of toilets near the specified latitude and longitude.")
							.queryParameters(
								parameterWithName("lat").description(
									"Latitude of the location to search nearby toilets."),
								parameterWithName("lng").description(
									"Longitude of the location to search nearby toilets."),
								parameterWithName("radius").description(
									"Radius (in meters) to search for nearby toilets. Default is 5000 meters.")
							)
							.responseFields(
								fieldWithPath("[]").description("List of nearby toilets."),
								fieldWithPath("[].id").description("Unique identifier of the toilet."),
								fieldWithPath("[].name").description("Name of the toilet."),
								fieldWithPath("[].coord.lat").description("Latitude of the toilet."),
								fieldWithPath("[].coord.lng").description("Longitude of the toilet.")
							)
							.build()

					)
				)
			);
	}

	@Test
	void postToiletReview() throws Exception {
		FixtureMonkey fixtureMonkey = FixtureMonkey.create();
		var id = 1L;
		var reviewRequest = fixtureMonkey.giveMeBuilder(ReviewRequest.class)
			.set("stars", Arbitraries.integers().between(1, 5))
			.sample();
		var content = objectMapper.writeValueAsString(reviewRequest);

		mockMvc.perform(post("/api/v1/toilets/{id}", id)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(
				document(
					"post-toilet-review",
					resource(
						ResourceSnippetParameters.builder()
							.description("Submit a review for a specific toilet by its ID.")
							.pathParameters(
								parameterWithName("id").description("Unique identifier of the toilet.")
							)
							.requestFields(
								fieldWithPath("stars").description("Star rating for the toilet (1 to 5)."),
								fieldWithPath("comment").description("Optional comment about the toilet."),
								fieldWithPath("likes.cleanliness").description(
									"Indicates if the reviewer liked the cleanliness of the toilet."),
								fieldWithPath("likes.toiletPaper").description(
									"Indicates if the reviewer liked the availability of toilet paper."),
								fieldWithPath("likes.waterPressure").description(
									"Indicates if the reviewer liked the water pressure."),
								fieldWithPath("likes.hotWater").description(
									"Indicates if the reviewer liked the availability of hot water."),
								fieldWithPath("likes.safety").description(
									"Indicates if the reviewer felt safe using the toilet.")
							)
							.build()

					)
				)
			);
	}
}