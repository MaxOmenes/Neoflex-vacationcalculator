package io.ukhin.repository;

import io.ukhin.models.Holiday;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface HolidayRepository extends CrudRepository<Holiday, Holiday> {
    @Query("SELECT h FROM Holiday h WHERE h.month BETWEEN (:startMonth) AND (:endMonth) AND CASE " +
            "WHEN h.month = (:startMonth) AND h.month = (:endMonth) THEN h.day BETWEEN (:startDay) AND (:endDay)")
    Iterable<Holiday> findBetweenDates(@Param("startMonth") int startMonth,
                                       @Param("startDay") int startDay,
                                       @Param("endMonth") int endMonth,
                                       @Param("endDay") int endDay);
}
