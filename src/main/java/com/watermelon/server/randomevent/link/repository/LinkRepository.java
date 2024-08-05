package com.watermelon.server.randomevent.link.repository;

import com.watermelon.server.randomevent.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByUri(String uri);

}
