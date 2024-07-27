package com.watermelon.server.randomevent.repository;

import com.watermelon.server.randomevent.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findByLotteryRankNot(int rank);

    Optional<Participant> findByUid(String uid);

}
