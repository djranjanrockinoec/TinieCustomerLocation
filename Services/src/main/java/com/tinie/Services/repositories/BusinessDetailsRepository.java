package com.tinie.Services.repositories;

import com.tinie.Services.models.BusinessDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusinessDetailsRepository extends JpaRepository<BusinessDetails, Integer> {

    @Query(value = """
            select dt.business_id, dt.business_type, dt.latitude, dt.longitude, dt.subcategory_id
            from (
                select business_id, business_type, latitude, longitude, subcategory_id,
                 ( 6371 * acos( cos( radians(?4) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(?5) ) + sin( radians(?4) ) * sin( radians( latitude ) ) ) ) as distance
                from business_details where subcategory_id = ?6
            ) as dt
            where distance < ?1
            order by dt.distance asc
            limit ?2 offset ?3
            """, nativeQuery = true)
    List<BusinessDetails> getBusinessesWithinDistance(int maxDistance, int limit, int offset, float lat, float lng, int subcatId);
}
