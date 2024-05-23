package com.sanlato.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanlato.demo.models.user;

@Repository
public interface UserRepository extends JpaRepository<user, Long> {
}