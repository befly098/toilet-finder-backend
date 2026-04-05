package io.seorin.ddongkan.utility;

public class ToiletGeoMember {

	public record Resolved(Long id, String name) { }

	public static String encode(Long toiletId, String name) {
		return toiletId + "!" + name;
	}

	public static Resolved decode(String member) {
		var parts = member.split("!", 2);
		return new Resolved(Long.parseLong(parts[0]), parts[1]);
	}
}
