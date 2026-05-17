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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.seorin.ddongkan.biz.descriptors.ResponseFieldsDescriptors;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ToiletControllerTest {

	private static final Logger log = LoggerFactory.getLogger(ToiletControllerTest.class);
	private final String TOILET_SERVICE_URL = "/api/v1/toilets";

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void getToilets() throws Exception {
		double lat = 37.4979;
		double lng = 127.0286;
		double radius = 30000.0;

		mockMvc.perform(get(TOILET_SERVICE_URL)
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
									.optional()
							)
							.responseFields(
								ResponseFieldsDescriptors.asList(
									ResponseFieldsDescriptors.TOILET_RESPONSE_FIELDS
								)
							)
							.build()
					)
				)
			);
	}

	@Test
	void getToiletDetail() throws Exception {
		var id = 1L;

		mockMvc.perform(get(TOILET_SERVICE_URL + "/{id}", id))
			.andExpect(status().isOk())
			.andDo(
				document(
					"get-toilet-detail",
					resource(
						ResourceSnippetParameters.builder()
							.description("Retrieve detailed information about a specific toilet by its ID.")
							.pathParameters(
								parameterWithName("id").description("Unique identifier of the toilet.")
							)
							.responseFields(
								ResponseFieldsDescriptors
									.toListWithIn(
										ResponseFieldsDescriptors.TOILET_RESPONSE_FIELDS,
										fieldWithPath("address").description("Address of the toilet."),
										fieldWithPath("openAt").description("Opening time of the toilet."),
										fieldWithPath("closeAt").description("Closing time of the toilet."),
										fieldWithPath("accessible").description(
											"Indicates if the toilet is accessible."),
										fieldWithPath("diaper").description(
											"Indicates if the toilet has diaper facilities."),
										fieldWithPath("unisex").description("Indicates if the toilet is unisex.")
									)
							)
							.build()

					)
				)
			);
	}

}