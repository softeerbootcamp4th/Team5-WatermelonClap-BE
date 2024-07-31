package com.watermelon.server.randomevent.repository;

import com.watermelon.server.randomevent.domain.LotteryApplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotteryApplierRepository extends JpaRepository<LotteryApplier, Long> {

    List<LotteryApplier> findByLotteryRankNot(int rank);

    Optional<LotteryApplier> findByUid(String uid);

    Page<LotteryApplier> findByIsLotteryApplierTrue(Pageable pageable);

    List<LotteryApplier> findByIsPartsWinnerTrue();

}
