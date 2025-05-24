package com.journey_book_now.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.journey_book_now.entity.Auth;

@Repository
@EnableJpaRepositories
public interface AuthRepo extends JpaRepository<Auth, Long>{

	Optional<Auth> findByEmail(String email);
}
