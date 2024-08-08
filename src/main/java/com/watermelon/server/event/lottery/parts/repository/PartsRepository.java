package com.watermelon.server.event.lottery.parts.repository;

import com.watermelon.server.event.lottery.parts.domain.Parts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartsRepository extends JpaRepository<Parts, Long> {

    @Query("select count(distinct parts.category) from Parts parts")
    long countCategoryDistinct();

}
