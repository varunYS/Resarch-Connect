package com.researchConnect.recommendation.repository;

import com.researchConnect.recommendation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
