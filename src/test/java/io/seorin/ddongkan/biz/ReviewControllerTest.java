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
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import net.jqwik.api.Arbitraries;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.seorin.ddongkan.biz.descriptors.ResponseFieldsDescriptors;
import io.seorin.ddongkan.dto.ReviewRequest;

import com.navercorp.fixturemonkey.FixtureMonkey;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ReviewControllerTest {

	private static final Logger log = LoggerFactory.getLogger(ReviewControllerTest.class);
	private final String REVIEW_SERVICE_URL = "/api/v1/reviews";

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void postToiletReview() throws Exception {
		FixtureMonkey fixtureMonkey = FixtureMonkey.create();
		var id = 1L;
		var reviewRequest = fixtureMonkey.giveMeBuilder(ReviewRequest.class)
			.set("stars", Arbitraries.integers().between(1, 5))
			.sample();
		var content = objectMapper.writeValueAsString(reviewRequest);

		mockMvc.perform(post(REVIEW_SERVICE_URL + "/{id}", id)
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

	@Test
	void getRaiting() throws Exception {
		var id = 1L;

		mockMvc.perform(get(REVIEW_SERVICE_URL+ "/{id}/rating", id))
			.andExpect(status().isOk())
			.andDo(
				document(
					"get-toilet-rating",
					resource(
						ResourceSnippetParameters.builder()
							.description("Retrieve rating information about a specific toilet by its ID.")
							.pathParameters(
								parameterWithName("id").description("Unique identifier of the toilet.")
							)
							.responseFields(
								fieldWithPath("avgStars").description("Average star rating of the toilet."),
								fieldWithPath("avgCleanliness").description("Average cleanliness rating."),
								fieldWithPath("avgToiletPaper").description("Average toilet paper availability rating."),
								fieldWithPath("avgWaterPressure").description("Average water pressure rating."),
								fieldWithPath("avgHotWater").description("Average hot water availability rating."),
								fieldWithPath("avgSafety").description("Average safety rating.")
							)
							.build()

					)
				)
			);

	}


	@Test
	void getReviews() throws Exception {
		var id = 1L;

		mockMvc.perform(get(REVIEW_SERVICE_URL+ "/{id}", id))
			.andExpect(status().isOk())
			.andDo(
				document(
					"get-toilet-reviews",
					resource(
						ResourceSnippetParameters.builder()
							.description("Retrieve reviews for a specific toilet by its ID.")
							.queryParameters(
								parameterWithName("size").description(
									"Number of reviews to retrieve per page. Default is 10.")
									.optional(),
								parameterWithName("index").description(
									"Page index for pagination. Default is 0.")
									.optional()
							)
							.pathParameters(
								parameterWithName("id").description("Unique identifier of the toilet.")
							)
							.responseFields(
								ResponseFieldsDescriptors.asList(
									new FieldDescriptor[] {
										fieldWithPath("stars").description("Star rating given in the review."),
										fieldWithPath("comment").description(
											"Comment provided in the review.").optional().type(JsonFieldType.STRING),
										fieldWithPath("cleanliness").description(
											"Indicates if cleanliness was liked."),
										fieldWithPath("toiletPaper").description(
											"Indicates if toilet paper availability was liked."),
										fieldWithPath("waterPressure").description(
											"Indicates if water pressure was liked."),
										fieldWithPath("hotWater").description(
											"Indicates if hot water availability was liked."),
										fieldWithPath("safety").description("Indicates if safety was liked."),
										fieldWithPath("createdAt").description(
											"Timestamp when the review was created.")
									}
								)
							)
							.build()
					)
				)
			);
	}

	@Test
	void getLatestReviews() throws Exception {
		var id = 1L;

		mockMvc.perform(get(REVIEW_SERVICE_URL+ "/{id}/latest", id))
			.andExpect(status().isOk())
			.andDo(
				document(
					"get-latest-toilet-reviews",
					resource(
						ResourceSnippetParameters.builder()
							.description("최신 100개의 화장실 리뷰를 조회합니다.")
							.pathParameters(
								parameterWithName("id").description("Unique identifier of the toilet.")
							)
							.responseFields(
								ResponseFieldsDescriptors.asList(
									new FieldDescriptor[] {
										fieldWithPath("stars").description("Star rating given in the review."),
										fieldWithPath("comment").description(
											"Comment provided in the review.").optional().type(JsonFieldType.STRING),
										fieldWithPath("cleanliness").description(
											"Indicates if cleanliness was liked."),
										fieldWithPath("toiletPaper").description(
											"Indicates if toilet paper availability was liked."),
										fieldWithPath("waterPressure").description(
											"Indicates if water pressure was liked."),
										fieldWithPath("hotWater").description(
											"Indicates if hot water availability was liked."),
										fieldWithPath("safety").description("Indicates if safety was liked."),
										fieldWithPath("createdAt").description(
											"Timestamp when the review was created.")
									}
								)
							)
							.build()
					)
				)
			);
	}

}