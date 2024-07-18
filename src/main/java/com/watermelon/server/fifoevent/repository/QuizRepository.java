package com.watermelon.server.fifoevent.repository;

import com.watermelon.server.fifoevent.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
}
