package io.seorin.ddongkan.repository;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.seorin.ddongkan.entity.Toilet;

@Repository
public interface ToiletRepository extends JpaRepository<Toilet, Long> {

	@Query(
		value = """
			select *
			from toilet t
			where ST_DWithin(t.position, :point, :radius, false) = true
			""",
		nativeQuery = true
	)
	List<Toilet> findToilets(@Param("point") Point point, @Param("radius") Double radius);
}
