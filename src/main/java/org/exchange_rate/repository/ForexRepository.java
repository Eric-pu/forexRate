package org.exchange_rate.repository;

import org.exchange_rate.entity.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ForexRepository extends JpaRepository<CollectionEntity, String> {

    @Query(value = " select datetime from collection ", nativeQuery = true)
    List<String> queryListOfDatetime();

    @Query(value = " select * from collection where datetime = ?1", nativeQuery = true)
    Optional<CollectionEntity> findByDatetime(String datetime);

    @Query(value = " select * from collection where to_date(datetime, 'yyyymmdd') between ?1 and ?2 ", nativeQuery = true)
    List<CollectionEntity> queryByStartDateAndEndDate(LocalDate startDate, LocalDate endDate);
}
