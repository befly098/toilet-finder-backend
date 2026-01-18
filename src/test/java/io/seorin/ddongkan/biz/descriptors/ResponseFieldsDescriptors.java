package io.seorin.ddongkan.biz.descriptors;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.restdocs.payload.FieldDescriptor;

public class ResponseFieldsDescriptors {
	public static final FieldDescriptor[] TOILET_RESPONSE_FIELDS = new FieldDescriptor[] {
		fieldWithPath("id").description("Unique identifier of the toilet."),
		fieldWithPath("name").description("Name of the toilet."),
		fieldWithPath("coord.lat").description("Latitude of the toilet."),
		fieldWithPath("coord.lng").description("Longitude of the toilet.")
	};

	public static FieldDescriptor[] asList(FieldDescriptor[] fields) {
		var foo = Stream.concat(
				Stream.of(
					fieldWithPath("[]").description("List of items")
				),
				Arrays.stream(fields)
					.map(fd ->
					{
						var tmp = fieldWithPath("[]." + fd.getPath()).description(fd.getDescription());
						if (fd.isOptional()) tmp.optional();
						if (fd.getType() != null) tmp.type(fd.getType());
						return tmp;
				})
			)
			.toArray(FieldDescriptor[]::new);

		return foo;
	}

	public static FieldDescriptor[] toListWithIn(FieldDescriptor[] fields, FieldDescriptor... additional) {
		return Stream.concat(
				Arrays.stream(fields),
				Arrays.stream(additional)
			)
			.toArray(FieldDescriptor[]::new);
	}
}
