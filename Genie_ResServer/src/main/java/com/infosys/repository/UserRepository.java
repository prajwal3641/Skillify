package com.infosys.repository;

import com.infosys.model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<users,Long> {

    Optional<users> findByEmail(String email);
}
