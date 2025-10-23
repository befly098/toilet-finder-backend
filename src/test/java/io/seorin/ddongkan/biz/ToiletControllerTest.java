package io.seorin.ddongkan.biz;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.*;
import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.epages.restdocs.apispec.ResourceSnippetParameters;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ToiletControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void getToilets() throws Exception {
		double lat = 37.5665;
		double lng = 126.9780;

		mockMvc.perform(get("/api/v1/toilets")
				.param("lat", Double.toString(lat))
				.param("lng", Double.toString(lng)))
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
									"Longitude of the location to search nearby toilets.")
							)
							.responseFields(
								fieldWithPath("[]").description("List of nearby toilets."),
								fieldWithPath("[].id").description("Unique identifier of the toilet."),
								fieldWithPath("[].name").description("Name of the toilet."),
								fieldWithPath("[].address").description("Address of the toilet."),
								fieldWithPath("[].lat").description("Latitude of the toilet."),
								fieldWithPath("[].lng").description("Longitude of the toilet."),
								fieldWithPath("[].openHours").description("Operating hours of the toilet."),
								fieldWithPath("[].accessible").description(
									"Indicates if the toilet is accessible for disabled individuals."),
								fieldWithPath("[].diaper").description(
									"Indicates if the toilet has a diaper changing station."),
								fieldWithPath("[].unisex").description("Indicates if the toilet is unisex."),
								fieldWithPath("[].avgRating").description("Average overall rating of the toilet."),
								fieldWithPath("[].avgCleanliness").description(
									"Average cleanliness rating of the toilet.")
							)
							.build()

					)
				)
			);
	}
}