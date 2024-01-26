package org.exchange_rate.repository;

import org.exchange_rate.entity.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ForexRepository extends JpaRepository<CollectionEntity, String> {

    @Query(value = " select c.date from CollectionEntity c")
    List<String> queryListOfDatetime();

    @Query(value = " select c from CollectionEntity c where datetime = :datetime")
    Optional<CollectionEntity> findByDatetime(@Param("datetime") String datetime);

    @Query(value = " select c from CollectionEntity c where to_date(datetime, 'yyyymmdd') between :startDate and :endDate ")
    List<CollectionEntity> queryByStartDateAndEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
